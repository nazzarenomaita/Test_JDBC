package com.ibm.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DataInserter {
    private DataSource dataSource;

    public DataInserter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertData() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
        	
            String insertUserSQL = "INSERT INTO U (id, Cognome, Nome) VALUES (?, ?, ?)";
            
            try (PreparedStatement ps = connection.prepareStatement(insertUserSQL)) {
            	
                Object[][] users = {
                        {1, "Rossi", "Mario"},
                        {2, "Verdi", "Andrea"},
                        {3, "Bianchi", "Massimo"},
                        {4, "Vallieri", "Sara"},
                        {5, "Graviglia", "Marco"},
                        {6, "Esposito", "Marzia"}
                };
                for (Object[] user : users) {
                    ps.setInt(1, (Integer) user[0]);
                    ps.setString(2, (String) user[1]);
                    ps.setString(3, (String) user[2]);
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }

}

