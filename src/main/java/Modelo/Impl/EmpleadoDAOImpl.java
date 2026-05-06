package Modelo.Impl;

import org.mindrot.jbcrypt.BCrypt;
import Modelo.Dao.EmpleadoDAO;
import Modelo.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class EmpleadoDAOImpl extends BaseDAO implements EmpleadoDAO {
    
    public EmpleadoDAOImpl() throws Exception {
    }
    
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
    @Override
    public String generateHash(String password) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(password, salt);
    }

    /**
     * Verifica si las credenciales proporcionadas son válidas.
     *
     * @param usuario El usuario de usuario a verificar.
     * @param password La contraseña a verificar.
     * @return true si las credenciales son válidas, false en caso contrario.
     * @throws Exception Si ocurre un error al ejecutar la consulta.
     */
    @Override
    public boolean isValidCredentials(String usuario, String password) throws Exception {
        String query = "SELECT password FROM empleado WHERE usuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, usuario);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String storedHash = resultSet.getString("password");
                if (password.equals(storedHash)) {
                    return true;
                } else {
                    throw new Exception("Password incorrecta.");
                }
            } else {
                throw new Exception("Usuario no encontrado.");
            }
        }
    }

    /**
     * Crea un nuevo empleado en la base de datos.
     *
     * @param empleado Empleado a guardar
     * @throws Exception Si ocurre una Excepcion
     */
    @Override
    public boolean agregarEmpleado(Empleado empleado) throws Exception {
        String hashedPassword = generateHash(empleado.getPassword());
        System.out.println(hashedPassword);

        String query = "{ CALL agregar_empleado(?, ?, ?) }";
        try (PreparedStatement statement = connection.prepareCall(query)) {
            statement.setString(1, empleado.getUsuario());
            statement.setString(2, hashedPassword);
            statement.setString(3, empleado.getRol());
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new Exception("Error al agregar el empleado: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene todos los empleados guardados en la BD.
     * 
     * @return Una lista de empleados.
     * @throws Exception Si ocurre alguna excepcion en la BD.
     */
    @Override
    public ArrayList<Empleado> obtenerEmpleados() throws Exception {
        
        ArrayList<Empleado> listaEmpleados = new ArrayList<>();
        
        String query = "SELECT * FROM vista_empleados_activos";
        
        // Uso de try-with-resources para asegurar que la conexión se cierre sola
        try (Connection con = this.getConexion(); 
            PreparedStatement ps = con.prepareStatement(query)) {
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                int id = rs.getInt("id_empleado");
                String usuario = rs.getString("usuario");
                String hashedPassword = rs.getString("password");
                String rol = rs.getString("rol");
                
                Empleado empleadoAux = new Empleado(id, usuario, hashedPassword, rol);
                listaEmpleados.add(empleadoAux);
            }
            
            return listaEmpleados;

        } catch (SQLException e) {
            throw new Exception("Error al obtener empleados: " + e.getMessage());
        }
    }

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param id El ID del usuario a eliminar.
     * @return true si el usuario se eliminó correctamente, false en caso
     * contrario.
     * @throws Exception Si ocurre alguna excepcion en la BD.
     */
    @Override
    public boolean removeEmpleado(int id) throws Exception {
        String query = "{ CALL eliminar_empleado(?) }";

        try (PreparedStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, id);
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Cambia el usuario de usuario de un usuario existente.
     *
     * @param id El ID del usuario.
     * @param usuario El nuevo usuario de usuario.
     * @return true si el usuario de usuario se cambió correctamente, false en
     *  caso contrario.
     * @throws Exception Si ocurre alguna excepcion en la BD.
     */
    @Override
    public boolean setEmpleadoUsuario(int id, String usuario) throws Exception {
        String query = "{ CALL cambiar_usuario_empleado(?, ?) }";

        try (PreparedStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, id);
            statement.setString(2, usuario);
            statement.execute();

            return true;
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Cambia la contraseña de un usuario existente.
     *
     * @param id El ID del usuario.
     * @param password La nueva contraseña.
     * @return true si la contraseña se cambió correctamente, false en caso
     * contrario.
     * @throws Exception Si ocurre alguna excepcion en la BD.
     */
    @Override
    public boolean setEmpleadoPassword(int id, String password) throws Exception {
        String hashedPassword = generateHash(password);
        String query = "{ CALL cambiar_password_empleado(?, ?) }";
        try (PreparedStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, id);
            statement.setString(2, hashedPassword);
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }
}
