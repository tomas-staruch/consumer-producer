package org.example.operations;

import org.example.datasources.Repository;

public class PrintAllOperation extends Operation {

    public PrintAllOperation(Repository repository) {
        super(repository);
    }

    @Override
    public void execute() {
        this.repository.printAll();
    }
}
