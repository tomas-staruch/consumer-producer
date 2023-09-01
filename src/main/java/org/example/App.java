package org.example;

import org.example.consumers.Consumer;
import org.example.datasources.Repository;
import org.example.datasources.RepositoryFactory;
import org.example.datasources.RepositoryFactory.Type;
import org.example.models.User;
import org.example.operations.AddOperation;
import org.example.operations.DeleteAllOperation;
import org.example.operations.Operation;
import org.example.operations.PrintAllOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class App {
	private static Logger LOGGER = LoggerFactory.getLogger(App.class);
	
    /**
     * Demonstration of processing of commands in FIFO queue using producer - consumer pattern. 
     * The commands are processed in order as long as there is just a single consumer. When there are two or more, the order is not guaranteed.
     *  
     *
     * Note: in a case when there are more than one consumer, then operations may not be performed
     * in order as they were added into queue.
     *
     * If the order of operations has to be preserved, then SynchronousQueue can be used. It guarantees
     * that "each insert operation must wait for a corresponding remove operation". Only one operation
     * is processed in a time.
     */
    public static void main( String[] args ) {
        Repository repository = RepositoryFactory.create(Type.H2);

        // the operations are defined in a sequence therefore a producer as separate class is not implemented
        BlockingQueue<Operation> queue = new LinkedBlockingQueue<>();
        queue.add(new AddOperation(repository, new User(1, "a1", "Robert")));
        queue.add(new AddOperation(repository, new User(2, "a2", "Martin")));
        queue.add(new PrintAllOperation(repository));
        queue.add(new DeleteAllOperation(repository));

        // CountDownLatch is decreased every time when a consumer processed a command
        CountDownLatch latch = new CountDownLatch(queue.size());

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        service.execute(new Consumer(queue, latch));
        service.execute(new Consumer(queue, latch));
        service.execute(new Consumer(queue, latch));

        shutdown(latch, service);
    }

    /**
     * Shutdown ExecutorService when there are no more tasks in the queue.
     */
    private static void shutdown(CountDownLatch latch, ExecutorService service) {
        try {
            latch.await();
        } catch (InterruptedException e) {
        	LOGGER.warn("CountDownLatch {} was interrupted while waiting", Thread.currentThread().getName());
        } finally {
            service.shutdownNow();
        }
    }
}
