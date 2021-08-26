package com.frozendo.rabbit.errors.domain.enums;

public enum DlqEnum {

    SPRING_ERRORS_DLQ_EX("spring-errors-dql-ex");

    private String value;

    DlqEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
