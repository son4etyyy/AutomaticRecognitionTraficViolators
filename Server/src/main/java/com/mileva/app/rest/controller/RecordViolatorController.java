package com.mileva.app.rest.controller;

import com.mileva.app.rest.model.ViolationRecord;
import com.mileva.app.rest.repo.ViolationRecordRepository;
import com.mileva.app.rest.service.RecordViolation;
import com.openalpr.jni.AlprException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class RecordViolatorController {
   final static Logger logger = LoggerFactory.getLogger(RecordViolatorController.class);

   private RecordViolation recordViolation;
   private ViolationRecordRepository violationRecordRepository;

   @Autowired
   public RecordViolatorController(RecordViolation recordViolation, ViolationRecordRepository violationRecordRepository) {
      this.recordViolation = recordViolation;
      this.violationRecordRepository = violationRecordRepository;
   }

   @RequestMapping(value = "violations", method = RequestMethod.POST)
   public @ResponseBody
   String save(@RequestParam("file") MultipartFile file) {
      if (!file.isEmpty()) {
         logger.info("Received request to record violation.");
         try {
            byte[] bytes = file.getBytes();
            //todo take date from request
            Timestamp capturedDate = Timestamp.from(Instant.now());
            recordViolation.recordViolation(bytes, capturedDate);

            return "Success";
         } catch (IOException e) {
            e.printStackTrace();
            return "Failed with IO exception.";
         } catch (AlprException e) {
            e.printStackTrace();
            return "AlprException";
         }
      } else {
         return "Failed";
      }
   }

   @GetMapping("violations/number/{numberId}")
   public String getViolations(@PathVariable("numberId") String numberId, Model model) {
      if (numberId.isEmpty()) {
         return "error";
      }
      logger.info("Handling request for number: " + numberId);
      List<ViolationRecord> list = violationRecordRepository.findByLicensePlateNumber(numberId);

      model.addAttribute("violationRecords", list);
      return "violations_number";
   }

   @GetMapping(value = "violations")
   public String getForPeriod(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
                       Model model) {
      logger.info("Handling request for period: " + fromDate + " - " + toDate);
      Timestamp from = new Timestamp(fromDate.getTime());
      Timestamp to = new Timestamp(toDate.getTime());
      List<ViolationRecord> list = violationRecordRepository.findByRecordedDateBetween(from, to);

      model.addAttribute("violationRecords", list);
      return "violations_period";
   }
}
