package com.frozendo.learnrabbit.domain;

public enum DirectEnum {

    JAVA_DIRECT_PRODUCT_EX("java-direct-product-ex"),
    ODD_QUANTITY_QUEUE("odd-quantity-queue"),
    EVEN_QUANTITY_QUEUE("even-quantity-queue"),
    ODD_VALUE_KEY("odd-value-key"),
    EVEN_VALUE_KEY("even-value-key");

    private String value;

    private DirectEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
