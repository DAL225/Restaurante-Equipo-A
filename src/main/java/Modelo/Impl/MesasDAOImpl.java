package Modelo.Impl;

import Modelo.Dao.MesasDAO;
import Modelo.Mesa;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MesasDAOImpl extends BaseDAO implements MesasDAO{
    
    public MesasDAOImpl () throws Exception {
    }

    @Override
    public boolean agregarMesa(int cantidadPersonas) throws Exception {
        String query = "CALL agregarMesa("+cantidadPersonas+")";
        
        try (Connection con = this.getConexion(); 
             CallableStatement cs = con.prepareCall(query)) {

            int filasAfectadas = cs.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new Exception("Error al agregar mesa: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Mesa> obtenerMesas() throws Exception {
        ArrayList<Mesa> listaMesas = new ArrayList<>();
        
        String query = "SELECT * FROM mesas";
        
        try (Connection con = this.getConexion(); 
            PreparedStatement ps = con.prepareStatement(query)) {
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                int id = rs.getInt("idMesa");
                int estadoNumerico = rs.getInt("estadoMesa"); // 1. Leemos el 0 o 1
                int personas = rs.getInt("cantidadPersonasPosibles");
                
                // 2. Traducimos el número a texto
                String estadoTexto;
                if (estadoNumerico == 0) {
                    estadoTexto = "Libre"; // o "Disponible", como prefieras llamarlo
                } else {
                    estadoTexto = "Ocupada"; 
                }
                
                // 3. Pasamos el texto ya traducido a tu objeto Mesa
                Mesa mesaAux = new Mesa(id, estadoTexto, personas);
                listaMesas.add(mesaAux);
            }
            
            return listaMesas;

        } catch (SQLException e) {
            throw new Exception("Error al obtener mesas: " + e.getMessage());
        }
    }
    @Override
    public boolean actualizarEstadoMesa(int idMesa, String nuevoEstado,int cantidadPersonas) throws Exception {
        String query = "UPDATE mesas SET estadoMesa = ?, CantidadPersonasPosibles = ? WHERE idMesa = ?";
    int estadoNumerico; 
    if(nuevoEstado.equals("Ocupada") || nuevoEstado.equals("Reservada")) {
    estadoNumerico = 1;
} else {
    estadoNumerico = 0;
}

    try (Connection con = this.getConexion();
         PreparedStatement ps = con.prepareStatement(query)) {

        ps.setInt(1, estadoNumerico);
        ps.setInt(2,cantidadPersonas);
        ps.setInt(3, idMesa);

        int filasAfectadas = ps.executeUpdate();
        return filasAfectadas > 0;

    } catch (SQLException e) {
        throw new Exception("Error al actualizar el estado de la mesa: " + e.getMessage());
    }
}

}