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

    public ProductoMenuDAOImpl() throws Exception {
    }
    
    

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
            
            return listaProd;

        } catch (SQLException e) {
            throw new Exception("Error al obtener productos: " + e.getMessage());
        }
    }
}