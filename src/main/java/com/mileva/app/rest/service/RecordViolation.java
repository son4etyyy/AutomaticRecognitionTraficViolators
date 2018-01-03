package com.mileva.app.rest.service;

import com.mileva.app.rest.model.DBConnector;
import com.openalpr.jni.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.OffsetDateTime;

@Service
public class RecordViolation {
    @Autowired
    DBConnector dbConnector;

    public void recordViolation(byte[] bytes, OffsetDateTime offsetDateTime) throws AlprException {
        //todo take from alpr
        String licensePlate = "CB 1234 KA";

        Alpr alpr = new Alpr("eu", "/Users/sonia2/Documents/projects/openalpr-master/config/openalpr.conf",
                "/Users/sonia2/Documents/projects/openalpr-master/runtime_data");
        // Set top N candidates returned to 20
        alpr.setTopN(20);
        alpr.setDefaultRegion("bg");


        // Make sure to call this to release memory
        alpr.unload();
        AlprResults results = alpr.recognize(bytes);
        //todo check
        licensePlate = results.getPlates().get(0).getBestPlate().getCharacters();

        System.out.format("  %-15s%-8s\n", "Plate Number", "Confidence");
        for (AlprPlateResult result : results.getPlates())
        {
            for (AlprPlate plate : result.getTopNPlates()) {
                if (plate.isMatchesTemplate())
                    System.out.print("  * ");
                else
                    System.out.print("  - ");
                System.out.format("%-15s%-8f\n", plate.getCharacters(), plate.getOverallConfidence());
            }
        }





        //todo process

        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        int size = bytes.length;
        try {
            dbConnector.saveViolation(inputStream, size, offsetDateTime, licensePlate);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
