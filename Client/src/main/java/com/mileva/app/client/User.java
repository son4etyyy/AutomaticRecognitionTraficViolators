package com.mileva.app.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class User {
   public final static void main(String[] args) throws IOException {
      HttpClient client = new DefaultHttpClient();
      String requestforNumber = "http://localhost:8080/violationsForNumber?number=B2440PX";
      sendRequest(client, requestforNumber);

      String requestforPeriod = "http://localhost:8080/violationsForPeriod?fromDate=2018-01-08&toDate=2018-01-15";
      sendRequest(client, requestforPeriod);
   }

   private static void sendRequest(HttpClient client, String url) throws IOException {
      HttpGet request = new HttpGet(url);
      HttpResponse response = client.execute(request);

      // Get the response
      BufferedReader rd = new BufferedReader
            (new InputStreamReader(
                  response.getEntity().getContent()));

      StringBuilder textView = new StringBuilder();
      String line = "";
      while ((line = rd.readLine()) != null) {
         textView.append(line);
      }
      System.out.println("User has received: " + textView.toString());
   }
}
