package Modelo.Impl;

import Modelo.Dao.ReservacionesDAO;
import Modelo.Reservacion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Clase que implementa el DAO de reservaciones
 * @author Diego-Aburto-Lara
 * @version 1.4
 */
public class ReservacionesDAOImpl extends BaseDAO implements ReservacionesDAO{
    
    public ReservacionesDAOImpl () throws Exception {
    }

    @Override
    public boolean reservarMesa(int personas, Date fecha, Time hora, String aNombre) throws Exception {
        String query = "{CALL reservarMesa("+personas+", '"+fecha+"', '"+hora+"', '"+aNombre+"')}";
        
        try (Connection con = this.getConexion();
            CallableStatement cs = con.prepareCall(query)) {
        
            int filasAfectadas = cs.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new Exception("Error al reservar mesa: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Reservacion> obtenerReservaciones() throws Exception {
        ArrayList<Reservacion> listaReservaciones = new ArrayList<>();
                        
        String query = "SELECT * FROM reservaciones";
        
        try (Connection con = this.getConexion(); 
            PreparedStatement ps = con.prepareStatement(query)) {
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                int id = rs.getInt("idReservacion");
                int mesa = rs.getInt("mesa");
                int personas = rs.getInt("personas");
                Date fecha = rs.getDate("fecha");
                Time hora = rs.getTime("hora");
                String aNombre = rs.getString("nombre");
                
                Reservacion reservacionAux = new Reservacion(id, mesa, personas, fecha, hora, aNombre);
                listaReservaciones.add(reservacionAux);
            }
            
            return listaReservaciones;

        } catch (SQLException e) {
            throw new Exception("Error al obtener reservaciones: " + e.getMessage());
        }
    }
    
    @Override
    public Boolean reservacionExiste (int mesa, int personas, Date fecha, Time hora, String aNombre) throws Exception {
        String query = "SELECT * FROM reservaciones WHERE mesa = "+mesa+" AND personas = "+personas+" AND fecha = '"+fecha+"' AND hora = '"+hora+"' AND nombre = '"+aNombre+"';";
        
        try (Connection con = this.getConexion(); 
            PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new Exception("Error al obtener la reservación: " + e.getMessage());
        }
    }
    
    @Override
    public int obtenerMesaEspecifica (int personas, Date fecha, Time hora, String aNombre) throws Exception{
        String query = "SELECT mesa FROM reservaciones WHERE personas = "+personas+" AND fecha = '"+fecha+"' AND hora = '"+hora+"' AND nombre = '"+aNombre+"';";
        
        try (Connection con = this.getConexion(); 
            PreparedStatement ps = con.prepareStatement(query)) {
            System.out.println("Todo bien 1/2");
            System.out.println(personas);
            System.out.println(fecha);
            System.out.println(hora);
            System.out.println(aNombre);
            System.out.println(query);
            System.out.println("AHHHHHH");
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                int mesa = rs.getInt("mesa");
                System.out.println("Todo bien 2/2");
                return mesa;    
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener la mesa: " + e.getMessage());
        }
    }
    
    @Override
    public Boolean eliminarReservacion (int mesa,int personas, Date fecha, Time hora, String aNombre) throws Exception {
        String query = "SELECT idReservacion FROM reservaciones WHERE mesa = "+mesa+" AND personas = "+personas+" AND fecha = '"+fecha+"' AND hora = '"+hora+"' AND nombre = '"+aNombre+"';";
        
        try (Connection con = this.getConexion(); 
            PreparedStatement ps = con.prepareStatement(query)) {
            
            ResultSet rs = ps.executeQuery();
            Boolean eliminado = rs.next();
            int id = rs.getInt("idReservacion");
            
            String query2 = "{CALL eliminarReservacion("+id+")}";

            PreparedStatement ps2 = con.prepareStatement(query2);
            
            ps2.executeQuery();
            
            return eliminado;
        } catch (SQLException e) {
            throw new Exception("Error al obtener la mesa: " + e.getMessage());
        }
    }
}