package Modelo.Impl;

import Modelo.Dao.ProductoAlmacenDAO;
import Modelo.ProductoAlmacen;
import Modelo.ProductoMenu;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductoAlmacenDAOImpl extends BaseDAO implements ProductoAlmacenDAO {

    /**
     * Constructor de la clase.
     */
    public ProductoAlmacenDAOImpl() throws Exception{
    }
    
    /**
     * Crea un nuevo producto de almacen(materia prima) en la base de datos.
     * 
     * @param producto producto a almacenar con toda su informacion.
     * @return true si el producto se creó correctamente, false en caso
     * contrario.
     * @throws Exception Si ocurre alguna Excepcion
     */
    @Override
    public boolean agregarProductoAlmacen(ProductoAlmacen producto) throws Exception {
        // Llamada al Procedimiento Almacenado que creamos en MySQL
        String query = "{CALL agregar_productoAlmacen(?, ?, ?, ?)}";

        // Uso de try-with-resources para asegurar que la conexión se cierre sola
        try (Connection con = this.getConexion(); 
             CallableStatement cs = con.prepareCall(query)) {

            // Seteamos los parámetros del SP
            cs.setString(1, producto.getMarca());
            cs.setString(2, producto.getTipo());
            cs.setInt(3, producto.getStock()); 
            cs.setString(4, producto.getProveedor());

            // Ejecutamos y verificamos si se afectó alguna fila
            int filasAfectadas = cs.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new Exception("Error al agregar producto: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene la lista de productos del almacen(materia prima) almacenados.
     * 
     * @return lista de productos del almacen
     * @throws Exception Si ocurre un error al ejecutar la consulta.
     */
    @Override
    public ArrayList<ProductoAlmacen> obtenerProductosAlmacen() throws Exception {
        ArrayList<ProductoAlmacen> listaProd = new ArrayList<>();
        
        String query = "SELECT * FROM vista_almacen_activos";
        
        // Uso de try-with-resources para asegurar que la conexión se cierre sola
        try (Connection con = this.getConexion(); 
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                int id = rs.getInt("id_productoAlmacen");
                String marca = rs.getString("marca");
                String tipo = rs.getString("tipo");
                int stock = rs.getInt("stock");
                String proveedor = rs.getString("proveedor");
                
                ProductoAlmacen productoMenuAux = new ProductoAlmacen(id, marca, tipo, stock, proveedor);
                listaProd.add(productoMenuAux);
            }
            
            return listaProd;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Error al obtener productos");
        }
    }
    
    
    /**
     * Retira de stock de un producto de almacen la cantidad indicada.
     * 
     * @param id id del producto a disminuir stock.
     * @param cantidad cantidad a retirar
     * @return true si se realizo con exito la disminucion, false en caso
     * contrario.
     * @throws Exception Si ocurre un error al realizar el procedimiento.
     */
    @Override
    public boolean retirarStock(int id, int cantidad) throws Exception {
        // Llamada al Procedimiento Almacenado que creamos en MySQL
        String query = "{CALL retirarStockAlmacen(?, ?)}";

        // Uso de try-with-resources para asegurar que la conexión se cierre sola
        try (Connection con = this.getConexion(); 
             CallableStatement cs = con.prepareCall(query)) {

            // los parámetros
            cs.setInt(1, id);
            cs.setInt(2, cantidad); 

            // Ejecutamos y verificamos si se afectó alguna fila
            int filasAfectadas = cs.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new Exception("Error al retirar: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene un producto del almacén (materia prima).
     *
     * @return producto del almacén
     * @throws Exception Si ocurre un error al ejecutar la consulta.
     */
    @Override
    public ProductoAlmacen obtenerProductoAlmacen(int idBuscar) throws Exception {

        ProductoAlmacen producto = null;

        String query = "SELECT * FROM vista_almacen_activos WHERE id_productoAlmacen = ?";

        try (Connection con = this.getConexion(); PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idBuscar);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int id = rs.getInt("id_productoAlmacen");
                String marca = rs.getString("marca");
                String tipo = rs.getString("tipo");
                int stock = rs.getInt("stock");
                String proveedor = rs.getString("proveedor");

                producto = new ProductoAlmacen(id, marca, tipo, stock, proveedor);
            }

            return producto;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            throw new Exception("Error al obtener producto");
        }
    }

    /**
     * Modifica un producto de almacén (materia prima) en la base de datos.
     *
     * @param producto producto con la información actualizada.
     * @return true si el producto se modificó correctamente, false en caso
     * contrario.
     * @throws Exception Si ocurre alguna excepción.
     */
    @Override
    public boolean modificarProductoAlmacen(ProductoAlmacen producto) throws Exception {
        // Llamada al Procedimiento Almacenado que creamos en MySQL
        String query = "{CALL modificar_productoAlmacen(?, ?, ?, ?, ?)}";

        // Uso de try-with-resources para asegurar que la conexión se cierre sola
        try (Connection con = this.getConexion(); 
             CallableStatement cs = con.prepareCall(query)) {

            // Seteamos los parámetros del SP
            cs.setInt(1, producto.getId());
            cs.setString(2, producto.getMarca());
            cs.setString(3, producto.getTipo());
            cs.setInt(4, producto.getStock()); 
            cs.setString(5, producto.getProveedor());

            // Ejecutamos y verificamos si se afectó alguna fila
            int filasAfectadas = cs.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new Exception("Error al modificar producto: " + e.getMessage());
        }
    }
}
