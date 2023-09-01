package org.example.operations;

import static org.junit.Assert.assertEquals;

import org.example.datasources.TestingRepository;
import org.example.models.User;
import org.junit.Test;

public class AddOperationTest {

    @Test
    public void testAdd() {
        TestingRepository repository = new TestingRepository();

        AddOperation operation = new AddOperation(repository, new User(1, "a1", "John"));
        operation.execute();

        assertEquals("Should invoke add method in repository", 1, repository.getAdded());
    }
}
