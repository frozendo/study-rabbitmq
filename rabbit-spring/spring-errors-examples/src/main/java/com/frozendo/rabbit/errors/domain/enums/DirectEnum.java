package com.frozendo.rabbit.errors.domain.enums;

public enum DirectEnum {

    SPRING_DIRECT_PRODUCT_EX("spring-errors-direct-product-ex"),

    BIG_QUANTITY_QUEUE("spring-errors-direct-big-quantity-queue"),
    SMALL_QUANTITY_QUEUE("spring-errors-direct-small-quantity-queue"),

    BIG_QUANTITY_DELAYED_QUEUE("spring-errors-direct-big-quantity-delayed-queue"),
    SMALL_QUANTITY_DELAYED_QUEUE("spring-errors-direct-small-quantity-delayed-queue"),

    BIG_QUANTITY_DLQ_QUEUE("spring-errors-direct-big-quantity-dlq-queue"),
    SMALL_QUANTITY_DLQ_QUEUE("spring-errors-direct-small-quantity-dlq-queue"),

    BIG_QUANTITY_DLQ_KEY("big-quantity-dlq-key"),
    SMALL_QUANTITY_DLQ_KEY("small-quantity-dlq-key"),

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
