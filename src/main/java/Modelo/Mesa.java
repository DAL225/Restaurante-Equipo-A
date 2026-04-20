package Modelo;

public class Mesa {
    
    private int id;
    private boolean estado;
    private int cantidadPersonas;
    
    public Mesa (int id, boolean estado, int cantidadPersonas){
        this.id = id;
        this.estado = estado;
        this.cantidadPersonas = cantidadPersonas;
    }
    
    public Mesa (boolean estado, int cantidadPersonas){
        this.estado = estado;
        this.cantidadPersonas = cantidadPersonas;
    }
    
    public Mesa (){
    }

    public int getId() {
        return id;
    }

    public boolean isEstado() {
        return estado;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }
}
