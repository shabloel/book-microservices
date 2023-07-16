package com.games.multiplication.repos;

import com.games.multiplication.domain.model.ChallengeAttempt;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChallengeAttemptRepository extends CrudRepository<ChallengeAttempt, Long> {

    /**
     * @return the last 10 attempts for a given user, identiefied by their alias.
     */
    List<ChallengeAttempt> findTop10ByUserAliasOrderByIdDesc(String alias);
}
