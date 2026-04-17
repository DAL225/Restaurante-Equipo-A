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
    private final String PASS = "12345";
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // Conexión reutilizable
    protected Connection connection;

    // Constructor
    public BaseDAO() throws Exception {
        conectar();
    }

    // Método para conectar (combinado)
    protected void conectar() throws Exception {
        try {
            // Cargar driver
            Class.forName(DRIVER);

            // Validar conexión (lo mejor de BaseSQL)
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Conexión establecida correctamente");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver: " + e.getMessage());
            throw new Exception("Error de acceso a la BD, intente mas tarde");
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            throw new Exception("Error de acceso a la BD, intente mas tarde");
        }
    }

    // Obtener conexión (para DAOs hijos)
    protected Connection getConexion() throws SQLException, Exception {
        if (connection == null || connection.isClosed()) {
            conectar();
        }
        return connection;
    }

  

}