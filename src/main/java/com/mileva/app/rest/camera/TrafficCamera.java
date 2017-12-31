package com.mileva.app.rest.camera;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.IOException;

public class TrafficCamera {
   public final static void main(String[] args) {
      File file = new File("test.txt");
      FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);

      MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
      builder.addPart("file", fileBody);
      HttpEntity entity = builder.build();

      HttpPost request = new HttpPost("http://localhost:8080/recordViolator");
      request.setEntity(entity);

      HttpClient client = HttpClientBuilder.create().build();
      HttpResponse response;
      int code = 0;
      try {
         response = client.execute(request);
         code = response.getStatusLine().getStatusCode();
      } catch (IOException e) {
         e.printStackTrace();
      }
      System.out.println("Responce status:" + code);
   }

}
