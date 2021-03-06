package net.jcip.examples;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UnsafeCounterTest extends ConcurrentHarness {

    private UnsafeCounter counter;

    @Before
    public void initCounters() {
        counter = new UnsafeCounter();
        counter.reset();
    }

    @Test
    public void testUnsafeIncrement() {
        try {
            for (int i = 0; i < CYCLES; i++) {
                executor.submit(new Runnable() {
                    public void run() { counter.increment(); }
                });
            }
        } catch (RejectedExecutionException e) {
            fail("Execution rejected");
        }
    }

    @Override
    protected void afterAllTasks() {
        assertEquals(CYCLES, counter.getCounter());
    }
}
