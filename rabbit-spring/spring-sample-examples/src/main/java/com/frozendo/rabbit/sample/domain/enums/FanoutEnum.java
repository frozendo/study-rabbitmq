package com.frozendo.rabbit.sample.domain.enums;

public enum FanoutEnum {

    SPRING_FANOUT_PRODUCT_EX("spring-sample-fanout-product-ex"),

    INVENTORY_QUEUE("spring-sample-fanout-inventory-queue"),
    PAYMENT_QUEUE("spring-sample-fanout-payment-queue");

    private String value;

    FanoutEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
