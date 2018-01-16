package com.mileva.app.camera;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class TrafficCamera {
   public final static void main(String[] args) throws MalformedURLException {
      InputStream inputStream = TrafficCamera.class.getResourceAsStream("/bg_b2440px.jpg");
      InputStreamBody inputStreamBody = new InputStreamBody(inputStream, "file.jpg");

      MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
      builder.addPart("file", inputStreamBody);
      HttpEntity entity = builder.build();

      HttpPost request = new HttpPost("http://localhost:8080/recordViolator");
      request.setEntity(entity);

      HttpClient client = HttpClientBuilder.create().build();

      int code = 0;
      try {
         HttpResponse response = client.execute(request);
         code = response.getStatusLine().getStatusCode();
      } catch (IOException e) {
         e.printStackTrace();
      }
      System.out.println("Response status:" + code);
   }

}
