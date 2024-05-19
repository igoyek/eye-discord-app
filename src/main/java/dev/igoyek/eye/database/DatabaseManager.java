package dev.igoyek.eye.database;

import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.zaxxer.hikari.HikariDataSource;
import dev.igoyek.eye.config.DatabaseConfig;

import java.io.File;
import java.sql.SQLException;

public class DatabaseManager {

    private final DatabaseConfig config;
    private final File folder;
    private HikariDataSource hikariDataSource;
    private ConnectionSource connectionSource;

    public DatabaseManager(DatabaseConfig config, File folder) {
        this.config = config;
        this.folder = folder;
    }

    public void connect() throws SQLException {

        this.hikariDataSource = new HikariDataSource();

        this.hikariDataSource.addDataSourceProperty("cachePrepStmts", true);
        this.hikariDataSource.addDataSourceProperty("prepStmtCacheSize", 250);
        this.hikariDataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        this.hikariDataSource.addDataSourceProperty("useServerPrepStmts", true);

        this.hikariDataSource.setMaximumPoolSize(5);
        this.hikariDataSource.setUsername(this.config.username);
        this.hikariDataSource.setPassword(this.config.password);

        switch (this.config.type) {
            case MYSQL -> {
                this.hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
                this.hikariDataSource.setJdbcUrl("jdbc:mysql://" + this.config.host + ":" + this.config.port + "/" + this.config.database);
            }
            case H2 -> {
                this.hikariDataSource.setDriverClassName("org.h2.Driver");
                this.hikariDataSource.setJdbcUrl("jdbc:h2:" + this.folder.getAbsolutePath() + "/database");
            }
            default -> throw new SQLException("Database type not supported: " + this.config.type);
        }

        this.connectionSource = new DataSourceConnectionSource(this.hikariDataSource, this.hikariDataSource.getJdbcUrl());
    }

    public void closeConnection() throws Exception {
        this.hikariDataSource.close();
        this.connectionSource.close();
    }

    public ConnectionSource getConnectionSource() {
        return this.connectionSource;
    }
}
