package com.frozendo.rabbit.errors.domain.enums;

public enum HeaderEnum {

    JAVA_HEADER_PRODUCT_EX("java-errors-header-product-ex"),

    SMALL_GIFT_QUEUE("java-errors-header-small-gift-queue"),
    BIG_GIFT_QUEUE("java-errors-header-big-gift-queue"),

    SMALL_GIFT_DLQ_QUEUE("java-errors-header-small-gift-dlq-queue"),
    BIG_GIFT_DLQ_QUEUE("java-errors-header-big-gift-dlq-queue"),

    JAVA_ERRORS_SMALL_GIFT_DLQ_KEY("java-errors-small-gift-dlq-key"),
    JAVA_ERRORS_BIG_GIFT_DLQ_KEY("java-errors-big-gift-dlq-key"),

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
