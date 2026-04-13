package Modelo.Impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * @author amiss
 */
public abstract class BaseDAO {

    // Configuración de la BD
    private final String URL = "jdbc:mysql://localhost:3306/restaurante";
    private final String USER = "adminRes";
    private final String PASS = "1234";
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // Conexión reutilizable
    protected Connection connection;

    // Constructor
    public BaseDAO() {
        conectar();
    }

    // Método para conectar (combinado)
    protected void conectar() {
        try {
            // Cargar driver
            Class.forName(DRIVER);

            // Validar conexión (lo mejor de BaseSQL)
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Conexión establecida correctamente");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }

    // Obtener conexión (para DAOs hijos)
    protected Connection getConexion() throws SQLException {
        if (connection == null || connection.isClosed()) {
            conectar();
        }
        return connection;
    }

  

}