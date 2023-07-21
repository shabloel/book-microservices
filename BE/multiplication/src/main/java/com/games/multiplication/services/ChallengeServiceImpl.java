package com.games.multiplication.services;

import com.games.multiplication.domain.model.ChallengeAttempt;
import com.games.multiplication.domain.dto.ChallengeAttemptDTO;
import com.games.multiplication.domain.model.Uzer;
import com.games.multiplication.repos.ChallengeAttemptRepository;
import com.games.multiplication.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChallengeServiceImpl implements ChallengeService {

    UserRepository userRepository;

    ChallengeAttemptRepository challengeAttemptRepository;

    public ChallengeServiceImpl(UserRepository userRepository, ChallengeAttemptRepository challengeAttemptRepository) {
        this.userRepository = userRepository;
        this.challengeAttemptRepository = challengeAttemptRepository;
    }

    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO challengeAttemptDto) {

        boolean isCorrect =
                challengeAttemptDto.getFactorA() * challengeAttemptDto.getFactorB() == challengeAttemptDto.getGuess() ? true : false;

        Uzer user = userRepository.findByAlias(challengeAttemptDto.getUserAlias()).orElseGet(() -> {
            log.info("Storing a new user with alias [{}]", challengeAttemptDto.getUserAlias());
            return userRepository.save(new Uzer(challengeAttemptDto.getUserAlias()));
        });

        ChallengeAttempt challengeAttempt =
                new ChallengeAttempt(null,
                        user,
                        challengeAttemptDto.getFactorA(),
                        challengeAttemptDto.getFactorB(),
                        challengeAttemptDto.getGuess(),
                        isCorrect);
        ChallengeAttempt storedAttempt = challengeAttemptRepository.save(challengeAttempt);
        return storedAttempt;
    }

    @Override
    public List<ChallengeAttempt> getUserStats(String alias) {

        return challengeAttemptRepository.findTop10ByUzerAliasOrderByIdDesc(alias);
    }


}
