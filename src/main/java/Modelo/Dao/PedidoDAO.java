/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Modelo.Dao;
import Modelo.Pedido;
import java.util.ArrayList;

public interface PedidoDAO {
    
    boolean agregarPedido(String producto, int cantidad, int mesa) throws Exception;
    ArrayList<Pedido> cargarPedidos() throws Exception;
    ArrayList<Pedido> cargarPedidosMesa(int pMesa) throws Exception;

    
}