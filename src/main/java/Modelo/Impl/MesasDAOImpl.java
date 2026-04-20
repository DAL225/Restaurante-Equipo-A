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
        String query = "{CALL agregarMesa("+cantidadPersonas+")}";
        
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
        
        String query = "{SELECT * FROM mesas}";
        
        try (Connection con = this.getConexion(); 
            PreparedStatement ps = con.prepareStatement(query)) {
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                int id = rs.getInt("idMesa");
                String estado = rs.getString("estadoMesa");
                int personas = rs.getInt("cantidadPersonasPosibles");
                
                Mesa mesaAux = new Mesa(id, estado, personas);
                listaMesas.add(mesaAux);
            }
            
            return listaMesas;

        } catch (SQLException e) {
            throw new Exception("Error al obtener mesas: " + e.getMessage());
        }
    }
}