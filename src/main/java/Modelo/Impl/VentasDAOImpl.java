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
    public Ventas agregarVenta(String descripcion, double subtotal, String tipoPago) throws Exception{
        Ventas venta = null;
        String query ="{ CALL agregarVenta(?,?,?) }";
        
        try (PreparedStatement stmt = connection.prepareCall(query)) {
            
            stmt.setString(1, descripcion);
            stmt.setDouble(2, subtotal);
            stmt.setString(3, tipoPago);
            
            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    int v_idVenta = rs.getInt("id_venta");
                    String v_descripcion = rs.getString("descripcion");
                    double v_subtotal= rs.getDouble("subtotal");
                    double v_iva = rs.getDouble("iva");
                    double v_total = rs.getDouble("total");
                    String v_tipoPago = rs.getString("tipo_pago");
                    
                    venta = new Ventas(v_idVenta, v_descripcion, v_subtotal, v_iva, v_total, v_tipoPago);
                }
            }catch(SQLException e){
                throw new Exception("Error al recuperar ventas: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new Exception ("Error" + e.getMessage());
        }
        return venta;
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

    @Override
    public Ventas obtenerUltima() throws Exception {
        Ventas venta = null;
        String query = "SELECT * FROM ventas ORDER BY id_venta DESC LIMIT 1;";
           
        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idVenta = rs.getInt("id_venta");
                String descripcion = rs.getString("descripcion");
                double subtotal= rs.getDouble("subtotal");
                double iva = rs.getDouble("iva");
                double total = rs.getDouble("total");
                String tipoPago = rs.getString("tipo_pago");
                
                venta = new Ventas(idVenta, descripcion, subtotal, iva, total, tipoPago);
            }

        } catch (SQLException e) {
            throw new Exception("Error al recuperar ventas: " + e.getMessage());
        }
        
        return venta;
    }



    
}
