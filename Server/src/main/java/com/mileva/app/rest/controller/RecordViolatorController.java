package com.mileva.app.rest.controller;

import com.mileva.app.rest.model.DBConnector;
import com.mileva.app.rest.service.RecordViolation;
import com.openalpr.jni.AlprException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Date;

@RestController
@RequestMapping("/")
public class RecordViolatorController {
   final static Logger logger = LoggerFactory.getLogger(RecordViolatorController.class);

   @Autowired
   RecordViolation recordViolation;

   @Autowired
   DBConnector dbConnector;

   @RequestMapping(value = "recordViolator", method = RequestMethod.POST)
   public @ResponseBody
   String save(@RequestParam("file") MultipartFile file) {
      if (!file.isEmpty()) {
         logger.info("Received request to record violation.");
         try {
            byte[] bytes = file.getBytes();
            //todo take date from request
            OffsetDateTime capturedDate = OffsetDateTime.now();
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

   @RequestMapping(value = "violationsForNumber", method = RequestMethod.GET)
   public @ResponseBody
   String getByNumber(@RequestParam("number") String number) {
      try {
         return dbConnector.getViolationsForNumber(number);
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }
      return "Failed";
   }

   @RequestMapping(value = "violationsForPeriod", method = RequestMethod.GET)
   public @ResponseBody
   String getForPeriod(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

      try {
         return dbConnector.getViolationsForPeriod(fromDate, toDate);
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }
      return "Failed";
   }

}
