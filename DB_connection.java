package com.smartqueue;

/*
 * we create a DBConnection utility class to handle database connectivity.
 *  Instead of writing the same DriverManager.getConnection(...) code in every servlet or DAO class,
 *  we centralize it in one reusable class.
 */

import java.sql.*;

public class DB_connection {
	
	
    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/smartqueue", "root", "adeshpol141546");
    }
}
