package com.mileva.app.rest.controller;

import com.mileva.app.rest.model.DBConnector;
import com.mileva.app.rest.service.RecordViolation;
import com.openalpr.jni.AlprException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/")
public class RecordViolatorController {

   @Autowired
   RecordViolation recordViolation;

   @Autowired
   DBConnector dbConnector;

   @RequestMapping(value = "recordViolator", method = RequestMethod.POST)
   public @ResponseBody
   String save(@RequestParam("file") MultipartFile file) {
      if(!file.isEmpty()) {
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

   @RequestMapping(value = "violation", method = RequestMethod.GET)
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

}
