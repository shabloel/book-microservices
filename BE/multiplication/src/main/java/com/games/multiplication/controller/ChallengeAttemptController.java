package com.games.multiplication.controller;

import com.games.multiplication.domain.dto.ChallengeAttemptDTO;
import com.games.multiplication.domain.model.ChallengeAttempt;
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
    ResponseEntity<ChallengeAttempt> postAChallengeAttempt(@RequestBody @Valid ChallengeAttemptDTO challengeAttemptDTO) {
        log.info("Received a request from client with the following attempt: [{}]", challengeAttemptDTO);
        ChallengeAttempt challengeAttempt = challengeService.verifyAttempt(challengeAttemptDTO);
        return ResponseEntity.ok(challengeAttempt);
    }

    @GetMapping
    ResponseEntity<List<ChallengeAttempt>> getUserStats(@RequestParam("alias") String alias) {
        log.info("Get request received fot the last 10 attempts for usr [{}]", alias);

        return ResponseEntity.ok(challengeService.getUserStats(alias));
    }


}
