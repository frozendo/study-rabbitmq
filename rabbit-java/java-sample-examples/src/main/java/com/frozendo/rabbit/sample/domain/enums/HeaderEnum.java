package com.frozendo.rabbit.sample.domain.enums;

public enum HeaderEnum {

    JAVA_HEADER_PRODUCT_EX("java-sample-header-product-ex"),
    SMALL_GIFT_QUEUE("java-sample-header-small-gift-queue"),
    BIG_GIFT_QUEUE("java-sample-header-big-gift-queue"),
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
