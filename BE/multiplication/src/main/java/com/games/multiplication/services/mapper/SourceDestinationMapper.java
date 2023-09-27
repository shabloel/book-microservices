package com.games.multiplication.services.mapper;

import com.games.multiplication.domain.dto.AttemptCheckedDto;
import com.games.multiplication.domain.dto.AttemptDTO;
import com.games.multiplication.domain.model.Attempt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SourceDestinationMapper {

    Attempt attemptDtoToAttemptEntity(AttemptDTO attemptDTO);
    AttemptDTO attemptToAttemptDto(Attempt attempt);

    @Mapping(target = "userAlias", source="attempt.uzer.alias")
    @Mapping(target = "userId", source="attempt.uzer.id")
    AttemptCheckedDto attemptToAttemptCheckedDto(Attempt attempt);

}
