package Modelo;

/**
 * Representa un producto almacenado en el inventario del sistema.
 * Contiene la información necesaria para identificar y gestionar
 * los productos disponibles en el almacén, incluyendo su marca,
 * tipo, cantidad en stock y proveedor.
 */
public class ProductoAlmacen {

    /** Identificador único del producto en la base de datos. */
    private int id;

    /** Marca o fabricante del producto. */
    private String marca;

    /** Tipo o categoría del producto dentro del almacén. */
    private String tipo;

    /** Cantidad de unidades disponibles actualmente en el almacén. */
    private int stock;

    /** Nombre del proveedor que suministra el producto. */
    private String proveedor;

    /**
     * Construye un ProductoAlmacen sin ID, para productos nuevos aún no persistidos.
     * El ID será asignado por la base de datos al momento de insertar el registro.
     *
     * @param marca      Marca o fabricante del producto.
     * @param tipo       Tipo o categoría del producto.
     * @param stock      Cantidad de unidades disponibles en el almacén.
     * @param proveedor  Nombre del proveedor que suministra el producto.
     */
    public ProductoAlmacen(String marca, String tipo, int stock, String proveedor) {
        this.marca = marca;
        this.tipo = tipo;
        this.stock = stock;
        this.proveedor = proveedor;
    }

    /**
     * Construye un ProductoAlmacen con todos sus atributos, incluyendo el ID.
     * Usar este constructor al recuperar registros existentes desde la base de datos.
     *
     * @param id         Identificador único del producto en la base de datos.
     * @param marca      Marca o fabricante del producto.
     * @param tipo       Tipo o categoría del producto.
     * @param stock      Cantidad de unidades disponibles en el almacén.
     * @param proveedor  Nombre del proveedor que suministra el producto.
     */
    public ProductoAlmacen(int id, String marca, String tipo, int stock, String proveedor) {
        this.id = id;
        this.marca = marca;
        this.tipo = tipo;
        this.stock = stock;
        this.proveedor = proveedor;
    }

    /**
     * Construye un ProductoAlmacen vacío, sin inicializar ningún atributo.
     * Útil cuando los valores se asignan posteriormente mediante setters.
     */
    public ProductoAlmacen() {
    }

    /**
     * Obtiene el identificador único del producto.
     *
     * @return El ID del producto.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la marca o fabricante del producto.
     *
     * @return La marca del producto.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Obtiene el tipo o categoría del producto.
     *
     * @return El tipo del producto.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Obtiene la cantidad de unidades disponibles en el almacén.
     *
     * @return El stock actual del producto.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Obtiene el nombre del proveedor que suministra el producto.
     *
     * @return El proveedor del producto.
     */
    public String getProveedor() {
        return proveedor;
    }

    /**
     * Establece el identificador único del producto.
     *
     * @param id Nuevo ID del producto.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece la marca o fabricante del producto.
     *
     * @param marca Nueva marca del producto.
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Establece el tipo o categoría del producto.
     *
     * @param tipo Nuevo tipo del producto.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Establece la cantidad de unidades disponibles en el almacén.
     *
     * @param stock Nueva cantidad en stock del producto.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Establece el nombre del proveedor que suministra el producto.
     *
     * @param proveedor Nuevo proveedor del producto.
     */
    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
}