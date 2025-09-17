package com.mycompany.pr02tar01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private ConexionBD() {}
    private static final String URL = "jdbc:mysql://localhost:3306/chalet";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // Cambia la contraseña según tu configuración

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
