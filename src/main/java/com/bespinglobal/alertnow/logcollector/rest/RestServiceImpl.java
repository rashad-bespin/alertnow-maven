package com.bespinglobal.alertnow.logcollector.rest;

import com.bespinglobal.alertnow.logcollector.Log;
import com.bespinglobal.alertnow.logcollector.Options;
import com.bespinglobal.alertnow.logcollector.Platform;
import com.bespinglobal.alertnow.logcollector.rest.model.LogRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class RestServiceImpl implements RestService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final Options options;

    public RestServiceImpl(Options options) {
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        this.options = options;
        this.options.setAccessToken(getAccessToken());
    }

    private String getAccessToken() {
        String url = String.format("%s/api/users/sign-in-with-api-key/%s", options.getHost(), options.getApiKey());
        String token = restTemplate.postForObject(url, null, String.class);
        return token;
    }

    @Override
    public void postLog(Log log) {
        String url = String.format("%s/api/logs/create", options.getHost());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + options.getAccessToken());

        LogRequest logRequest = null;

        try {
            logRequest = LogRequest
                    .builder()
                    .platform(Platform.MAVEN)
                    .value(objectMapper.writeValueAsString(log))
                    .build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpEntity<LogRequest> entity = new HttpEntity<>(logRequest, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Action not completed!");
        }
    }
}
