/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

import Modelo.Dao.PedidoDAO;
import Modelo.Pedido;
import Modelo.ProductoMenu;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author LENOVO CORE i7
 */
public class PedidoDAOImpl extends BaseDAO implements PedidoDAO  {
    
        public PedidoDAOImpl() throws Exception {
    }
    
        
/**
 * Metodo para agregar un pedido al sistema
 * @param producto Nombre del platillo
 * @param cantidad Cantidad a agregar
 * @param mesa  Mesa del pedido
 * @return True
 * @throws Exception 
 */
    public boolean agregarPedido(String producto, int cantidad, int mesa) throws Exception {
        String query ="{ CALL agregarPedido(?,?,?) }";
        
        try (PreparedStatement stmt = connection.prepareCall(query)) {
            
            stmt.setString(1, producto);
            stmt.setInt(2, cantidad);
            stmt.setInt(3, mesa);
            stmt.execute();
            return true;
            
        } catch (SQLException e) {
            throw new Exception ("Error" + e.getMessage());
        }
    }
    
// MÉTODO PARA CARGAR TODOS LOS PEDIDOS
    public ArrayList<Pedido> cargarPedidos() throws Exception {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT idPedido, producto, cantidad, subtotal, estado, preparado, mesa FROM pedidosTab";

        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idPedido = rs.getInt("idPedido");
                String producto = rs.getString("producto");
                int cantidad= rs.getInt("cantidad");
                double subtotal = rs.getDouble("subtotal");
                boolean estado = rs.getBoolean("estado");
                boolean preparado = rs.getBoolean("preparado");
                int mesa = rs.getInt("mesa");

                Pedido pedido = new Pedido(idPedido, producto, cantidad, subtotal, estado, preparado, mesa);
                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            throw new Exception("Error al cargar ventas: " + e.getMessage());
        }

        return pedidos;
    }
    
    public ArrayList<Pedido> cargarPedidosNoPreparado() throws Exception {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT idPedido, producto, cantidad, subtotal, estado, preparado, mesa FROM pedidosTab WHERE preparado = false";

        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idPedido = rs.getInt("idPedido");
                String producto = rs.getString("producto");
                int cantidad= rs.getInt("cantidad");
                double subtotal = rs.getDouble("subtotal");
                boolean estado = rs.getBoolean("estado");
                boolean preparado = rs.getBoolean("preparado");
                int mesa = rs.getInt("mesa");

                Pedido pedido = new Pedido(idPedido, producto, cantidad, subtotal, estado, preparado, mesa);
                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            throw new Exception("Error al cargar ventas: " + e.getMessage());
        }

        return pedidos;
    }
/**
 * Metodo para cargar los pedidos por mesa
 * @param pMesa Numero de mesa
 * @return ArrayList con todos los pedidos Mesa
 * @throws Exception 
 */    
     public ArrayList<Pedido> cargarPedidosMesa(int pMesa) throws Exception {
        ArrayList<Pedido> pedidos= new ArrayList<>();
        String query ="{ CALL pedidosMesa(?) }";
        
        try (PreparedStatement stmt = connection.prepareCall(query)) {
            
            stmt.setInt(1, pMesa);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int idPedido = rs.getInt(1);
                String producto = rs.getString("producto");
                int cantidad= rs.getInt("cantidad");
                double subtotal = rs.getDouble("subtotal");
                boolean estado = rs.getBoolean("estado");
                boolean preparado = rs.getBoolean("preparado");
                int mesa = rs.getInt("mesa");

                Pedido pedido = new Pedido(idPedido, producto, cantidad, subtotal, estado, preparado, mesa);
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            throw new Exception ("Error" + e.getMessage());
        }
        return pedidos;
    }
        
    // MÉTODO PARA BUSCAR LOS PEDIDOS POR ID O POR NOMBRE DEL PLATILLO FILTRADO POR MESA
/**
 * Metodo para recuperar los pedidos por ID o por nombre del platillo (Por mesa)
 * @param busqueda Texto para la busqueda
 * @param mesaSeleccionada Mesa seleccionada
 * @return ArrayList con los pedidos 
 * @throws Exception 
 */
    public ArrayList<Pedido> buscarPedidos(String busqueda, int mesaSeleccionada) throws Exception {
        busqueda = busqueda.trim();
    
        if (busqueda.isEmpty()) {
            return null;
        }

        String query;
        boolean esIdNumerico;
        int id = -1;

        // 1. Validar si la búsqueda es un ID (número) o un Nombre (texto)
        try {
            id = Integer.parseInt(busqueda);
            esIdNumerico = true;
            // Búsqueda por ID: Ignora la mesa actual para encontrar el pedido exacto
            query = "SELECT idPedido, producto, cantidad, subtotal, estado, preparado, mesa FROM pedidosTab WHERE idPedido = ?";
        } catch (NumberFormatException e) {
            esIdNumerico = false;
            // Búsqueda por Nombre: Obliga a que pertenezca a la mesa seleccionada en el ChoiceBox
            query = "SELECT idPedido, producto, cantidad, subtotal, estado, preparado, mesa FROM pedidosTab WHERE LOWER(producto) LIKE LOWER(?) AND mesa = ?";
        }

        ArrayList<Pedido> pedidos = new ArrayList<>();

        // 2. Ejecutar la consulta en la base de datos
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
        
            if (esIdNumerico) {
                stmt.setInt(1, id);
            } else {
                stmt.setString(1, "%" + busqueda + "%");
                stmt.setInt(2, mesaSeleccionada);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idPedido = rs.getInt("idPedido");
                    String producto = rs.getString("producto");
                    int cantidad = rs.getInt("cantidad");
                    double subtotal = rs.getDouble("subtotal");
                    boolean estado = rs.getBoolean("estado");
                    boolean preparado = rs.getBoolean("preparado");
                    int mesa = rs.getInt("mesa");

                    Pedido pedido = new Pedido(idPedido, producto, cantidad, subtotal, estado, preparado, mesa);
                    pedidos.add(pedido);
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al buscar en la base de datos: " + e.getMessage());
        }

        if (pedidos.isEmpty()) {
        return null;
        }
        
        return pedidos;
    }
    
// MÉTODO PARA MODIFICAR UN PEDIDO
/**
 * Metodo para modificar un pedido
 * @param p_idPedido Id del pedido a modificar
 * @param p_producto Id del nuevo platillo
 * @param p_cantidad Id de la nueva cantidad a ingresar
 * @return Pedido actualizado
 * @throws Exception 
 */
    public Boolean modificarPedido(int p_idPedido, String p_producto, int p_cantidad) throws Exception {
        Pedido pedidoActualizado = null;
        String query = "{ CALL modificarPedido(?, ?, ?) }";

        try (PreparedStatement stmt = connection.prepareCall(query)){
            stmt.setInt(1, p_idPedido);
            stmt.setString(2, p_producto);
            stmt.setInt(3, p_cantidad);
            
            stmt.execute();

        } catch (SQLException e) {
            throw new Exception("Error al modificar pedido " + e.getMessage());
        }

        return true;
    }

/**
 * Metodo para cancelar un pedido
 * @param p_idPedido Id del pedido a cancelar
 * @return True
 * @throws Exception 
 */
    public boolean cancelarPedido( int p_idPedido ) throws Exception {
        String query = "{ CALL cancelarPedido(?) }";

        try (PreparedStatement stmt = connection.prepareCall(query)){
            stmt.setInt(1, p_idPedido);
            
            stmt.execute();
       
        return true;
        } catch (SQLException e) {
            
            throw new Exception("Error al cancelar el pedido: " + e.getMessage());
        }
        
    }
    
    /**
     * Metodo que recibe una ID y marca el pedido con dicha ID como "Preparado"
     * @param idPedido Integer correspondiente a la ID del pedido a preparar
     * @return Boolean, true si se logró marcar como "Preparado" el pedido, false si no
     * @throws Exception Excepción de SQL
     */
    public boolean pedidoPreparado(int idPedido) throws Exception {
        String query = "update pedidosTab set preparado = true where idPedido="+idPedido;
        
        try (PreparedStatement stmt = connection.prepareCall(query)){
            stmt.execute();
            return true;
        } catch (SQLException e){
            throw new Exception("Error al preparar el pedido: "+e.getMessage());
        }
    }
    
    /**
     * Metodo para eliminar un pedido en la base de datos
     * @param p_idPedido id del pedido
     * @return True
     * @throws Exception En caso de que el pedido no pueda eliminarse 
     */
    public Boolean eliminarPedido(int p_idPedido) throws Exception {
        String query = "{ CALL eliminarPedido(?) }";

        try (PreparedStatement stmt = connection.prepareCall(query)){
            stmt.setInt(1, p_idPedido);
            stmt.execute();

        } catch (SQLException e) {
            throw new Exception("Error al eliminar el pedido: "+ p_idPedido + " "+e.getMessage());
        }

        return true;
    }
}