package com.games.gamification.gamification.controllers;


import com.games.gamification.gamification.domain.model.LeaderBoardRow;
import com.games.gamification.gamification.services.LeaderBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/leaders")
public class LeaderBoardController {

    private LeaderBoardService leaderBoardService;
    public LeaderBoardController(LeaderBoardService leaderBoardService) {
        this.leaderBoardService = leaderBoardService;
    }

    @GetMapping
    public List<LeaderBoardRow> getLeaderBoard(){
        log.info("Get request reveiced to /leaders/");
        return leaderBoardService.getCurrentLeaderBoard();
    }

}


