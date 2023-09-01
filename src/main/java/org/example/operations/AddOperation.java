package org.example.operations;

import org.example.datasources.Repository;
import org.example.models.User;

public class AddOperation extends Operation {
    private final User user;

    public AddOperation(Repository repository, User user) {
        super(repository);
        this.user = user;
    }

    @Override
    public void execute() {
        this.repository.add(user);
    }
}
