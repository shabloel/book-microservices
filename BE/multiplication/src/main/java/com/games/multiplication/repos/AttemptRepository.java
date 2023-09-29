package com.games.multiplication.repos;

import com.games.multiplication.domain.model.AttemptChecked;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptRepository extends CrudRepository<AttemptChecked, Long> {

    /**
     * @return the last 10 attempts for a given user, identiefied by their alias.
     */
    List<AttemptChecked> findTop10ByUzerAliasOrderByIdDesc(String alias);
}
