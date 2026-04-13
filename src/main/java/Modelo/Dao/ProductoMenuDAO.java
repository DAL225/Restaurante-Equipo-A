package Modelo.Dao;

import Modelo.ProductoMenu;
import java.util.ArrayList;

public interface ProductoMenuDAO {
    
    boolean agregarProductoMenu(ProductoMenu producto) throws Exception ;
    
    ArrayList<ProductoMenu> obtenerProductosMenu() throws Exception ;

    
}
