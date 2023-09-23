package com.games.multiplication.repos;

import com.games.multiplication.domain.model.Uzer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Uzer, Long> {

    Optional<Uzer> findByAlias(final String alias);

    List<Uzer> findAllByIdIn(final List<Long> ids);
}
