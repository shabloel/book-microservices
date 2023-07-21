package com.games.multiplication.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Uzer {

    @Id
    @GeneratedValue
    private Long id;
    private String alias;

    public Uzer(final String userAlias) {
        this(null, userAlias);
    }


}
