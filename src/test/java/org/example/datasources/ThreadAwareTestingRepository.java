package org.example.datasources;

import org.example.models.User;

import java.util.concurrent.CountDownLatch;

/**
 * Repository which interrupts given thread after the last operation.
 */
public class ThreadAwareTestingRepository extends TestingRepository {
    private final Thread thread;
    private final CountDownLatch latch;

    public ThreadAwareTestingRepository(Thread thread, CountDownLatch latch) {
        this.thread = thread;
        this.latch = latch;
    }

    @Override
    public void add(User user) {
        super.add(user);
        interrupt();
    }

    @Override
    public void printAll() {
        super.printAll();
        interrupt();
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
        interrupt();
    }

    private void interrupt() {
        // interrupt consumer after the last operation
        if(this.latch.getCount() == 1)
            this.thread.interrupt();
    }
}
