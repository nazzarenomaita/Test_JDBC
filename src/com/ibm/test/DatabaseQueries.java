package com.ibm.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DatabaseQueries {
    private DataSource dataSource;

    public DatabaseQueries(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void getBooksLoanedToUserVallieri() throws SQLException {
        String query = "SELECT L.Titolo, P.inizio, P.fine " +
                       "FROM P " +
                       "JOIN U ON P.id_U = U.id " +
                       "JOIN L ON P.id_L = L.id " +
                       "WHERE U.Cognome = 'Vallieri' " +
                       "ORDER BY P.inizio";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String titolo = rs.getString("Titolo");
                String inizio = rs.getDate("inizio").toString();
                String fine = rs.getDate("fine").toString();
                System.out.println("Titolo: " + titolo + ", Inizio: " + inizio + ", Fine: " + fine);
            }
        }
    }
    
    
    public void getTopThreeReaders() throws SQLException {
        String query = "SELECT U.Cognome, U.Nome, COUNT(P.id_L) AS num_books " +
                       "FROM P " +
                       "JOIN U ON P.id_U = U.id " +
                       "GROUP BY U.id " +
                       "ORDER BY num_books DESC " +
                       "LIMIT 3";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String cognome = rs.getString("Cognome");
                String nome = rs.getString("Nome");
                int num_books = rs.getInt("num_books");
                System.out.println("Cognome: " + cognome + ", Nome: " + nome + ", Libri letti: " + num_books);
            }
        }
    }
    
    public void getBooksNotYetReturned() throws SQLException {
        String query = "SELECT U.Cognome, U.Nome, L.Titolo " +
                       "FROM P " +
                       "JOIN U ON P.id_U = U.id " +
                       "JOIN L ON P.id_L = L.id " +
                       "WHERE P.fine IS NULL OR P.fine > CURDATE()";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String cognome = rs.getString("Cognome");
                String nome = rs.getString("Nome");
                String titolo = rs.getString("Titolo");
                System.out.println("Cognome: " + cognome + ", Nome: " + nome + ", Titolo: " + titolo);
            }
        }
    }
    public void getLoanHistoryForUser(String cognome) throws SQLException {
        String query = "SELECT L.Titolo, P.inizio, P.fine " +
                       "FROM P " +
                       "JOIN U ON P.id_U = U.id " +
                       "JOIN L ON P.id_L = L.id " +
                       "WHERE U.Cognome = ? " +
                       "ORDER BY P.inizio";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, cognome);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String titolo = rs.getString("Titolo");
                    String inizio = rs.getDate("inizio").toString();
                    String fine = rs.getDate("fine") != null ? rs.getDate("fine").toString() : "Non ancora restituito";
                    System.out.println("Titolo: " + titolo + ", Inizio: " + inizio + ", Fine: " + fine);
                }
            }
        }
    }

    
    public void getMostLoanedBooks() throws SQLException {
        String query = "SELECT L.Titolo, COUNT(P.id) AS num_prestiti " +
                       "FROM P " +
                       "JOIN L ON P.id_L = L.id " +
                       "GROUP BY L.id " +
                       "ORDER BY num_prestiti DESC";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String titolo = rs.getString("Titolo");
                int num_prestiti = rs.getInt("num_prestiti");
                System.out.println("Titolo: " + titolo + ", Numero di prestiti: " + num_prestiti);
            }
        }
    }
    
    public void getLongTermLoans() throws SQLException {
        String query = "SELECT U.Cognome, U.Nome, L.Titolo, P.inizio, P.fine " +
                       "FROM P " +
                       "JOIN U ON P.id_U = U.id " +
                       "JOIN L ON P.id_L = L.id " +
                       "WHERE DATEDIFF(CURDATE(), P.inizio) > 15";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String cognome = rs.getString("Cognome");
                String nome = rs.getString("Nome");
                String titolo = rs.getString("Titolo");
                String inizio = rs.getDate("inizio").toString();
                String fine = rs.getDate("fine") != null ? rs.getDate("fine").toString() : "Non ancora restituito";
                System.out.println("Cognome: " + cognome + ", Nome: " + nome + ", Titolo: " + titolo + ", Inizio: " + inizio + ", Fine: " + fine);
            }
        }
    }


}
