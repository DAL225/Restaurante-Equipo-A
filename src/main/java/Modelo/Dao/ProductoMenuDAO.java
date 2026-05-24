package Modelo.Dao;

import Modelo.ProductoMenu;
import java.util.ArrayList;

public interface ProductoMenuDAO {
    
    /**
     * Crea un nuevo producto de menu en la base de datos.
     * 
     * @param producto producto a almacenar con toda su informacion.
     * @return true si el producto se creó correctamente, false en caso
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

    /**
     * Obtiene un producto del menu
     *
     * @return producto del menu
     * @throws Exception Si ocurre un error al ejecutar la consulta.
     */
    ProductoMenu obtenerProductoMenu(int id) throws Exception;
    
    /**
     * Modifica un producto del Menu en la base de datos.
     *
     * @param producto producto con la información actualizada.
     * @return true si el producto se modificó correctamente, false en caso
     * contrario.
     * @throws Exception Si ocurre alguna excepción.
     */
    boolean modificarProductoMenu(ProductoMenu producto) throws Exception ;

}
