package com.mileva.app.rest.repo;

import com.mileva.app.rest.model.PermittedVehicle;
import org.springframework.data.repository.CrudRepository;

public interface PermittedVehicleReporsitory extends CrudRepository<PermittedVehicle, String>  {
   boolean existsByLicensePlateNumber(String license_plate_number);
}
