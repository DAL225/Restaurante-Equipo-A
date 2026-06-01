package Modelo.Impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Clase que define los metodos de conexion a la BD.
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

    /**
     * Constructor de la clase.
     * @throws Exception si ocurre alguna excepcion al implementarlo.
     */
    public BaseDAO() throws Exception {
        conectar();
    }
    
    /**
     * Método para conectar (combinado).
     * @throws Exception si ocurre alguna excepcion al intentar conectar
     */
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

    /**
     * Obtiene la conexión activa a la base de datos. Si la conexión no existe o
     * está cerrada, se crea nuevamente.
     *
     * @return conexión activa a la base de datos
     *
     * @throws SQLException si ocurre un error al verificar el estado de la
     * conexión
     * @throws Exception si ocurre un error al establecer la conexión
     */
    protected Connection getConexion() throws SQLException, Exception {
        if (connection == null || connection.isClosed()) {
            conectar();
        }
        return connection;
    }

  

}