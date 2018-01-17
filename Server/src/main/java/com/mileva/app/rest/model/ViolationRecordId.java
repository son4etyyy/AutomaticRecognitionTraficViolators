package com.mileva.app.rest.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ViolationRecordId implements Serializable {
   String licensePlateNumber;
   Timestamp capturedDate;
}
