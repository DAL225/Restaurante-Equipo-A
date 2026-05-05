package Modelo.Dao;

import Modelo.ProductoMenu;
import java.util.ArrayList;

public interface ProductoMenuDAO {
    
    /**
     * Crea un nuevo producto de menu en la base de datos.
     *
     * @param role El rol del usuario.
     * @param username El nombre de usuario.
     * @param password La contraseña del usuario.
     * @return true si el usuario se creó correctamente, false en caso
     * contrario.
     * @throws Exception Si ocurre alguna Excepcion
     */
    boolean agregarProductoMenu(ProductoMenu producto) throws Exception ;
    
    /**
     * Obtiene la lista de productos del menu almacenados.
     * 
     * @return lista de productos del menu
     * @throws Exception Si ocurre un error al ejecutar la consulta.
     */
    ArrayList<ProductoMenu> obtenerProductosMenu() throws Exception ;

    
}
