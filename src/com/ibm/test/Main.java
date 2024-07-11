package com.ibm.test;

import java.sql.SQLException;

import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = DataSourceConfig.getDataSource();
        DatabaseSetup setup = new DatabaseSetup(dataSource);
        DataInserter inserter = new DataInserter(dataSource);
        DatabaseQueries queries = new DatabaseQueries(dataSource);

        try {
            setup.setupDatabase();
            inserter.insertData();
            
            System.out.println("Libri prestati a Sara Vallieri:");
            queries.getBooksLoanedToUserVallieri();
            
            System.out.println("\nPrimi tre lettori che hanno letto più libri:");
            queries.getTopThreeReaders();
            
            System.out.println("\nLibri non ancora rientrati e i possessori:");
            queries.getBooksNotYetReturned();
            
            System.out.println("\nStorico dei libri chiesi in prestito da Sara Vallieri:");
            queries.getLoanHistoryForUser("Vallieri");
            
            System.out.println("\nClassifica dei libri maggiormente prestati:");
            queries.getMostLoanedBooks();
            
            System.out.println("\nPrestiti la cui durata supera i 15 giorni:");
            queries.getLongTermLoans();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

