package com.frozendo.rabbit.multi.domain.enums;

public enum TopicEnum {

    SPRING_TOPIC_PRODUCT_EX("spring-multi-topic-product-ex"),

    PRODUCT_REGISTER_QUEUE("spring-multi-topic-register-queue"),
    SPORT_DEPARTMENT_QUEUE("spring-multi-topic-sport-department-queue"),
    PROMOTION_QUEUE("spring-multi-topic-promotion-queue"),

    DEPARTMENT_BINDING_KEY("department.#"),
    DEPARTMENT_SPORT_BINDING_KEY("department.sport.#"),
    DEPARTMENT_PROMOTION_BINDING_KEY("department.*.promotion"),

    GENERAL_ROUTING_KEY("department."),
    SPORT_ROUTING_KEY("department.sport."),
    SPORT_PROMOTION_ROUTING_KEY("department.sport.promotion"),
    ELECTRONIC_PROMOTION_ROUTING_KEY("department.electronic.promotion");

    private String value;

    TopicEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
