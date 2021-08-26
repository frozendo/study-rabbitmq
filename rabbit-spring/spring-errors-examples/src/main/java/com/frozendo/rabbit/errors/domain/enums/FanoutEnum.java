package com.frozendo.rabbit.errors.domain.enums;

public enum FanoutEnum {

    SPRING_FANOUT_PRODUCT_EX("spring-errors-fanout-product-ex"),

    INVENTORY_QUEUE("spring-errors-fanout-inventory-queue"),
    PAYMENT_QUEUE("spring-errors-fanout-payment-queue"),

    INVENTORY_DELAYED_QUEUE("spring-errors-fanout-inventory-delayed-queue"),
    PAYMENT_DELAYED_QUEUE("spring-errors-fanout-payment-delayed-queue"),

    INVENTORY_DLQ_QUEUE("spring-errors-fanout-inventory-dlq-queue"),
    PAYMENT_DLQ_QUEUE("spring-errors-fanout-payment-dlq-queue"),

    INVENTORY_DLQ_KEY("inventory-dlq-key"),
    PAYMENT_DLQ_KEY("payment-dlq-key");

    private String value;

    FanoutEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
