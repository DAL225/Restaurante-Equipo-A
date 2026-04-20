package Modelo;

import java.sql.Date;
import java.sql.Time;

public class Reservacion {
    
    private int id;
    private int mesa;
    private int personas;
    private Date fecha;
    private Time hora;
    private String aNombre;
    
    public Reservacion (int id, int mesa, int personas, Date fecha, Time hora, String aNombre){
        this.id = id;
        this.mesa = mesa;
        this.personas = personas;
        this.fecha = fecha;
        this.hora = hora;
        this.aNombre = aNombre;
    }
    
    public Reservacion (int mesa, int personas, Date fecha, Time hora, String aNombre){
        this.mesa = mesa;
        this.personas = personas;
        this.fecha = fecha;
        this.hora = hora;
        this.aNombre = aNombre;
    }
    
    public Reservacion (){
    }

    public int getId() {
        return id;
    }

    public int getMesa() {
        return mesa;
    }

    public int getPersonas() {
        return personas;
    }

    public Date getFecha() {
        return fecha;
    }

    public Time getHora() {
        return hora;
    }

    public String getaNombre() {
        return aNombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public void setPersonas(int personas) {
        this.personas = personas;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public void setaNombre(String aNombre) {
        this.aNombre = aNombre;
    }
}