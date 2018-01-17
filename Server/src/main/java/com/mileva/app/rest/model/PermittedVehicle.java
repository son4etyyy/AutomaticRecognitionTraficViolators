package com.mileva.app.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "permitted_vehicle")
public class PermittedVehicle implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @Column(name = "license_plate_number", length = 128)
   private String licensePlateNumber;

   @Column(name = "type", length = 128)
   private String type;

   protected PermittedVehicle() {}

   public PermittedVehicle(String licensePlateNumber, String type) {
      this.licensePlateNumber = licensePlateNumber;
      this.type = type;
   }

   @Override
   public String toString() {
      return String.format(
            "PermittedVehicle[licensePlateNumber='%s', type='%s']",
            licensePlateNumber, type);
   }

   public String getLicensePlateNumber() {
      return licensePlateNumber;
   }

   public void setLicensePlateNumber(String licensePlateNumber) {
      this.licensePlateNumber = licensePlateNumber;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }
}
