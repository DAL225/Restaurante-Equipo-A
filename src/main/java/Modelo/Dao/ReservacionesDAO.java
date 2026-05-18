package Modelo.Dao;

import Modelo.Reservacion;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public interface ReservacionesDAO {
    
    boolean reservarMesa(int personas, Date fecha, Time hora, String aNombre) throws Exception;
    
    ArrayList<Reservacion> obtenerReservaciones () throws Exception;
    
    Boolean reservacionExiste (int mesa,int personas, Date fecha, Time hora, String aNombre) throws Exception;
    
    int obtenerMesaEspecifica (int personas, Date fecha, Time hora, String aNombre) throws Exception;
    
    Boolean eliminarReservacion (int mesa,int personas, Date fecha, Time hora, String aNombre) throws Exception;
}
