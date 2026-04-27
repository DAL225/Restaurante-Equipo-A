package Modelo;


public class Empleado {
    private int id;
    private String usuario;
    private String password;
    private String rol;

    public Empleado(int id, String usuario, String password, String rol) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }
    
    public Empleado() {
    }

    public int getId() {
        return id;
    }
    
    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
}
