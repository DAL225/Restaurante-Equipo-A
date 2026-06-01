package Modelo.Impl;

import Modelo.Dao.ProductoMenuDAO;
import Modelo.ProductoMenu;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProductoMenuDAOImpl extends BaseDAO implements ProductoMenuDAO {

    /**
     * Constructor de la clase.
     * @throws Exception Si ocurre alguna excepcion en la construccion.
     */
    public ProductoMenuDAOImpl() throws Exception {
    }
    
    /**
     * Crea un nuevo producto de menu en la base de datos.
     *
     * @param role El rol del usuario.
     * @param username El nombre de usuario.
     * @param password La contraseña del usuario.
     * @return true si el usuario se creó correctamente, false en caso
     * contrario.
     * @throws Exception Si ocurre alguna Excepcion
     */
    @Override
    public boolean agregarProductoMenu(ProductoMenu producto) throws Exception {
        // Llamada al Procedimiento Almacenado que creamos en MySQL
        String query = "{CALL agregar_productoMenu(?, ?, ?, ?, ?)}";

        // Uso de try-with-resources para asegurar que la conexión se cierre sola
        try (Connection con = this.getConexion(); 
             CallableStatement cs = con.prepareCall(query)) {

            // Seteamos los parámetros del SP
            cs.setString(1, producto.getNombre());
            cs.setString(2, producto.getCategoria());
            cs.setString(3, producto.getImagenRuta()); // Aquí va la ruta: "img_productos/foto.jpg"
            cs.setDouble(4, producto.getPrecio());
            cs.setString(5, producto.getIngredientes());

            // Ejecutamos y verificamos si se afectó alguna fila
            int filasAfectadas = cs.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new Exception("Error al agregar producto: " + e.getMessage());
        }
    }

    /**
     * Obtiene la lista de productos del menu almacenados.
     * 
     * @return lista de productos del menu
     * @throws Exception Si ocurre un error al ejecutar la consulta.
     */
    @Override
    public ArrayList<ProductoMenu> obtenerProductosMenu() throws Exception {
        
        ArrayList<ProductoMenu> listaProd = new ArrayList<>();
        
        String query = "SELECT * FROM vista_menu_activos";
        
        // Uso de try-with-resources para asegurar que la conexión se cierre sola
        try (Connection con = this.getConexion(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                int id = rs.getInt("id_productoMenu");
                String nombre = rs.getString("nombre");
                String categoria = rs.getString("categoria");
                String imagenRuta = rs.getString("imagenRuta");
                Double precio = rs.getDouble("precio");
                String ingredientes = rs.getString("ingredientes");
                Boolean disponibilidad = rs.getBoolean("disponibilidad");
                
                ProductoMenu productoMenuAux = new ProductoMenu(id, nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad);
                listaProd.add(productoMenuAux);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Error al obtener productos");
        }
        return listaProd;
    }

    @Override
    public ProductoMenu obtenerProductoMenu(int idBuscar) throws Exception {
        ProductoMenu producto = null;

        String query = "SELECT * FROM vista_menu_activos WHERE id_productoMenu = ?";

        try (Connection con = this.getConexion(); PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idBuscar);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int id = rs.getInt("id_productoMenu");
                String nombre = rs.getString("nombre");
                String categoria = rs.getString("categoria");
                String imagenRuta = rs.getString("imagenRuta");
                Double precio = rs.getDouble("precio");
                String ingredientes = rs.getString("ingredientes");
                Boolean disponibilidad = rs.getBoolean("disponibilidad");
                
                producto = new ProductoMenu(id, nombre, categoria, imagenRuta, precio, ingredientes, disponibilidad);
                
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            throw new Exception("Error al obtener producto");
        }
        
        return producto;
    }
    
    /**
     * Modifica un producto del Menu en la base de datos.
     *
     * @param producto producto con la información actualizada.
     * @return true si el producto se modificó correctamente, false en caso
     * contrario.
     * @throws Exception Si ocurre alguna excepción.
     */
    @Override
    public boolean modificarProductoMenu(ProductoMenu producto) throws Exception {
        // Llamada al Procedimiento Almacenado que creamos en MySQL
        String query = "{CALL modificar_productoMenu(?, ?, ?, ?, ?, ?, ?)}";

        // Uso de try-with-resources para asegurar que la conexión se cierre sola
        try (Connection con = this.getConexion(); 
             CallableStatement cs = con.prepareCall(query)) {

            // Seteamos los parámetros del SP
            cs.setInt(1, producto.getId());
            cs.setString(2, producto.getNombre());
            cs.setString(3, producto.getCategoria());
            cs.setString(4, producto.getImagenRuta());
            cs.setDouble(5, producto.getPrecio());
            cs.setString(6, producto.getIngredientes());
            cs.setBoolean(7, producto.getDisponibilidad());

            // Ejecutamos y verificamos si se afectó alguna fila
            int filasAfectadas = cs.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Error al modificar producto: " + e.getMessage());
            
        }
    }
}