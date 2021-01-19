package com.invillia.denver.retrydlqconsumer.config;

public class QueueConstants {
    public static final String LOOP_EXAMPLE_QUEUE = "loop-example-queue";
    public static final String REJECT_DISCARD_QUEUE = "reject-discard-queue";
    public static final String REJECT_SAVE_QUEUE = "reject-save-queue";
    public static final String EXECUTE_RETRY_QUEUE = "execute-retry-queue";
    public static final String EXECUTE_RETRY_DELAYED = "execute-retry-delayed";
    
    public static final String LOG_MESSAGE_ROUTING_KEY = "Consume QUEUE {}, with ROUTING_KEY = {}, with VALUE = {}";
    public static final String LOG_MESSAGE_X_DEATH = "Consume QUEUE {}, with ROUTING_KEY = {}, with X-DEATH = {}, with VALUE = {}";
}
