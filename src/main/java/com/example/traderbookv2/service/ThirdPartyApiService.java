package com.example.traderbookv2.service;

import com.example.traderbookv2.exception.ApiRequestException;
import com.example.traderbookv2.model.ShareEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ThirdPartyApiService {

    @Autowired
    ObjectMapper objectMapper;

    public ShareEntity coin(String shareName) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://realstonks.p.rapidapi.com/" + shareName))
                .header("X-RapidAPI-Key", "5525bfe3abmsh62d8c0385d948cap16ff08jsnc07bcbaddb06")
                .header("X-RapidAPI-Host", "realstonks.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;

        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String json = response.body();
        ShareEntity shareEntity = null;

        try {
            shareEntity = objectMapper.readValue(json, ShareEntity.class);
        } catch (JsonProcessingException e) {
            throw new ApiRequestException(e.getMessage());

        }

        shareEntity.setName(shareName);
        System.out.println(response.body());

        return shareEntity;
    }
}
