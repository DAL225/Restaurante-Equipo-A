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
                        
        String query = "{SELECT * FROM reservaciones}";
        
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
    public int obtenerMesaEspecifica (int personas, Date fecha, Time hora, String aNombre) throws Exception{
        String query = "{SELECT mesa FROM reservaciones WHERE personas = "+personas+" AND fecha = '"+fecha+"' AND hora = '"+hora+"' AND nombre = '"+aNombre+"'}";
        
        try (Connection con = this.getConexion(); 
            PreparedStatement ps = con.prepareStatement(query)) {
            
            ResultSet rs = ps.executeQuery();
            
            int mesa = rs.getInt("mesa");
                            
            return mesa;

        } catch (SQLException e) {
            throw new Exception("Error al obtener la reservación: " + e.getMessage());
        }

    }
}