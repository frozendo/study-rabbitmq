package com.frozendo.rabbit.sample.domain.enums;

public enum DirectEnum {

    JAVA_DIRECT_PRODUCT_EX("java-sample-direct-product-ex"),

    BIG_QUANTITY_QUEUE("java-sample-direct-big-quantity-queue"),
    SMALL_QUANTITY_QUEUE("java-sample-direct-small-quantity-queue"),

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
