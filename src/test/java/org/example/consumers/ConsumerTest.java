package org.example.consumers;

import static org.junit.Assert.assertEquals;

import org.example.datasources.Repository;
import org.example.datasources.TestingRepository;
import org.example.datasources.ThreadAwareTestingRepository;
import org.example.models.User;
import org.example.operations.AddOperation;
import org.example.operations.DeleteAllOperation;
import org.example.operations.Operation;
import org.example.operations.PrintAllOperation;
import org.junit.Test;

import java.util.concurrent.*;

public class ConsumerTest {

    @Test
    public void testConsumer() throws InterruptedException {
        BlockingQueue<Operation> queue = new LinkedBlockingQueue<>();
        CountDownLatch latch = new CountDownLatch(3);

        Thread thread = new Thread(new Consumer(queue, latch));
        ThreadAwareTestingRepository repository = new ThreadAwareTestingRepository(thread, latch);

        queue.add(new AddOperation(repository, null));
        queue.add(new PrintAllOperation(repository));
        queue.add(new DeleteAllOperation(repository));

        thread.start();
        thread.join();

        assertEquals("Consumer should take out all operations from the queue", 0, queue.size());
        assertEquals("Consumer should decrement the latch", 0, latch.getCount());
        assertEquals("Consumer should invoke add method in repository", 1, repository.getAdded());
        assertEquals("Consumer should invoke printAll method in repository", 1, repository.getPrinted());
        assertEquals("Consumer should invoke deleteAll method in repository", 1, repository.getDeleted());
    }
}
