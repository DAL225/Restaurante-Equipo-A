package Modelo.Dao;

import Modelo.Reservacion;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Interfaz que sirve como DAO para la clase Reservaciones
 * @author Diego-Aburto-Lara
 * @version 1.1
 */
public interface ReservacionesDAO {
    
    /**
     * Método que recibe una serie de parámetros y en base a ellos reserva una Mesa
     * @param personas Cantidad de personas que ocuparán la mesa
     * @param fecha Fecha de la reservación
     * @param hora Hora de la reservación
     * @param aNombre Nombre de quien hizo la reservación
     * @return Boolean que será true de haberse reservado correctamente la mesa, false si no
     * @throws Exception Excepción general
     */
    boolean reservarMesa(int personas, Date fecha, Time hora, String aNombre) throws Exception;
    
    /**
     * Método que regresa una lista de Reservaciones
     * @return Arraylist de reservaciones
     * @throws Exception Excepción general
     */
    ArrayList<Reservacion> obtenerReservaciones () throws Exception;
    
    /**
     * Método que recibe una serie de parámetros y comprueba si una reservación con dichos parámetros existe
     * @param mesa ID de la mesa reservada
     * @param personas Cantidad de personas que ocuparán la mesa
     * @param fecha Fecha de la reservación
     * @param hora Hora de la reservación
     * @param aNombre Nombre de quien hizo la reservación
     * @return Boolean que será true de existir una reservación con dichos parámetros, y false si no
     * @throws Exception Excepción general
     */
    Boolean reservacionExiste (int mesa,int personas, Date fecha, Time hora, String aNombre) throws Exception;
    
    /**
     * Método que recibe una serie de parámetros y regresa la ID de la mesa de la reservación que coincida con dichos parámetros
     * @param personas Cantidad de personas que ocuparán la mesa
     * @param fecha Fecha de la reservación
     * @param hora Hora de la reservación
     * @param aNombre Nombre de quién hizo la reservación
     * @return Integer correspondiente a la ID de la mesa que coincida con los parámetros dados
     * @throws Exception Excepción general
     */
    int obtenerMesaEspecifica (int personas, Date fecha, Time hora, String aNombre) throws Exception;
    
    /**
     * Método que recibe una serie de parámetros y elimina la reservación que coincida con dichos parámetros
     * @param mesa ID de la mesa reservada
     * @param personas Cantidad de personas que ocuparán la mesa
     * @param fecha Fecha de la reservación
     * @param hora Hora de la reservación
     * @param aNombre Nombre de quién hizo la reservación
     * @return Boolean que será true de lograrse eliminar la reservación, y false de no hacerlo
     * @throws Exception Excepción general
     */
    Boolean eliminarReservacion (int mesa,int personas, Date fecha, Time hora, String aNombre) throws Exception;
}