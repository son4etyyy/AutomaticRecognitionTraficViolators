package com.mileva.app.rest.repo;

import com.mileva.app.rest.model.ViolationRecord;
import com.mileva.app.rest.model.ViolationRecordId;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;

public interface ViolationRecordRepository extends CrudRepository<ViolationRecord, ViolationRecordId> {

   ViolationRecord save(ViolationRecord persisted);

   List<ViolationRecord> findByLicensePlateNumber(String license_plate_number);

   List<ViolationRecord> findByRecordedDateBetween(Timestamp from, Timestamp to);
}
