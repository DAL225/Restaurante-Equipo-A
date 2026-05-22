package Modelo;

/**
 * Representa un producto del menú en el sistema. Contiene la información
 * necesaria para describir un platillo o bebida, incluyendo su categoría,
 * precio, ingredientes y disponibilidad.
 */
public class ProductoMenu {

    /**
     * Identificador único del producto en la base de datos.
     */
    private int id;

    /**
     * Nombre del producto tal como aparece en el menú.
     */
    private String nombre;

    /**
     * Categoría a la que pertenece el producto, por ejemplo: Entradas, Bebidas,
     * Postres.
     */
    private String categoria;

    /**
     * Ruta relativa o absoluta de la imagen representativa del producto.
     */
    private String imagenRuta;

    /**
     * Precio unitario del producto en la moneda local.
     */
    private Double precio;

    /**
     * Ingredientes que componen el producto, separados por coma o salto de
     * línea.
     */
    private String ingredientes;

    /**
     * Indica si el producto está disponible para ser ordenado. Verdadero si
     * está disponible, falso en caso contrario.
     */
    private Boolean disponibilidad;

    /**
     * Construye un ProductoMenu con todos sus atributos, incluyendo el ID. Usar
     * este constructor al recuperar registros existentes desde la base de
     * datos.
     *
     * @param id Identificador único del producto en la base de datos.
     * @param nombre Nombre del producto.
     * @param categoria Categoría a la que pertenece el producto.
     * @param imagenRuta Ruta de la imagen del producto.
     * @param precio Precio unitario del producto.
     * @param ingredientes Ingredientes que componen el producto.
     * @param disponibilidad Verdadero si el producto está disponible, falso en
     * caso contrario.
     */
    public ProductoMenu(int id, String nombre, String categoria, String imagenRuta,
            Double precio, String ingredientes, Boolean disponibilidad) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.imagenRuta = imagenRuta;
        this.precio = precio;
        this.ingredientes = ingredientes;
        this.disponibilidad = disponibilidad;
    }

    /**
     * Construye un ProductoMenu sin ID, para productos nuevos aún no
     * persistidos. El ID será asignado por la base de datos al momento de
     * insertar el registro.
     *
     * @param nombre Nombre del producto.
     * @param categoria Categoría a la que pertenece el producto.
     * @param imagenRuta Ruta de la imagen del producto.
     * @param precio Precio unitario del producto.
     * @param ingredientes Ingredientes que componen el producto.
     * @param disponibilidad Verdadero si el producto está disponible, falso en
     * caso contrario.
     */
    public ProductoMenu(String nombre, String categoria, String imagenRuta,
            Double precio, String ingredientes, Boolean disponibilidad) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.imagenRuta = imagenRuta;
        this.precio = precio;
        this.ingredientes = ingredientes;
        this.disponibilidad = disponibilidad;
    }

    /**
     * Construye un ProductoMenu vacío, sin inicializar ningún atributo. Útil
     * cuando los valores se asignan posteriormente mediante setters.
     */
    public ProductoMenu() {
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getImagenRuta() {
        return imagenRuta;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public Boolean getDisponibilidad() {
        return disponibilidad;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setImagenRuta(String imagenRuta) {
        this.imagenRuta = imagenRuta;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void setDisponibilidad(Boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

}
