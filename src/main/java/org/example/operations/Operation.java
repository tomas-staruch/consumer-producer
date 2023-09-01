package org.example.operations;

import org.example.datasources.Repository;

public abstract class Operation {
    protected final Repository repository;

    protected Operation(Repository repository) {
        this.repository = repository;
    }

    public abstract void execute();
}
