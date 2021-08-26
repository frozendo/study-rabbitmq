package com.frozendo.rabbit.multi.domain.enums;

public enum HeaderEnum {

    SPRING_HEADER_PRODUCT_EX("spring-multi-header-product-ex"),

    SMALL_GIFT_QUEUE("spring-multi-header-small-gift-queue"),
    BIG_GIFT_QUEUE("spring-multi-header-big-gift-queue"),

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
