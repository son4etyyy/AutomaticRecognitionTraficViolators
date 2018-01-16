package com.mileva.app.rest.service;

import com.mileva.app.rest.model.DBConnector;
import com.openalpr.jni.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.OffsetDateTime;

@Service
public class RecordViolation {
   final static Logger logger = LoggerFactory.getLogger(RecordViolation.class);

   @Autowired
   DBConnector dbConnector;

   public void recordViolation(byte[] bytes, OffsetDateTime offsetDateTime) throws AlprException {
      String licensePlate;

      Alpr alpr = new Alpr("eu", "/home/sonia/openalpr/config/openalpr.conf",
            "/home/sonia/openalpr/runtime_data");
      // Set top N candidates returned to 20
      alpr.setTopN(20);
      alpr.setDefaultRegion("bg");

      AlprResults results = alpr.recognize(bytes);
      licensePlate = results.getPlates().get(0).getBestPlate().getCharacters();

      logger.trace(String.format("  %-15s%-8s\n", "Plate Number", "Confidence"));
      for (AlprPlateResult result : results.getPlates()) {
         for (AlprPlate plate : result.getTopNPlates()) {
            if (plate.isMatchesTemplate()) {
               licensePlate = plate.getCharacters();
               break;
            } else
               logger.trace(String.format("%-15s%-8f\n", plate.getCharacters(), plate.getOverallConfidence()));
         }
      }
      //release memory
      alpr.unload();

      logger.debug("License plate: " + licensePlate);

      try {
         //check if vehicle allowed to drive in BUS lane
         boolean isPermitted = dbConnector.isPermittedVehicle(licensePlate);

         if (!isPermitted) {
            //save violation
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            int size = bytes.length;
            dbConnector.saveViolation(inputStream, size, offsetDateTime, licensePlate);
         }
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }

   }
}
