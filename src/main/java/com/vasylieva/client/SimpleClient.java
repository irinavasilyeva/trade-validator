package com.vasylieva.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class SimpleClient {

    private static final String REST_URI = "http://localhost:8080/validate";

    public static void main(String[] args) throws Exception {
        String data = getDataFromFile();
        String responseData = sendRequest(data);
        printResponseData(responseData);
    }

    private static String getDataFromFile() throws IOException, URISyntaxException {
        StringBuilder data = new StringBuilder();
        Path path = Paths.get(SimpleClient.class.getClassLoader().getResource("dataToClient").toURI());
        Stream<String> lines = Files.lines(path);
        lines.forEach(line -> data.append(line).append("\n"));
        lines.close();

        return data.toString();
    }

    private static String sendRequest(String data) throws UnirestException {
        HttpResponse<String> response = Unirest.post(REST_URI)
                .header("content-type","application/json")
                .header("accept","application/json")
                .body(data)
                .asString();

        if (response.getStatus() != 200) {
            throw new IllegalStateException("Failed : HTTP error code : " + response.getStatus());
        }

        return response.getBody();
    }

    private static void printResponseData(String data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Object json = mapper.readValue(data, Object.class);
        String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        System.out.println("Output from Server .... \n");
        System.out.println(indented);
    }
}
