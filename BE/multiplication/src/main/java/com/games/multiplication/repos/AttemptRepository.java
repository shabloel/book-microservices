package com.games.multiplication.repos;

import com.games.multiplication.domain.model.Attempt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptRepository extends CrudRepository<Attempt, Long> {

    /**
     * @return the last 10 attempts for a given user, identiefied by their alias.
     */
    List<Attempt> findTop10ByUzerAliasOrderByIdDesc(String alias);
}
