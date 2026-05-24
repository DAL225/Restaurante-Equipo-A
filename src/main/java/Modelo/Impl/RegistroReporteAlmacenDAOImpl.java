package Modelo.Impl;

import Modelo.Dao.RegistroReporteAlmacenDAO;
import Modelo.RegistroReporteAlmacen;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author amiss
 */
public class RegistroReporteAlmacenDAOImpl  extends BaseDAO implements RegistroReporteAlmacenDAO {

    public RegistroReporteAlmacenDAOImpl() throws Exception {
    }

    @Override
    public ArrayList<RegistroReporteAlmacen> obtenerRegistrosMes(LocalDate fechaIn) throws Exception {
       ArrayList<RegistroReporteAlmacen> listaRegistros = new ArrayList<>();
       
        // Llamada al Procedimiento Almacenado que creamos en MySQL
        String query = "{CALL obtenerRegistrosReporteAlmacen(?)}";

        // Uso de try-with-resources para asegurar que la conexión se cierre sola
        try (Connection con = this.getConexion(); 
             CallableStatement cs = con.prepareCall(query)) {
            
            // Seteamos los parámetros del SP
            cs.setDate(1, Date.valueOf(fechaIn));
            
            ResultSet rs = cs.executeQuery();
            
            while (rs.next()){
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                String tipo = rs.getString("tipo");
                int idProducto = rs.getInt("idProducto");
                int cantidad = rs.getInt("cantidad");
                String marca = rs.getString("marca");
                
                
                RegistroReporteAlmacen registroMenuAux = new RegistroReporteAlmacen(fecha, tipo, idProducto, cantidad, marca);
                listaRegistros.add(registroMenuAux);
            }

        } catch (SQLException e) {
            throw new Exception("Error al obtener los registros: " + e.getMessage());
        }
        
        return listaRegistros;
    }
}
