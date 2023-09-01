package org.example.datasources;

import org.example.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryRepository implements Repository {
	private static Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
    private final Collection<User> storage = new CopyOnWriteArrayList<>();

    @Override
    public void add(User user) {
    	LOGGER.info("Add user to collection: {}", user);
    	
        storage.add(user);
    }

    @Override
    public void printAll() {
    	LOGGER.info("Print all users from collection:");
    	
        storage
            .stream()
            .forEach(user -> LOGGER.info("user: {}", user));
    }

    @Override
    public void deleteAll() {
    	LOGGER.info("Delete all users from collection: {}", storage.size());
        storage.clear();
    }
}
