package com.invillia.denver.sampleconsumer.config;

public class QueueConstants {

    public static final String SAMPLE_MANUAL_DIRECT_QUEUE = "sample-manual-direct-queue";
    public static final String OTHER_SAMPLE_MANUAL_DIRECT_QUEUE = "other-sample-manual-direct-queue";

    public static final String SAMPLE_MANUAL_HEADER_QUEUE = "sample-manual-header-queue";
    public static final String OTHER_SAMPLE_MANUAL_HEADER_QUEUE = "other-sample-manual-header-queue";

    public static final String SAMPLE_BEAN_FANOUT_QUEUE = "sample-bean-fanout-queue";
    public static final String OTHER_SAMPLE_BEAN_FANOUT_QUEUE = "other-sample-bean-fanout-queue";

    public static final String SAMPLE_BEAN_TOPIC_QUEUE = "sample-bean-topic-queue";
    public static final String OTHER_SAMPLE_BEAN_TOPIC_QUEUE = "other-sample-bean-topic-queue";
    public static final String THIRD_SAMPLE_BEAN_TOPIC_QUEUE = "third-sample-bean-topic-queue";

    public static final String LOG_MESSAGE_ROUTING_KEY = "Consume QUEUE {}, with ROUTING_KEY = {}, with VALUE = {}";
    public static final String LOG_MESSAGE = "Consume QUEUE {}, with VALUE = {}";

    private QueueConstants() {}

}
