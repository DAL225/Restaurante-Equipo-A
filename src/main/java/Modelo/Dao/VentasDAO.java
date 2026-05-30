/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Dao;
import Modelo.Ventas;
import java.util.ArrayList;
/**
 *
 * @author LENOVO CORE i7
 */
public interface VentasDAO {
    
    boolean agregarVenta(String descripccion, double subtotal, String tipoPago) throws Exception;
    ArrayList<Ventas> obtenerVentas() throws Exception;
}
