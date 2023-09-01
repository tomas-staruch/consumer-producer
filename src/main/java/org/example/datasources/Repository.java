package org.example.datasources;

import org.example.models.User;

public interface Repository {

    void add(User user);

    void printAll();

    void deleteAll();
}
