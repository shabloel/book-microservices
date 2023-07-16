package com.games.multiplication.services;

import com.games.multiplication.domain.model.Challenge;

public interface  ChallengeGeneratorService {

    /**
     *
     * @return a randomly generated challenge with factors between 11 and 99
     */
     Challenge randomChallenge();
}
