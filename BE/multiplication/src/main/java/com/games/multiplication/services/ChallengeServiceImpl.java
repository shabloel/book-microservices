package com.games.multiplication.services;

import com.games.multiplication.domain.dto.AttemptCheckedDto;
import com.games.multiplication.domain.model.Attempt;
import com.games.multiplication.domain.dto.AttemptDTO;
import com.games.multiplication.domain.model.Uzer;
import com.games.multiplication.repos.ChallengeAttemptRepository;
import com.games.multiplication.repos.UserRepository;
import com.games.multiplication.serviceclient.GamificationServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChallengeServiceImpl implements ChallengeService {

    private final UserRepository userRepository;

    private final GamificationServiceClient gamificationServiceClient;

    private final ChallengeAttemptRepository challengeAttemptRepository;

    public ChallengeServiceImpl(UserRepository userRepository, GamificationServiceClient gamificationServiceClient, ChallengeAttemptRepository challengeAttemptRepository) {
        this.userRepository = userRepository;
        this.gamificationServiceClient = gamificationServiceClient;
        this.challengeAttemptRepository = challengeAttemptRepository;
    }

    @Override
    public Attempt verifyAttempt(AttemptDTO attemptDto) {

        boolean isCorrect =
                attemptDto.getFactorA() * attemptDto.getFactorB() == attemptDto.getGuess() ? true : false;

        Uzer user = userRepository.findByAlias(attemptDto.getUserAlias()).orElseGet(() -> {
            log.info("Storing a new user with alias [{}]", attemptDto.getUserAlias());
            return userRepository.save(new Uzer(attemptDto.getUserAlias()));
        });

        Attempt attempt =
                new Attempt(null,
                        user,
                        attemptDto.getFactorA(),
                        attemptDto.getFactorB(),
                        attemptDto.getGuess(),
                        isCorrect);
        Attempt storedAttempt = challengeAttemptRepository.save(attempt);

        //Send the challengeAttempt to the Leaderboardservice
        AttemptCheckedDto attemptCheckedDto = new AttemptCheckedDto(
                storedAttempt.getId(),
                storedAttempt.isCorrect(),
                storedAttempt.getFactorA(),
                storedAttempt.getFactorB(),
                storedAttempt.getUzer().getId(),
                storedAttempt.getUzer().getAlias()
        );

        var isSuccessful = gamificationServiceClient.sendAttempt(attemptCheckedDto);
        if(isSuccessful){
            log.info("Successfully sent the challenge attempt to the LeaderBoardService");
        } else {
            log.info("There was an error sending the challenge attempt to the LeaderBoardService");
        }
        return storedAttempt;
    }

    @Override
    public List<Attempt> getUserStats(String alias) {

        return challengeAttemptRepository.findTop10ByUzerAliasOrderByIdDesc(alias);
    }


}
