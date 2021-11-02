package com.libraryapp.javafx;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BaseController {

    @Value("${server.url}")
    protected String endpointUrl;

    protected HttpClient client = HttpClient.newHttpClient();
    protected ObjectMapper objectMapper = new ObjectMapper();

    protected  <T> T makeGetRequest(String uri, Class<T> responseType) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder(URI.create(uri)).build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        return objectMapper.readValue(response, responseType);
    }

    protected  <T> T makePostRequest(String uri, Class<T> responseType, Object postObject) throws IOException {
        var httpRequest = new HttpPost(uri);
        var stringObject = objectMapper.writeValueAsString(postObject);
        var entity = new StringEntity(stringObject);
        httpRequest.setEntity(entity);
        httpRequest.setHeader("Content-Type", "application/json");
        var apacheClient = HttpClients.createDefault();
        var response = apacheClient.execute(httpRequest);
        if (response.getEntity().getContentLength() == 0) {
            return null;
        }
        return objectMapper.readValue(response.getEntity().getContent(), responseType);
    }

    protected  <T> T makePutRequest(String uri, Class<T> responseType, Object postObject) throws IOException {
        var httpRequest = new HttpPut(uri);
        var stringObject = objectMapper.writeValueAsString(postObject);
        var entity = new StringEntity(stringObject);
        httpRequest.setEntity(entity);
        httpRequest.setHeader("Content-Type", "application/json");
        var apacheClient = HttpClients.createDefault();
        var response = apacheClient.execute(httpRequest);
        return objectMapper.readValue(response.getEntity().getContent(), responseType);
    }

    protected  <T> T makeDeleteRequest(String uri, Class<T> responseType) throws IOException {
        var httpRequest = new HttpDelete(uri);
        var apacheClient = HttpClients.createDefault();
        var response = apacheClient.execute(httpRequest);
        return objectMapper.readValue(response.getEntity().getContent(), responseType);
    }

    protected void enableButton(Button button) {
        button.setDisable(false);
        button.setOpacity(1);
    }

    protected void disableButton(Button button) {
        button.setDisable(true);
        button.setOpacity(0.66);
    }

    protected void showText(Text text) {
        text.setOpacity(1);
        text.setVisible(true);
    }

    protected void hideText(Text text) {
        text.setOpacity(0);
        text.setVisible(false);
    }
}
