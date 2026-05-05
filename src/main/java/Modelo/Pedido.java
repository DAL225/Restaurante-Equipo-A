/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author LENOVO CORE i7
 */
public class Pedido {
    
    //Variable que representa el id del pedido
    private int idPedido;
    //Variable que representa el nombre del producto
    private String producto;
    //Variable que representa la cantidad de platillos
    private int cantidad;
    //Variable que representa la cantidad a pagar por los platillos
    private double subtotal;
    //Variable que representa el estado del pedido
    private boolean estado;
    //Variable que representa si la orden esta preparada y lista para servirse
    private boolean preparado;
    //Variable que representa la mesa de donde proviene el pedido
    private int mesa;
    
    public Pedido(int idPedido, String producto, int cantidad, double subtotal, boolean estado, boolean preparado, int mesa){
        this.idPedido= idPedido;
        this.producto=producto;
        this.cantidad=cantidad;
        this.subtotal=subtotal;
        this.estado=estado;
        this.preparado=preparado;
        this.mesa=mesa;
     
    }
    
    public int getIdPedido(){
        return this.idPedido;
    }
    
    public void setIdPedido(int idPedido){
        this.idPedido=idPedido;
    }
    
    public String getProducto(){
        return this.producto;
    }
    
    public void setProducto(String nProducto){
        this.producto=nProducto;
    }
    
    public int getCantidad(){
        return this.cantidad;
    }
    
    public void setCantidad(int nCantidad){
        this.cantidad=nCantidad;
    }
    
    public double getSubtotal(){
        return this.subtotal;
    }
    
    public void setSubtotal(double nSubtotal){
        this.subtotal=nSubtotal;
    }

    public boolean getEstado(){
        return this.estado;
    }
    
    public void setEstado(boolean nEstado){
        this.estado=nEstado;
    }
    
    public boolean getPreparado(){
        return this.preparado;
    }
    
    public void setPreparado(boolean nPreparado){
        this.preparado=nPreparado;
    }
    
    public int getPMesa(){
        return this.mesa;
    }
    
    public void setPMesa(int nPMesa){
        this.mesa=nPMesa;
    }
    

    /** Retorna una representación en cadena de la venta */
    @Override
    public String toString() {
        return "pedidosTab{" +
                "idPedido=" + idPedido +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                ", subtotal=" + subtotal +
                ", estado=" + estado +
                ", preparado=" + preparado +
                ", mesa=" + mesa +
                '\'' +
                '}';
    }
    
}
