package com.mileva.app.rest.client;

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
      HttpGet request = new HttpGet("http://localhost:8080/violation?number=B2440PX");
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
