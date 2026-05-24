package Modelo.Dao;

import Modelo.RegistroReporteAlmacen;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author amiss
 */
public interface RegistroReporteAlmacenDAO {
    
    ArrayList<RegistroReporteAlmacen> obtenerRegistrosMes(LocalDate fecha) throws Exception ;
}
