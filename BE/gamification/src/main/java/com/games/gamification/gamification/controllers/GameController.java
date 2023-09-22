package com.games.gamification.gamification.controllers;

import com.games.gamification.gamification.domain.dto.Attempt;
import com.games.gamification.gamification.services.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attempts")
@Slf4j
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    void postResult(@RequestBody Attempt challenge) {
        gameService.newAttemptFromUser(challenge);
    }
}
