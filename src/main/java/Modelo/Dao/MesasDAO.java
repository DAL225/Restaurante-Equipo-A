package Modelo.Dao;

import Modelo.Mesa;
import java.util.ArrayList;

public interface MesasDAO {
    
    boolean agregarMesa (int cantidadPersonas) throws Exception;
    boolean actualizarEstadoMesa(int idMesa, String nuevoEstado,int cantidadPersonas) throws Exception;
    
    ArrayList<Mesa> obtenerMesas () throws Exception;
}
