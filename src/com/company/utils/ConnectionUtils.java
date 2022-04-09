package com.company.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {
    public static Connection getMySQLConnection() {
        try {
            String hostName = "localhost";

            String dbName = "quanlysinhvien";
            String userName = "root";
            String password = "12345678";

            return getMySQLConnection(hostName, dbName, userName, password);
        } catch (Exception e) {
            System.out.println("Loi roi khong ket noi duoc");
        }
        return null;
    }

    public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password) throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

        Connection conn = DriverManager.getConnection(connectionURL, userName, password);
        return conn;
    }
}
