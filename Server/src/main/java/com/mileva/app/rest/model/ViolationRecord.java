package com.mileva.app.rest.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@IdClass(ViolationRecordId.class)
@Table(name = "violation_record")
public class ViolationRecord implements Serializable {
   private static final long serialVersionUID = 1L;

   @Column(name = "picture")
   private byte[] picture;

   @Id
   @Column(name = "license_plate_number", length = 128)
   private String licensePlateNumber;

   @Id
   @Column(name = "captured_date")
   private Timestamp capturedDate;

   @Column(name = "recorded_date")
   private Timestamp recordedDate;

   protected ViolationRecord() {}

   public  ViolationRecord(byte[] picture, String licensePlateNumber, Timestamp capturedDate,
                           Timestamp recordedDate) {
      this.picture = picture;
      this.licensePlateNumber = licensePlateNumber;
      this.capturedDate = capturedDate;
      this.recordedDate = recordedDate;
   }

   @Override
   public String toString() {
      return String.format(
            "ViolationRecord[licensePlateNumber='%s', capturedDate='%s', recordedDate='%s']",
            licensePlateNumber, capturedDate, recordedDate);
   }

   public byte[] getPicture() {
      return picture;
   }

   public void setPicture(byte[] picture) {
      this.picture = picture;
   }

   public String getLicensePlateNumber() {
      return licensePlateNumber;
   }

   public void setLicensePlateNumber(String licensePlateNumber) {
      this.licensePlateNumber = licensePlateNumber;
   }

   public Timestamp getCapturedDate() {
      return capturedDate;
   }

   public void setCapturedDate(Timestamp capturedDate) {
      this.capturedDate = capturedDate;
   }

   public Timestamp getRecordedDate() {
      return recordedDate;
   }

   public void setRecordedDate(Timestamp recordedDate) {
      this.recordedDate = recordedDate;
   }
}
