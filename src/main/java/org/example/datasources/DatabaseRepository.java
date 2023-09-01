package org.example.datasources;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.example.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;

public class DatabaseRepository implements Repository {
    private static Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final DataSource dataSource;

    public DatabaseRepository(DatabaseDataSource databaseDataSource) {
        this.dataSource = databaseDataSource.create();
    }

    @Override
    public synchronized void add(User user) {
        LOGGER.info("Add user {} to DB", user);
        
        try {
            QueryRunner qr = new QueryRunner(this.dataSource);
            qr.update("INSERT INTO SUSERS (USER_ID, USER_GUID, USER_NAME) VALUES (?, ?, ?)",
                        user.getId(),
                        user.getGuid(),
                        user.getName());
        } catch (SQLException e) {
        	LOGGER.error(String.format("An SQLException exception occurred when inserting user %d: %s", user.getId(), e), e);
        }
    }

    @Override
    public synchronized void printAll() {
        LOGGER.info("Print all users from DB:");
        
        try {
            new QueryRunner(this.dataSource)
                    .query("SELECT * FROM SUSERS", new ArrayListHandler())
                    .stream()
                    .forEach(objects -> LOGGER.info("user: {}", new User(objects)));
        } catch (SQLException e) {
        	LOGGER.error(String.format("An SQLException exception occurred when getting users: %s", e), e);
        }
    }

    @Override
    public synchronized void deleteAll() {
        LOGGER.info("Delete all users from DB");
        try {
            new QueryRunner(this.dataSource)
                    .update("TRUNCATE TABLE SUSERS");
        } catch (SQLException e) {
        	LOGGER.error(String.format("An SQLException exception occurred when deleting users: %s", e), e);
        }

    }
}
