package com.purplelove.http;

import java.time.Duration;
import java.util.function.Supplier;

public class RetryHandler {

    private final int maxAttempts;
    private final Duration initialDelay;
    private final Duration maxDelay;

    /**
     * Constructor with Sensible defaults
     * Max attempts: 3
     * Initial delay: 1 Second
     * Max delay: 10 Seconds
     */

    public RetryHandler() {
        this(3, Duration.ofSeconds(1), Duration.ofSeconds(10));
    }

    /**
     * Full constructor for customization
     */
    public RetryHandler(int maxAttempts, Duration initialDelay, Duration maxDelay) {
        this.maxAttempts = maxAttempts;
        this.initialDelay = initialDelay;
        this.maxDelay = maxDelay;
    }

    /**
     * Executes the given operation with retries.
     * If the operation succeeds, return the results.
     * If it fails after all attempts, throws the last exception.
     */

    public <T> T executeWithRetry(Supplier<T> operation) {
        Throwable lastException = null;
        long delayMillis = initialDelay.toMillis();

        for (int attempts = 1; attempts <= maxAttempts; attempts++) {
            try {
                return operation.get();
            } catch (Exception e) {
                lastException = e;

                if (attempts == maxAttempts) {
                    System.err.println("[Retry] All " + maxAttempts + " attempts failed.");
                    break;
                }

                // Log the failure and wait
                System.out.printf("[Retry] Attempt %d/%d failed: %s. Retrying in %d ms...%n",
                        attempts, maxAttempts, e.getMessage(), delayMillis);

                try {
                    Thread.sleep(delayMillis);
                } catch(InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Retry interrupted", interrupted);
                }

                delayMillis = Math.min(delayMillis * 2, maxDelay.toMillis());
            }
        }

        // If we get here, all attempts failed
        throw new RuntimeException("All " + maxAttempts + " attempts failed", lastException);
    } 

}