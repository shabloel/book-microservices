package com.games.multiplication.controller;

import com.games.multiplication.domain.model.ChallengeAttempt;
import com.games.multiplication.domain.model.ChallengeAttemptDTO;
import com.games.multiplication.services.ChallengeService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/attempts")
public class ChallengeAttemptController {

    private final ChallengeService challengeService;


    public ChallengeAttemptController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @PostMapping
    ResponseEntity<ChallengeAttempt> postAChallengeAttempt(@RequestBody @Valid ChallengeAttemptDTO challengeAttemptDTO) {
        log.info("Received a request from client with the following attempt: [{}]", challengeAttemptDTO);

        ChallengeAttempt challengeAttempt = challengeService.verifyAttempt(challengeAttemptDTO);

        return ResponseEntity.ok(challengeAttempt);
    }


}
