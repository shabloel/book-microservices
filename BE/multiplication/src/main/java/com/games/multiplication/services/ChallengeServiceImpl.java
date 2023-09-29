package com.games.multiplication.services;

import com.games.multiplication.domain.dto.AttemptDtoChecked;
import com.games.multiplication.domain.model.AttemptChecked;
import com.games.multiplication.domain.dto.AttemptDTO;
import com.games.multiplication.domain.model.Uzer;
import com.games.multiplication.repos.AttemptRepository;
import com.games.multiplication.repos.UserRepository;
import com.games.multiplication.serviceclient.GamificationServiceClient;
import com.games.multiplication.services.mapper.SourceDestinationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChallengeServiceImpl implements ChallengeService {

    private final UserRepository userRepository;

    private final GamificationServiceClient gamificationServiceClient;

    private final AttemptRepository attemptRepository;

    private final SourceDestinationMapper sourceDestinationMapper;

    public ChallengeServiceImpl(UserRepository userRepository, GamificationServiceClient gamificationServiceClient, AttemptRepository attemptRepository, SourceDestinationMapper sourceDestinationMapper) {
        this.userRepository = userRepository;
        this.gamificationServiceClient = gamificationServiceClient;
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
        sentCheckedAttempt(storedAttemptChecked);

        return storedAttemptChecked;
    }

    @Override
    public List<AttemptDtoChecked> getUserStats(String alias) {
       return sourceDestinationMapper
               .attemptsCheckedToAttemptsCheckedDto(attemptRepository.findTop10ByUzerAliasOrderByIdDesc(alias));
    }

    private void sentCheckedAttempt(AttemptChecked attemptChecked) {
        AttemptDtoChecked attemptDtoChecked = sourceDestinationMapper.attempCheckedToAttemptDtoChecked(attemptChecked);

        var isSuccessful = gamificationServiceClient.sendAttempt(attemptDtoChecked);
        if(isSuccessful){
            log.info("Successfully sent the challenge attempt to the LeaderBoardService");
        } else {
            log.info("There was an error sending the challenge attempt to the LeaderBoardService");
        }
    }
}
