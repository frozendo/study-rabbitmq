package com.frozendo.rabbit.errors.domain.enums;

public enum HeaderEnum {

    SPRING_HEADER_PRODUCT_EX("spring-errors-header-product-ex"),

    SMALL_GIFT_QUEUE("spring-errors-header-small-gift-queue"),
    BIG_GIFT_QUEUE("spring-errors-header-big-gift-queue"),

    SMALL_GIFT_DELAYED_QUEUE("spring-errors-header-small-gift-delayed-queue"),
    BIG_GIFT_DELAYED_QUEUE("spring-errors-header-big-gift-delayed-queue"),

    SMALL_GIFT_DLQ_QUEUE("spring-errors-header-small-gift-dlq-queue"),
    BIG_GIFT_DLQ_QUEUE("spring-errors-header-big-gift-dlq-queue"),

    SMALL_GIFT_DLQ_KEY("small-gift-dlq-key"),
    BIG_GIFT_DLQ_KEY("big-gift-dlq-key"),

    PRICE_HEADER("price-header"),
    QUANTITY_HEADER("quantity-header"),

    X_MATCH_HEADER_KEY("x-match");

    private String value;

    HeaderEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
