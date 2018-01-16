package com.mileva.app.rest.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class DBConnector {
   final static Logger logger = LoggerFactory.getLogger(DBConnector.class);

   public void saveViolation(ByteArrayInputStream inputStream, int pictureSize, OffsetDateTime capturedDate, String licensePlate) throws ClassNotFoundException, SQLException, FileNotFoundException {
      Connection conn = connectToDb();
      OffsetDateTime recordedDate = OffsetDateTime.now();

      //request
      PreparedStatement ps = conn.prepareStatement("INSERT INTO \"public\".\"violation_record\" " +
            "(\"picture\", \"captured_date\", \"recorded_date\", \"license_plate_number\") " +
            "VALUES (?, ?, ?, ?)");
      ps.setBinaryStream(1, inputStream, pictureSize /*(int)file.length()*/);
      ps.setObject(2, capturedDate, Types.TIMESTAMP_WITH_TIMEZONE);
      ps.setObject(3, recordedDate, Types.TIMESTAMP_WITH_TIMEZONE);
      ps.setString(4, licensePlate);

      ps.execute();
      conn.close();
   }

   public String getViolationsForNumber(String licensePlateNumber) throws SQLException, ClassNotFoundException {
      Connection connection = connectToDb();
      PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"violation_record\" " +
            "WHERE \"violation_record\".\"license_plate_number\"=?");
      ps.setString(1, licensePlateNumber);
      //SELECT * FROM "violationrecords" WHERE "violationrecords"."licensePlateNumber"='B2440PX';
      ResultSet resultSet = ps.executeQuery();
      String result = getResultRecords(licensePlateNumber, resultSet);

      connection.close();
      return result;
   }

   public String getViolationsForPeriod(Date fromDate, Date toDate)
         throws SQLException, ClassNotFoundException {
      Connection connection = connectToDb();
      PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"violation_record\" " +
            "WHERE \"violation_record\".\"recorded_date\" BETWEEN ? AND ?");
      //SELECT * FROM "violationrecords" WHERE "violationrecords"."recordedDate" BETWEEN '2018-01-08' AND '2018-01-15';

      ps.setObject(1, OffsetDateTime.ofInstant(fromDate.toInstant(), ZoneOffset.ofHours(2)),
            Types.TIMESTAMP_WITH_TIMEZONE);
      ps.setObject(2, OffsetDateTime.ofInstant(toDate.toInstant(), ZoneOffset.ofHours(2)),
            Types.TIMESTAMP_WITH_TIMEZONE);

      ResultSet resultSet = ps.executeQuery();
      String result = getResultRecords("-", resultSet);
      connection.close();
      return result;
   }

   public boolean isPermittedVehicle(String licensePlateNumber) throws SQLException, ClassNotFoundException {
      Connection connection = connectToDb();
      PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM \"permitted_vehicle\" " +
            "WHERE \"permitted_vehicle\".\"license_plate_number\"=?");
      ps.setString(1, licensePlateNumber);
      //SELECT * FROM "permittedvehicles" WHERE "permittedvehicles"."licenseplatenumber"='C1234AA';
      ResultSet resultSet = ps.executeQuery();
      int rowCount = 0;
      while (resultSet.next()) {
         rowCount = resultSet.getInt(1);
      }
      connection.close();

      logger.debug("License plate " + licensePlateNumber);
      if (rowCount > 0) {
         logger.debug(" permitted to drive in BUS lane.");
         return true;
      }
      logger.debug(" not permitted to drive in BUS lane.");
      return false;
   }

   private Connection connectToDb() throws ClassNotFoundException, SQLException {
      Class.forName("org.postgresql.Driver");
      String url = "jdbc:postgresql://localhost:5432/alpralpr?user=alpralpr&password=alpralpr";
      return DriverManager.getConnection(url);
   }

   private String getResultRecords(String licensePlateNumber, ResultSet resultSet) throws SQLException {
      StringBuilder builder = new StringBuilder();
      builder.append("Violation records for number: ");
      builder.append(licensePlateNumber);
      builder.append("\n");
      while (resultSet.next()) {
         builder.append("License plate number: ");
         builder.append(resultSet.getString("license_plate_number"));
         builder.append(", captured date: ");
         builder.append(resultSet.getObject("captured_date", OffsetDateTime.class));
         builder.append(", recorded date: ");
         builder.append(resultSet.getObject("recorded_date", OffsetDateTime.class));
         builder.append("\n");
      }
      return builder.toString();
   }


}
