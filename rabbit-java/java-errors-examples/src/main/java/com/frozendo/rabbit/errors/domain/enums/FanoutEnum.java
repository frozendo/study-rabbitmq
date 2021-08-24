package com.frozendo.rabbit.errors.domain.enums;

public enum FanoutEnum {

    JAVA_FANOUT_PRODUCT_EX("java-errors-fanout-product-ex"),

    INVENTORY_QUEUE("java-errors-fanout-inventory-queue"),
    PAYMENT_QUEUE("java-errors-fanout-payment-queue"),

    INVENTORY_DELAYED_QUEUE("java-errors-fanout-inventory-delayed-queue"),
    PAYMENT_DELAYED_QUEUE("java-errors-fanout-payment-delayed-queue"),

    INVENTORY_DLQ_QUEUE("java-errors-fanout-inventory-dlq-queue"),
    PAYMENT_DLQ_QUEUE("java-errors-fanout-payment-dlq-queue"),

    JAVA_ERRORS_INVENTORY_DLQ_KEY("inventory-dlq-key"),
    JAVA_ERRORS_PAYMENT_DLQ_KEY("payment-dlq-key");

    private String value;

    FanoutEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
