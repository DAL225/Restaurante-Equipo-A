package Modelo.Dao;

import Modelo.RegistroReporteAlmacen;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author amiss
 */
public interface RegistroReporteAlmacenDAO {
    
    /**
     * Obtiene la lista de reportes de movimientos al almacen 
     * en una determinada fecha.
     * 
     * @return lista de resgistros de reportes del almacen.
     * @throws Exception Si ocurre un error al ejecutar la consulta.
     */
    ArrayList<RegistroReporteAlmacen> obtenerRegistrosMes(LocalDate fecha) throws Exception ;
}
