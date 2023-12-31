package com.games.multiplication.services.mapper;

import com.games.multiplication.domain.dto.AttemptCheckedEvent;
import com.games.multiplication.domain.dto.AttemptDTO;
import com.games.multiplication.domain.model.AttemptChecked;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SourceDestinationMapper {

    AttemptChecked attemptDtoToAttemptChecked(AttemptDTO attemptDTO);

    AttemptDTO attemptCheckedToAttemptDto(AttemptChecked attemptChecked);

    @Mapping(target = "userAlias", source = "attemptChecked.uzer.alias")
    @Mapping(target = "userId", source = "attemptChecked.uzer.id")
    AttemptCheckedEvent attempCheckedToAttemptCheckedEvent(AttemptChecked attemptChecked);

    List<AttemptCheckedEvent> attemptsCheckedToAttemptsCheckedEvent(List<AttemptChecked> attempts);

}
