package com.frozendo.rabbit.errors.domain.enums;

public enum TopicEnum {

    SPRING_TOPIC_PRODUCT_EX("spring-errors-topic-product-ex"),

    PRODUCT_REGISTER_QUEUE("spring-errors-topic-register-queue"),
    SPORT_DEPARTMENT_QUEUE("spring-errors-topic-sport-department-queue"),
    PROMOTION_QUEUE("spring-errors-topic-promotion-queue"),

    PRODUCT_REGISTER_DELAYED_QUEUE("spring-errors-topic-register-delayed-queue"),
    SPORT_DEPARTMENT_DELAYED_QUEUE("spring-errors-topic-sport-department-delayed-queue"),
    PROMOTION_DELAYED_QUEUE("spring-errors-topic-promotion-delayed-queue"),

    PRODUCT_REGISTER_DLQ_QUEUE("spring-errors-topic-register-dql-queue"),
    SPORT_DEPARTMENT_DLQ_QUEUE("spring-errors-topic-sport-department-dql-queue"),
    PROMOTION_DLQ_QUEUE("spring-errors-topic-promotion-dql-queue"),

    DEPARTMENT_BINDING_KEY("department.#"),
    DEPARTMENT_SPORT_BINDING_KEY("department.sport.#"),
    DEPARTMENT_PROMOTION_BINDING_KEY("department.*.promotion"),

    REGISTER_DLQ_KEY("register-dlq-key"),
    SPORT_DLQ_KEY("sport-dlq-key"),
    PROMOTION_DLQ_KEY("promotion-dlq-key"),

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
