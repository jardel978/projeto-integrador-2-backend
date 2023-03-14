package com.dmh.msaccounts.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardsTypeEnum {

    DEBITO(0, "debito"),
    CREDITO(1, "credito"),
    DEBITO_CREDITO(2, "debito/credito");

    private final int value;
    private final String description;


}
