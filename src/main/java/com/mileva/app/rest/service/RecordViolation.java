package com.mileva.app.rest.service;

import com.mileva.app.rest.model.DBConnector;
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

    public void recordViolation(byte[] bytes, OffsetDateTime offsetDateTime) {
        //todo take from alpr
        String licensePlate = "CB 1234 KA";

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
