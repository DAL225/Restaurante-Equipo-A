package Modelo.Dao;

import Modelo.Reservacion;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public interface ReservacionesDAO {
    
    boolean reservarMesa(int personas, Date fecha, Time hora, String aNombre) throws Exception;
    
    ArrayList<Reservacion> obtenerReservaciones () throws Exception;
    
    int obtenerMesaEspecifica (int personas, Date fecha, Time hora, String aNombre) throws Exception;
}
