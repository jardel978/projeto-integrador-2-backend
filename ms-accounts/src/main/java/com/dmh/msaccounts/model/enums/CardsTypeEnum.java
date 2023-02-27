package com.dmh.msaccounts.model.enums;

import lombok.Getter;

@Getter
public enum CardsTypeEnum {

    DEBITO(0,"debito"),CREDITO(1,"credito"), DEBITO_CREDITO(2,"debito/credito");

    private final int value;
    private final String description;

    CardsTypeEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

}
