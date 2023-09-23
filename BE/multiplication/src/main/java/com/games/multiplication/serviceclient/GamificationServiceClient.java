package com.games.multiplication.serviceclient;

import com.games.multiplication.domain.dto.ChallengeAttemptDTO;
import com.games.multiplication.domain.model.ChallengeAttempt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GamificationServiceClient {
    private RestTemplate restTemplate;
    private String gamificationHostUrl;

    public GamificationServiceClient(
            final RestTemplateBuilder builder,
            @Value("$(service.gamification.host}")
            final String gamificationHostUrl) {
        this.restTemplate = builder.build();
        this.gamificationHostUrl = gamificationHostUrl;
    }

    public boolean sendAttempt(final ChallengeAttempt attempt){
        ChallengeAttemptDTO challengeAttemptDTO = new ChallengeAttemptDTO(
                attempt.getFactorA(),
                attempt.getFactorB(),
                attempt.getUzer().getAlias(),
                attempt.getUserGuess());

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    gamificationHostUrl + "/attempts",
                    challengeAttemptDTO,
                    String.class);
            log.info("Gamification service response: {}", response.getStatusCode());
            return response.getStatusCode().is2xxSuccessful();
        } catch (RestClientException e) {
            log.error("There was a problem sending the attempt. ", e);
            return false;
        }
    }
}
