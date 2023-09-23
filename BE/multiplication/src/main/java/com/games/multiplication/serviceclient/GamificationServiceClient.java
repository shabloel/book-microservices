package com.games.multiplication.serviceclient;

import com.games.multiplication.domain.dto.AttemptCheckedDto;
import com.games.multiplication.domain.dto.AttemptDTO;
import com.games.multiplication.domain.model.Attempt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GamificationServiceClient {
    private RestTemplate restTemplate;
    private String gamificationHostUrl ="http://localhost:8081";

    public GamificationServiceClient(
            final RestTemplateBuilder builder) {

        this.restTemplate = builder.build();
    }

    public boolean sendAttempt(final AttemptCheckedDto attempt){
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    gamificationHostUrl + "/attempts",
                    attempt,
                    String.class);
            log.info("Gamification service response: {}", response.getStatusCode());
            return response.getStatusCode().is2xxSuccessful();
        } catch (RestClientException e) {
            log.error("There was a problem sending the attempt. ", e);
            return false;
        } catch (IllegalArgumentException e) {
            log.error("The url is not correctly specified. ", e);
            return false;
        }
    }
}
