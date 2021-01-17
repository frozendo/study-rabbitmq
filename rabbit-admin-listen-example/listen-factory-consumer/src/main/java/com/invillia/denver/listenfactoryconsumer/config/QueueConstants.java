package com.invillia.denver.listenfactoryconsumer.config;

public class QueueConstants {

    public static final String ADMIN_LISTEN_DIRECT_QUEUE = "admin-listen-direct-queue";
    public static final String OTHER_ADMIN_LISTEN_DIRECT_QUEUE = "other-admin-listen-direct-queue";

    public static final String ADMIN_LISTEN_FANOUT_QUEUE = "admin-listen-fanout-queue";
    public static final String OTHER_ADMIN_LISTEN_FANOUT_QUEUE = "other-admin-listen-fanout-queue";

    public static final String ADMIN_LISTEN_HEADER_QUEUE = "admin-listen-header-queue";
    public static final String OTHER_ADMIN_LISTEN_HEADER_QUEUE = "other-admin-listen-header-queue";

    public static final String ADMIN_LISTEN_TOPIC_QUEUE = "admin-listen-topic-queue";
    public static final String OTHER_ADMIN_LISTEN_TOPIC_QUEUE = "other-admin-listen-topic-queue";
    public static final String THIRD_ADMIN_LISTEN_TOPIC_QUEUE = "third-admin-listen-topic-queue";

    public static final String LOG_MESSAGE_ROUTING_KEY = "Consume QUEUE {}, with ROUTING_KEY = {}, with VALUE = {}";
    public static final String LOG_MESSAGE = "Consume QUEUE {}, with VALUE = {}";
}
