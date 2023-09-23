package com.games.multiplication.controller;

import com.games.multiplication.domain.dto.AttemptDTO;
import com.games.multiplication.domain.model.Attempt;
import com.games.multiplication.services.ChallengeService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/attempts")
public class ChallengeAttemptController {

    private final ChallengeService challengeService;

    public ChallengeAttemptController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @PostMapping
    ResponseEntity<Attempt> postAChallengeAttempt(@RequestBody @Valid AttemptDTO attemptDTO) {
        log.info("Received a request from client with the following attempt: [{}]", attemptDTO);
        Attempt attempt = challengeService.verifyAttempt(attemptDTO);
        return ResponseEntity.ok(attempt);
    }

    @GetMapping
    ResponseEntity<List<Attempt>> getUserStats(@RequestParam("alias") String alias) {
        log.info("Get request received fot the last 10 attempts for usr [{}]", alias);

        return ResponseEntity.ok(challengeService.getUserStats(alias));
    }


}
