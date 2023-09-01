package org.example.datasources;

import org.example.datasources.Repository;
import org.example.models.User;

public class TestingRepository implements Repository {
    private int added = 0;
    private int printed = 0;
    private int deleted = 0;

    @Override
    public void add(User user) {
        this.added++;
    }

    @Override
    public void printAll() {
        printed++;
    }

    @Override
    public void deleteAll() {
        deleted++;
    }

    public int getAdded() {
        return added;
    }

    public int getDeleted() {
        return deleted;
    }

    public int getPrinted() {
        return printed;
    }
}
