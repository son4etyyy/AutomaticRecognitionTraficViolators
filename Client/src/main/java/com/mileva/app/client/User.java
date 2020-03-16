package com.mileva.app.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class User {
   final static Logger logger = LoggerFactory.getLogger(User.class);

   public final static void main(String[] args) throws IOException {
      HttpClient client = new DefaultHttpClient();
      String requestforNumber = "http://localhost:8080/violations/number/B2440PX";
      sendRequest(client, requestforNumber);

      String requestforPeriod = "http://localhost:8080/violations?fromDate=2018-01-08&toDate=2020-12-18";
      sendRequest(client, requestforPeriod);
   }

   private static void sendRequest(HttpClient client, String url) throws IOException {
      HttpGet request = new HttpGet(url);
      HttpResponse response = client.execute(request);

      // Get the response
      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

      StringBuilder textView = new StringBuilder();
      String line = "";
      while ((line = rd.readLine()) != null) {
         textView.append(line);
      }
      logger.info("User has received: " + textView.toString());
   }
}
