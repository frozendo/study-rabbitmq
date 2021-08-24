package com.frozendo.rabbit.errors.domain.enums;

public enum DirectEnum {

    JAVA_DIRECT_PRODUCT_EX("java-errors-direct-product-ex"),

    BIG_QUANTITY_QUEUE("java-errors-direct-big-quantity-queue"),
    SMALL_QUANTITY_QUEUE("java-errors-direct-small-quantity-queue"),

    BIG_QUANTITY_DELAYED_QUEUE("java-errors-direct-big-quantity-delayed-queue"),
    SMALL_QUANTITY_DELAYED_QUEUE("java-errors-direct-small-quantity-delayed-queue"),

    BIG_QUANTITY_DLQ_QUEUE("java-errors-direct-big-quantity-dlq-queue"),
    SMALL_QUANTITY_DLQ_QUEUE("java-errors-direct-small-quantity-dlq-queue"),

    JAVA_ERRORS_BIG_QUANTITY_DLQ_KEY("big-quantity-dlq-key"),
    JAVA_ERRORS_SMALL_QUANTITY_DLQ_KEY("small-quantity-dlq-key"),

    BIG_QUANTITY_KEY("big-quantity-key"),
    SMALL_QUANTITY_KEY("small-quantity-key");

    private String value;

    DirectEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
