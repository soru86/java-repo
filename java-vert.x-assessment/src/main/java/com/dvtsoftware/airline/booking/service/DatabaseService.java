package com.dvtsoftware.airline.booking.service;

import io.vertx.core.Vertx;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;

public class DatabaseService {
    protected Vertx vertx;
    protected Pool client;

    public DatabaseService(Vertx vertx) {
        this.vertx = vertx;
        this.client = JDBCPool.pool(
                vertx,
                new io.vertx.jdbcclient.JDBCConnectOptions()
                        .setJdbcUrl("jdbc:h2:mem:airline_booking;DB_CLOSE_DELAY=-1")
                        .setUser("sa")
                        .setPassword(""),
                new PoolOptions().setMaxSize(20));
    }

    public Pool getClient() {
        return client;
    }
}