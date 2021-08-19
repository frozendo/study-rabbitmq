package com.frozendo.rabbit.multi.domain.enums;

public enum FanoutEnum {

    JAVA_FANOUT_PRODUCT_EX("java-fanout-product-ex"),
    INVENTORY_QUEUE("java-fanout-inventory-queue"),
    PAYMENT_QUEUE("java-fanout-payment-queue");

    private String value;

    FanoutEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
