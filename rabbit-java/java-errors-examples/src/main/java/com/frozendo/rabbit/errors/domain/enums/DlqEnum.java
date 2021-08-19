package com.frozendo.rabbit.errors.domain.enums;

public enum DlqEnum {

    JAVA_ERRORS_DLQ_EX("java-errors-dql-ex");

    private String value;

    DlqEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
