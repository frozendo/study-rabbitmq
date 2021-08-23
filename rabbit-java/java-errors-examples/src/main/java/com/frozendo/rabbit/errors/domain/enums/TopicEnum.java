package com.frozendo.rabbit.errors.domain.enums;

public enum TopicEnum {

    JAVA_TOPIC_PRODUCT_EX("java-errors-topic-product-ex"),
    PRODUCT_REGISTER_QUEUE("java-errors-topic-register-queue"),
    SPORT_DEPARTMENT_QUEUE("java-errors-topic-sport-department-queue"),
    PROMOTION_QUEUE("java-errors-topic-promotion-queue"),


    PRODUCT_REGISTER_DELAYED_QUEUE("java-errors-topic-register-delayed-queue"),
    SPORT_DEPARTMENT_DELAYED_QUEUE("java-errors-topic-sport-department-delayed-queue"),
    PROMOTION_DELAYED_QUEUE("java-errors-topic-promotion-delayed-queue"),

    PRODUCT_REGISTER_DLQ_QUEUE("java-errors-topic-register-dql-queue"),
    SPORT_DEPARTMENT_DLQ_QUEUE("java-errors-topic-sport-department-dql-queue"),
    PROMOTION_DLQ_QUEUE("java-errors-topic-promotion-dql-queue"),

    DEPARTMENT_BINDING_KEY("department.#"),
    DEPARTMENT_SPORT_BINDING_KEY("department.sport.#"),
    DEPARTMENT_PROMOTION_BINDING_KEY("department.*.promotion"),

    JAVA_ERRORS_REGISTER_DLQ_KEY("java-errors-register-dlq-key"),
    JAVA_ERRORS_SPORT_DLQ_KEY("java-errors-sport-dlq-key"),
    JAVA_ERRORS_PROMOTION_DLQ_KEY("java-errors-promotion-dlq-key"),

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
