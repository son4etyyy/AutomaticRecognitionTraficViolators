package com.mileva.app.rest.model;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.OffsetDateTime;

@Service
public class DBConnector {

   public void saveViolation(ByteArrayInputStream inputStream, int pictureSize, OffsetDateTime capturedDate, String licensePlate) throws ClassNotFoundException, SQLException, FileNotFoundException {
      Connection conn = connectToDb();
      Statement statement = conn.createStatement();

      OffsetDateTime recordedDate = OffsetDateTime.now();

      //request
      PreparedStatement ps = conn.prepareStatement("INSERT INTO \"public\".\"violationrecords\" " +
            "(\"picture\", \"capturedDate\", \"recordedDate\", \"licensePlateNumber\") " +
            "VALUES (?, ?, ?, ?)");
      ps.setBinaryStream(1, inputStream, pictureSize /*(int)file.length()*/);
      ps.setObject(2, capturedDate, Types.TIMESTAMP_WITH_TIMEZONE);
      ps.setObject(3, recordedDate, java.sql.Types.TIMESTAMP_WITH_TIMEZONE);
      ps.setString(4, licensePlate);

      ps.execute();
      statement.close();
      conn.close();
   }

   public String getViolationsForNumber(String licensePlateNumber) throws SQLException, ClassNotFoundException {
      Connection connection = connectToDb();
      Statement statement = connection.createStatement();
      PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"violationrecords\" " +
            "WHERE \"violationrecords\".\"licensePlateNumber\"=?");
      ps.setString(1, licensePlateNumber);
      //SELECT * FROM "violationrecords" WHERE "violationrecords"."licensePlateNumber"='B2440PX';
      ResultSet resultSet = ps.executeQuery();
      StringBuilder builder = new StringBuilder();
      builder.append("Violation records for number: ");
      builder.append(licensePlateNumber);
      builder.append("\n");
      while (resultSet.next()) {
         builder.append("License plate number: ");
         builder.append(resultSet.getString("licensePlateNumber"));
         builder.append(", captured date: ");
         builder.append(resultSet.getTimestamp("capturedDate"));
         builder.append(", recorded date: ");
         builder.append(resultSet.getTimestamp("recordedDate"));
         builder.append("\n");
      }

      statement.close();
      connection.close();
      return builder.toString();
   }

   private Connection connectToDb() throws ClassNotFoundException, SQLException {
      Class.forName("org.postgresql.Driver");
      String url = "jdbc:postgresql://localhost:5432/violations?user=postgres&password=postgres";
      return DriverManager.getConnection(url);
   }

}
