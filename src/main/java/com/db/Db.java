package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class Db {

    private static final String URL  = "jdbc:postgresql://localhost:5432/coworking_space";
    private static final String USER = "postgres";
    private static final String PASS = "3425";   // change this

    private Db() {}

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
