package com.frozendo.learnrabbit.domain;

public enum TopicEnum {

    JAVA_TOPIC_PRODUCT_EX("java-topic-product-ex"),
    PRODUCT_REGISTER_QUEUE("product-register-queue"),
    PRODUCT_REGISTER_SPORT_QUEUE("product-register-sport-queue"),
    PRODUCT_REGISTER_ELECTRONIC_QUEUE("product-register-electronic-queue"),
    DEPARTMENT_BINDING_KEY("department.#"),
    DEPARTMENT_SPORT_BINDING_KEY("department.sport.#"),
    DEPARTMENT_PROMOTION_BINDING_KEY("department.*.promotion"),
    GENERAL_ROUTING_KEY("department."),
    SPORT_ROUTING_KEY("department.sport."),
    SPORT_PROMOTION_ROUTING_KEY("department.sport.promotion"),
    ELECTRONIC_PROMOTION_ROUTING_KEY("department.electronic.promotion");

    private String value;

    private TopicEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
