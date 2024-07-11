package com.ibm.test;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.SQLException;

import javax.sql.DataSource;

public class DataSourceConfig {
    private static MysqlDataSource dataSource;

    static {
        dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/");
        dataSource.setUser("root");
        dataSource.setPassword("admin");
        try {
			dataSource.getCreateDatabaseIfNotExist();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}

