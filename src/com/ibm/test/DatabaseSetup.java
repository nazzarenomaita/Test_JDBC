package com.ibm.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

public class DatabaseSetup {
    private DataSource dataSource;

    public DatabaseSetup(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setupDatabase() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
        	statement.execute("CREATE DATABASE IF NOT EXISTS biblioteca");
            statement.execute("CREATE TABLE IF NOT EXISTS U (" +
                    "id INT NOT NULL, " +
                    "Cognome VARCHAR(255), " +
                    "Nome VARCHAR(255), " +
                    "PRIMARY KEY (id))");

            statement.execute("CREATE TABLE IF NOT EXISTS L (" +
                    "id VARCHAR(255) NOT NULL, " +
                    "Titolo VARCHAR(255), " +
                    "Autore VARCHAR(255), " +
                    "PRIMARY KEY (id))");

            statement.execute("CREATE TABLE IF NOT EXISTS P (" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "inizio DATE, " +
                    "fine DATE, " +
                    "id_U INT, " +
                    "id_L VARCHAR(255), " +
                    "PRIMARY KEY (id), " +
                    "FOREIGN KEY (id_U) REFERENCES U(id), " +
                    "FOREIGN KEY (id_L) REFERENCES L(id))");
        }
    }

}
