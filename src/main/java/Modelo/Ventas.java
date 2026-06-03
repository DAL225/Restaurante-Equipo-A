/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author LENOVO CORE i7
 */
public class Ventas {
    private int idVenta;
    private String descripcion;
    private double subtotal;
    private double iva;
    private double total;
    private String tipoPago;
    
    public Ventas (int idVenta, String descripcion, double subtotal, double iva, double total, String tipoPago){
        this.idVenta= idVenta;
        this.descripcion= descripcion;
        this.subtotal= subtotal;
        this.iva=iva;
        this.total=total;
        this.tipoPago=tipoPago;
    };
    
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int nIdVenta) {
        this.idVenta = nIdVenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String nDescripcion) {
        this.descripcion = nDescripcion;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double nSubtotal) {
        this.subtotal = nSubtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double nIva) {
        this.iva = nIva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double nTotal) {
        this.total = nTotal;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String nTipoPago) {
        this.tipoPago = nTipoPago;
    }
    


    @Override
    public String toString() {
        return "Id: "+idVenta+
                " Descripccion: "+ descripcion +
                " Subtotal: "+subtotal+
                " Iva: "+iva+
                " Total: "+total+
                " Tipo de pago: "+tipoPago;
    }
    
}
