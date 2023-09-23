package com.games.multiplication.controller;

import com.games.multiplication.domain.model.Challenge;
import com.games.multiplication.services.ChallengeGeneratorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/challenges")
public class ChallengeController {

    private final ChallengeGeneratorService challengeGeneratorService;
    public ChallengeController(ChallengeGeneratorService challengeGeneratorService) {
        this.challengeGeneratorService = challengeGeneratorService;
    }

    @GetMapping("/random")
    public Challenge getChallenge(){
        Challenge challenge = challengeGeneratorService.randomChallenge();
        //log.info("Received GET request /challenges, and returning challenge [{}]", challenge.toString());
        return challenge;
    }
}
