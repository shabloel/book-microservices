package com.games.gamification.gamification.services;

import com.games.gamification.gamification.domain.dto.LeaderBoardRow;

import java.util.List;

public interface LeaderBoardService {

    List<LeaderBoardRow> getCurrentLeaderBoard();
}
