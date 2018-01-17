package com.mileva.app.rest.service;

import com.mileva.app.rest.model.ViolationRecord;
import com.mileva.app.rest.repo.PermittedVehicleReporsitory;
import com.mileva.app.rest.repo.ViolationRecordRepository;
import com.openalpr.jni.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class RecordViolation {
   final static Logger logger = LoggerFactory.getLogger(RecordViolation.class);

   @Autowired
   ViolationRecordRepository violationRecordRepository;

   @Autowired
   PermittedVehicleReporsitory permittedVehicleReporsitory;

   public void recordViolation(byte[] bytes, Timestamp timestamp) throws AlprException {
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

      //check if vehicle allowed to drive in BUS lane
      boolean isPermitted = permittedVehicleReporsitory.existsByLicensePlateNumber(licensePlate);

      if (!isPermitted) {
         //save violation
         ViolationRecord violationRecord = new ViolationRecord(bytes, licensePlate, timestamp, Timestamp.from(Instant.now()));
         violationRecordRepository.save(violationRecord);
      }

   }
}
