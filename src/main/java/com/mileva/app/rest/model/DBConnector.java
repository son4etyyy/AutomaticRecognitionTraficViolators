package com.mileva.app.rest.model;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
    import java.io.FileNotFoundException;
import java.sql.*;
import java.sql.Statement;
import java.time.OffsetDateTime;

@Service
public class DBConnector {

    public void saveViolation(ByteArrayInputStream inputStream, int pictureSize, OffsetDateTime capturedDate, String licensePlate) throws ClassNotFoundException, SQLException, FileNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/test?user=postgres&password=son4encety";
        Connection conn = DriverManager.getConnection(url);
        Statement statement = conn.createStatement();

        OffsetDateTime recordedDate = OffsetDateTime.now();

        //request
        PreparedStatement ps = conn.prepareStatement("INSERT INTO \"Violations\".\"ViolationRecords\" " +
                "(\"picture\", \"capturedDate\", \"recordedDate\", \"licensePlate\") " +
                "VALUES (?, ?, ?, ?)");
        ps.setBinaryStream(1, inputStream, pictureSize /*(int)file.length()*/);
        ps.setObject(2, capturedDate, Types.TIMESTAMP_WITH_TIMEZONE);
        ps.setObject(3, recordedDate, java.sql.Types.TIMESTAMP_WITH_TIMEZONE);
        ps.setString(4, licensePlate);

        ps.execute();
        statement.close();
        conn.close();
    }

}
