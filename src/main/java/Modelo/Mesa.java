package Modelo;

/**
 * Clase que representa a una Mesa del restaurante
 * @author Diego-Aburto-Lara
 * @version 1.2
 */
public class Mesa {
    
    /**
     * Atributo ID que sirve como identificador único de la Mesa
     */
    private int id;
    
    /**
     * Atributo Estado que sirve para determinar si la mesa está ocupada, libre o reservada
     */
    private String estado;
    
    /**
     * Atributo CantidadPersonas que determina cuántas personas pueden ocupar una mesa
     */
    private int cantidadPersonas;
    
    /**
     * Metodo constructor de la Mesa
     * @param id El identificador único de la mesa
     * @param estado El estado de la mesa
     * @param cantidadPersonas La cantidad de personas que pueden estar en la mesa
     */
    public Mesa (int id, String estado, int cantidadPersonas){
        this.id = id;
        this.estado = estado;
        this.cantidadPersonas = cantidadPersonas;
    }
    
    /**
     * Constructor sin ID de la mesa
     * @param estado El estado de la mesa
     * @param cantidadPersonas La cantidad de personas que pueden estar en la mesa
     */
    public Mesa (String estado, int cantidadPersonas){
        this.estado = estado;
        this.cantidadPersonas = cantidadPersonas;
    }
    
    /**
     * Constructor sin parámetros de la mesa
     */
    public Mesa (){
    }

    /**
     * Método que regresa la ID de la mesa
     * @return Integer correspondiente al ID de la mesa
     */
    public int getId() {
        return id;
    }

    /**
     * Método que regresa el estado de la mesa
     * @return String correspondiente al estado de la mesa, pudiendo ser libre, ocupada o reservada
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Método que regrea la cantidad de personas que puede tener la mesa
     * @return Integer correspondiente a la capacidad máxima de personas de la mesa
     */
    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    /**
     * Método que recibe un Integer y lo establece como la ID de la mesa
     * @param id Integer correspondiente a la nueva ID de la mesa
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Método que recibe un String y lo establece como el estado de la mesa
     * @param estado String correspondiente al nuevo estado de la mesa
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Método que recibe un Integer y lo establece como la cantidad máxima de personas posibles en la mesa
     * @param cantidadPersonas Integer correspondiente a la nueva cantidad máxima de personas
     */
    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }
}