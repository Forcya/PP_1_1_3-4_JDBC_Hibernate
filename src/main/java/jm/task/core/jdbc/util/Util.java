package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String dbURL = "jdbc:mysql://localhost:3306/dbPP";
    private static final String dbUserName = "root";
    private static final String dbPassword = "4561756Api17";


    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(dbURL,dbUserName, dbPassword);
            if (!connection.isClosed()) {
                System.out.println("Соединение с БД установленно");
            }
            return connection;
        } catch (SQLException e) {
            System.out.println("Проблемы с подключением");
            return null;
        }
    }

    public static void isClosed () {
        try {
            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
