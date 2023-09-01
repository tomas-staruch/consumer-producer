package org.example.consumers;

import org.example.operations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Consumer implements Runnable {
	private static Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
	
    private final BlockingQueue<Operation> queue;
    private final CountDownLatch counter;

    public Consumer(BlockingQueue<Operation> queue, CountDownLatch counter) {
        this.queue = queue;
        this.counter = counter;
    }

    public void run() {
        try {
            while (true) {
                consume(queue.take());
                counter.countDown();
            }
        } catch (InterruptedException ex) {
        	LOGGER.warn("Consumer {} was interrupted", Thread.currentThread().getName());
        }
    }

    private void consume(Operation operation) {
        operation.execute();
    }
}
