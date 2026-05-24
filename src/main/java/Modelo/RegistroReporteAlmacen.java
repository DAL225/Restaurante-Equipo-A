package Modelo;

import java.time.LocalDate;

/**
 * Esta clase representa cada operacion o registro de algun movimiento 
 * de stock en productos de Almacen
 * 
 * @author amiss
 */
public class RegistroReporteAlmacen {
    
    /**
     * Fecha en que se realizo
     */
    private LocalDate fecha;
    /**
     * Tipo de movimiento en el stock(ingreso, retiro, eliminacion[del producto])
     */
    private String tipo;
    
    /**
     * Id del producto que se modifico su stock
     */
    private int idproducto;
    
    /**
     * Cantidad que fue agregada o disminuida
     */
    private int cantidadproducto;
    
    /**
     * Marca del producto
     */
    private String marca;

    public RegistroReporteAlmacen(LocalDate fecha, String tipo, int idproducto, int cantidadproducto, String marca) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.idproducto = idproducto;
        this.cantidadproducto = cantidadproducto;
        this.marca = marca;
    }
    
    public RegistroReporteAlmacen() {
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public int getCantidadproducto() {
        return cantidadproducto;
    }

    public String getMarca() {
        return marca;
    }
    
    

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public void setCantidadproducto(int cantidadproducto) {
        this.cantidadproducto = cantidadproducto;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    
    
    
}
