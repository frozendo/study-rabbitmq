package com.frozendo.rabbit.multi.domain.enums;

public enum FanoutEnum {

    SPRING_FANOUT_PRODUCT_EX("spring-multi-fanout-product-ex"),

    INVENTORY_QUEUE("spring-multi-fanout-inventory-queue"),
    PAYMENT_QUEUE("spring-multi-fanout-payment-queue");

    private String value;

    FanoutEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
