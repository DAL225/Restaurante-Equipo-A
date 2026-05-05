package Modelo.Dao;

import Modelo.Empleado;
import java.sql.SQLException;
import java.util.ArrayList;

public interface EmpleadoDAO {
    
    /**
     * Genera un hash de la contraseña proporcionada utilizando BCrypt.
     *
     * @param password La contraseña a hashear.
     * @return El hash de la contraseña.
     */
    /**
     * Genera un hash de la contraseña proporcionada utilizando BCrypt.
     *
     * @param password La contraseña a hashear.
     * @return El hash de la contraseña.
     */
    String generateHash(String password);

    /**
     * Verifica si las credenciales proporcionadas son válidas.
     *
     * @param username El nombre de usuario a verificar.
     * @param password La contraseña a verificar.
     * @return true si las credenciales son válidas, false en caso contrario.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    boolean isValidCredentials(String username, String password) throws Exception;

    /**
     * Crea un nuevo empleado en la base de datos.
     *
     * @param empleado Empleado a guardaramente, false en caso
     * contrario.
     * @throws Exception Si ocurre un error al ejecutar la consulta.
     */
    public boolean agregarEmpleado(Empleado empleado) throws Exception ;

    /**
     * Obtiene todos los empleados guardados en la BD.
     * 
     * @return Una lista de empleados.
     * @throws Exception Si ocurre alguna excepcion en la BD.
     */
    ArrayList<Empleado> obtenerEmpleados() throws Exception;
    /**
     * Elimina un usuario de la base de datos.
     *
     * @param id El ID del usuario a eliminar.
     * @return true si el usuario se eliminó correctamente, false en caso
     * contrario.
     * @throws Exception Si ocurre un error al ejecutar la consulta.
     */
    boolean removeEmpleado(int id) throws Exception;

    /**
     * Cambia el nombre de usuario de un usuario existente.
     *
     * @param id El ID del usuario.
     * @param username El nuevo nombre de usuario.
     * @return true si el nombre de usuario se cambió correctamente, false en
     * caso contrario.
     * @throws Exception Si ocurre un error al ejecutar la consulta.
     */
    boolean setEmpleadoUsuario(int id, String username)  throws Exception;

    /**
     * Cambia la contraseña de un usuario existente.
     *
     * @param id El ID del usuario.
     * @param password La nueva contraseña.
     * @return true si la contraseña se cambió correctamente, false en caso
     * contrario.
     * @throws Exception Si ocurre un error al ejecutar la consulta.
     */
    boolean setEmpleadoPassword(int id, String password) throws Exception;
}
