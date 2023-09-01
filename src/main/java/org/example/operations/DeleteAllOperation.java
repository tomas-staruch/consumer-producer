package org.example.operations;

import org.example.datasources.Repository;

public class DeleteAllOperation extends Operation {

    public DeleteAllOperation(Repository repository) {
        super(repository);
    }

    @Override
    public void execute() {
        this.repository.deleteAll();
    }
}
