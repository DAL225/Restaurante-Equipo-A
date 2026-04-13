package Modelo;

public class ProductoMenu {

    private int id;
    private String nombre;
    private String categoria;
    private String imagenRuta;
    private Double precio;
    private String ingredientes;
    private Boolean disponibilidad;

    public ProductoMenu(int id, String nombre, String categoria, String imagenRuta, Double precio, String ingredientes, Boolean disponibilidad) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.imagenRuta = imagenRuta;
        this.precio = precio;
        this.ingredientes = ingredientes;
        this.disponibilidad = disponibilidad;
    }

    public ProductoMenu(String nombre, String categoria, String imagenRuta, Double precio, String ingredientes, Boolean disponibilidad) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.imagenRuta = imagenRuta;
        this.precio = precio;
        this.ingredientes = ingredientes;
        this.disponibilidad = disponibilidad;
    }
    
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
