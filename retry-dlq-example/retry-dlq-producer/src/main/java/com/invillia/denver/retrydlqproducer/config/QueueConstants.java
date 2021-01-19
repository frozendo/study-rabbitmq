package com.invillia.denver.retrydlqproducer.config;

public class QueueConstants {

    private QueueConstants() {}

    public static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    public static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    public static final String RETRY_DIRECT_EXCHANGE = "retry-direct-exchange";
    public static final String RETRY_DLX_DIRECT_EXCHANGE = "retry-dlx-direct-exchange";

    public static final String LOOP_EXAMPLE_QUEUE = "loop-example-queue";
    public static final String BINDING_LOOP_QUEUE = "binding.loop.queue";

    public static final String REJECT_DISCARD_QUEUE = "reject-discard-queue";
    public static final String BINDING_REJECT_QUEUE = "binding.reject.queue";

    public static final String REJECT_SAVE_QUEUE = "reject-save-queue";
    public static final String REJECT_SAVE_DLQ = "reject-save-dlq";
    public static final String BINDING_REJECT_SAVE_QUEUE = "binding.reject.save.queue";
    public static final String BINDING_REJECT_SAVE_DLQ = "binding.reject.save.dlq";

    public static final String EXECUTE_RETRY_QUEUE = "execute-retry-queue";
    public static final String EXECUTE_RETRY_DELAYED = "execute-retry-delayed";
    public static final String EXECUTE_RETRY_DLQ = "execute-retry-dlq";
    public static final String BINDING_EXECUTE_QUEUE = "binding.execute.queue";
    public static final String BINDING_EXECUTE_DLQ = "binding.execute.dlq";
}
