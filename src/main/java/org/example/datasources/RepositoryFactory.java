package org.example.datasources;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public final class RepositoryFactory {
    public enum Type { H2, IN_MEMORY };

    private RepositoryFactory() {}

    /**
     * Return type of Repository based on given type.
     */
    public static Repository create(Type type) {
        switch (type) {
            case H2:
                // optionally file persistent version can be used: "jdbc:h2:./data/test;INIT=runscript from './data/init.sql'"
                return new DatabaseRepository(
                        () -> JdbcConnectionPool
                                .create("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;INIT=runscript from './data/init.sql'",
                                        "sa",
                                        "")
                );
            case IN_MEMORY:
                return new InMemoryRepository();
            default:
                throw new IllegalArgumentException("Unsupported repository type");
        }
    }
}
