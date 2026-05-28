package Modelo;

import java.sql.Date;
import java.sql.Time;

/**
 * Clase que representa a una reservación de una mesa
 * @author Diego-Aburto-Lara
 * @version 1.1
*/
public class Reservacion {
    
    /**
     * Atributo ID que sirve como identificador único de la reservación
     */
    private int id;
    
    /**
     * Atributo Mesa que representa el ID de la mesa correspondiente a la reservación
     */
    private int mesa;
    
    /**
     * Atributo Personas que representa la cantidad de personas que usarán la mesa
     */
    private int personas;
    
    /**
     * Atributo Fecha que representa la fecha de la reservación
     */
    private Date fecha;
    
    /**
     * Atributo Hora que representa la hora de la reservación
     */
    private Time hora;
    
    /**
     * Atributo aNombre que representa el nombre de la persona que reservó la mesa
     */
    private String aNombre;
    
    /**
     * Método constructor de la Reservación
     * @param id El identificador único de la reservación
     * @param mesa El ID de la mesa de la reservación
     * @param personas El número de personas que ocuparán la mesa
     * @param fecha La fecha de la reservación
     * @param hora La hora de la reservación
     * @param aNombre El nombre de quién reservó la mesa
     */
    public Reservacion (int id, int mesa, int personas, Date fecha, Time hora, String aNombre){
        this.id = id;
        this.mesa = mesa;
        this.personas = personas;
        this.fecha = fecha;
        this.hora = hora;
        this.aNombre = aNombre;
    }
    
    /**
     * Constructor sin ID de la reservación
     * @param mesa El ID de la mesa de la reservación
     * @param personas El número de personas que ocuparán la mesa
     * @param fecha La fecha de la reservación
     * @param hora La hora de la reservación
     * @param aNombre El nombre de quién reservó la mesa
     */
    public Reservacion (int mesa, int personas, Date fecha, Time hora, String aNombre){
        this.mesa = mesa;
        this.personas = personas;
        this.fecha = fecha;
        this.hora = hora;
        this.aNombre = aNombre;
    }

    /**
     * Constructor sin parámetros de la reservación
     */
    public Reservacion (){
    }

    /**
     * Método que regresa la ID de la reservación
     * @return Integer correspondiente a la ID de la reservación
     */
    public int getId() {
        return id;
    }

    /**
     * Método que regresa la ID de la mesa de la reservación
     * @return Inreger correspondiente al ID de la mesa reservada
     */
    public int getMesa() {
        return mesa;
    }

    /**
     * Método que regresa la cantidad de personas que ocuparán la mesa
     * @return Integer correspondiente a la cantidad de personas que ocuparán la mesa
     */
    public int getPersonas() {
        return personas;
    }

    /**
     * Método que regresa la fecha de la reservación
     * @return Date correspondiente a la fecha de la reservación
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Método que regresa la hora de la reservación
     * @return Time correspondiente a la hora de la reservación
     */
    public Time getHora() {
        return hora;
    }

    /**
     * Método que regresa el nombre de quien hizo la reservación
     * @return String correspondiente al nombre del que hizo la reservación
     */
    public String getaNombre() {
        return aNombre;
    }

    /**
     * Método que recibe un Integer y lo establece como la ID de la reservación
     * @param id Integer correspondiente a la nueva ID de la reservación
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Método que recibe un Integer y lo establece como la ID de la mesa reservada
     * @param mesa Integer correspondiente a la nueva ID de la mesa reservada
     */
    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    /**
     * Método que recibe un Integer y lo establece ocmo la cantidad de personas que ocuparán la mesa
     * @param personas Integer correspondiente a la nueva cantidad de personas que ocuparán la mesa
     */
    public void setPersonas(int personas) {
        this.personas = personas;
    }

    /**
     * Método que recibe un Date y lo establece como la fecha de la reservación
     * @param fecha Date correspondiente a la nueva fecha de la reservación
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Método que recibe un Time y lo establece como la hora de la reservación
     * @param hora Time correspondiente a la nueva hora de la reservación
     */
    public void setHora(Time hora) {
        this.hora = hora;
    }

    /**
     * Método que recibe un String y lo establece como el nombre de quien hizo la reservación
     * @param aNombre String correspondiente al nuevo nombre de quien hizo la reservación
     */
    public void setaNombre(String aNombre) {
        this.aNombre = aNombre;
    }
}