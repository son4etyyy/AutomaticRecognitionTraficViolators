package com.mileva.app.rest.controller;

//import org.springframework.beans.factory.anotation.RestController;

import com.mileva.app.rest.service.RecordViolation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/")
public class RecordViolatorController {

   @Autowired
   RecordViolation recordViolation;

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
         }
      } else {
         return "Failed";
      }
   }

}
