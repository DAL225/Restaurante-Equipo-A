package Modelo.Impl;

import Modelo.Dao.ProductoMenuDAO;
import Modelo.ProductoMenu;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;


public class ProductoMenuDAOImpl extends BaseDAO implements ProductoMenuDAO {

    public ProductoMenuDAOImpl() {
    }
    
    

    @Override
    public boolean agregarProductoMenu(ProductoMenu producto) throws Exception {
        // Llamada al Procedimiento Almacenado que creamos en MySQL
        String sql = "{CALL agregar_productoMenu(?, ?, ?, ?, ?)}";

        // Uso de try-with-resources para asegurar que la conexión se cierre sola
        try (Connection con = this.getConexion(); 
             CallableStatement cs = con.prepareCall(sql)) {

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
}