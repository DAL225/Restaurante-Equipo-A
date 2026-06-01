package Modelo.Impl;
import Modelo.Dao.VentasDAO;
import Modelo.Ventas;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LENOVO CORE i7
 */
public class VentasDAOImpl extends BaseDAO implements VentasDAO{
    public VentasDAOImpl() throws Exception {
        
    }
    
    /**
     * Metodo para agregar una venta a la base de datos
     * @param descripcion La lista de pedidos de la mesa
     * @param subtotal El subtotal a pagar de la mesa
     * @param tipoPago El tipo de pago que fue elegido
     * @return true
     * @throws Exception
     */
    @Override
    public boolean agregarVenta(String descripcion, double subtotal, String tipoPago) throws Exception{
        String query ="{ CALL agregarVenta(?,?,?) }";
        
        try (PreparedStatement stmt = connection.prepareCall(query)) {
            
            stmt.setString(1, descripcion);
            stmt.setDouble(2, subtotal);
            stmt.setString(3, tipoPago);
            stmt.execute();
            return true;
            
        } catch (SQLException e) {
            throw new Exception ("Error" + e.getMessage());
        }
    }
    
/**
 * Metodo para recuperar todas las ventas
 * @return ArrayList de ventas
 * @throws Exception 
 */
    @Override
    public ArrayList<Ventas> obtenerVentas() throws Exception {
        ArrayList<Ventas> ventas = new ArrayList<>();
        String query = "SELECT id_venta, descripcion, subtotal, iva, total, tipo_pago FROM ventas";

        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idVenta = rs.getInt("id_venta");
                String descripcion = rs.getString("descripcion");
                double subtotal= rs.getDouble("subtotal");
                double iva = rs.getDouble("iva");
                double total = rs.getDouble("total");
                String tipoPago = rs.getString("tipo_pago");

                Ventas venta = new Ventas(idVenta, descripcion, subtotal, iva, total, tipoPago);
                ventas.add(venta);
            }

        } catch (SQLException e) {
            throw new Exception("Error al recuperar ventas: " + e.getMessage());
        }

        return ventas;
    }    
}
