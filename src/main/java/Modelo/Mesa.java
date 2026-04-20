package Modelo;

public class Mesa {
    
    private int id;
    private String estado;
    private int cantidadPersonas;
    
    public Mesa (int id, String estado, int cantidadPersonas){
        this.id = id;
        this.estado = estado;
        this.cantidadPersonas = cantidadPersonas;
    }
    
    public Mesa (String estado, int cantidadPersonas){
        this.estado = estado;
        this.cantidadPersonas = cantidadPersonas;
    }
    
    public Mesa (){
    }

    public int getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

}
