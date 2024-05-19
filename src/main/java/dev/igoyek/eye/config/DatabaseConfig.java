package dev.igoyek.eye.config;

import dev.igoyek.eye.database.DatabaseType;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

public class DatabaseConfig extends OkaeriConfig {

    @Comment("# Database settings")
    @Comment("# Type of database server")
    public DatabaseType type = DatabaseType.H2;

    @Comment({"# SQL Drivers and ports:", "# MySQL (3306)", "# H2" })
    public String host = "localhost";
    public int port = 3306;
    public String database = "";
    public String password = "";
    public String username = "";
}
