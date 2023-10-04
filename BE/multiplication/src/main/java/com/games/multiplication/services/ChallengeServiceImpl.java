package com.games.multiplication.services;

import com.games.multiplication.domain.dto.AttemptCheckedEvent;
import com.games.multiplication.domain.dto.AttemptDTO;
import com.games.multiplication.domain.model.AttemptChecked;
import com.games.multiplication.domain.model.Uzer;
import com.games.multiplication.events.ChallengeEventPub;
import com.games.multiplication.repos.AttemptRepository;
import com.games.multiplication.repos.UserRepository;
import com.games.multiplication.services.mapper.SourceDestinationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChallengeServiceImpl implements ChallengeService {

    private final UserRepository userRepository;

    private final ChallengeEventPub challengeEventPub;

    private final AttemptRepository attemptRepository;

    private final SourceDestinationMapper sourceDestinationMapper;

    public ChallengeServiceImpl(UserRepository userRepository, ChallengeEventPub challengeEventPub, AttemptRepository attemptRepository, SourceDestinationMapper sourceDestinationMapper) {
        this.userRepository = userRepository;
        this.challengeEventPub = challengeEventPub;
        this.attemptRepository = attemptRepository;
        this.sourceDestinationMapper = sourceDestinationMapper;
    }

    @Override
    public AttemptChecked verifyAttempt(AttemptDTO attemptDto) {

        boolean isCorrect =
                attemptDto.getFactorA() * attemptDto.getFactorB() == attemptDto.getUserGuess() ? true : false;

        Uzer user = userRepository.findByAlias(attemptDto.getUserAlias()).orElseGet(() -> {
            log.info("Storing a new user with alias [{}]", attemptDto.getUserAlias());
            return userRepository.save(new Uzer(attemptDto.getUserAlias()));
        });

        AttemptChecked attemptChecked = sourceDestinationMapper.attemptDtoToAttemptChecked(attemptDto);
        attemptChecked.setUzer(user);
        attemptChecked.setCorrect(isCorrect);
        AttemptChecked storedAttemptChecked = attemptRepository.save(attemptChecked);

        //Send the challengeAttempt to the Leaderboardservice
        challengeEventPub.challengeSolved(storedAttemptChecked);

        return storedAttemptChecked;
    }

    @Override
    public List<AttemptCheckedEvent> getUserStats(String alias) {
        return sourceDestinationMapper
                .attemptsCheckedToAttemptsCheckedEvent(attemptRepository.findTop10ByUzerAliasOrderByIdDesc(alias));
    }
}
