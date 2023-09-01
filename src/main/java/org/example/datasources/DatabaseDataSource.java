package org.example.datasources;

import javax.sql.DataSource;

@FunctionalInterface
public interface DatabaseDataSource {
    DataSource create();
}
