package Modelo.Dao;

import Modelo.ProductoAlmacen;
import java.util.ArrayList;

public interface ProductoAlmacenDAO {
    
    /**
     * Crea un nuevo producto de almacen(materia prima) en la base de datos.
     * 
     * @param producto producto a almacenar con toda su informacion.
     * @return true si el producto se creó correctamente, false en caso
     * contrario.
     * @throws Exception Si ocurre alguna Excepcion
     */
    boolean agregarProductoAlmacen(ProductoAlmacen producto) throws Exception ;
    
    /**
     * Obtiene la lista de productos del almacen(materia prima) almacenados.
     * 
     * @return lista de productos del almacen
     * @throws Exception Si ocurre un error al ejecutar la consulta.
     */
    ArrayList<ProductoAlmacen> obtenerProductosAlmacen() throws Exception ;
    
    /**
     * Obtiene un producto del almacén (materia prima).
     *
     * @return producto del almacén
     * @throws Exception Si ocurre un error al ejecutar la consulta.
     */
    ProductoAlmacen obtenerProductoAlmacen(int id) throws Exception;

    /**
     * Retira de stock de un producto de almacen la cantidad indicada
     * 
     * @param id id del producto a disminuir stock.
     * @param cantidad cantidad a retirar
     * @return true si se realizo con exito la disminucion, false en caso
     * contrario.
     * @throws Exception Si ocurre un error al realizar el procedimiento.
     */
    boolean retirarStock(int id, int cantidad) throws Exception ;
    
    /**
     * Modifica un producto de almacén (materia prima) en la base de datos.
     *
     * @param producto producto con la información actualizada.
     * @return true si el producto se modificó correctamente, false en caso
     * contrario.
     * @throws Exception Si ocurre alguna excepción.
     */
    boolean modificarProductoAlmacen(ProductoAlmacen producto) throws Exception;
}
