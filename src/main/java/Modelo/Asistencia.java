package Modelo;

import javafx.beans.property.*;
import java.util.HashMap;
import java.util.Map;

public class Asistencia {

    private final IntegerProperty id;
    private final StringProperty usuario;

    /**
     * El Map asocia el número del día (Integer) con su estado
     * (BooleanProperty). Usamos BooleanProperty para que el TableView detecte
     * automáticamente cuando el usuario marca o desmarca el CheckBox.
     */
    private final Map<Integer, BooleanProperty> registroMensual;

    public Asistencia(int id, String usuario) {
        this.id = new SimpleIntegerProperty(id);
        this.usuario = new SimpleStringProperty(usuario);
        this.registroMensual = new HashMap<>();
    }
    
    // Método clave para el TableView
    public BooleanProperty insertarDiaProperty(int dia) {
        // Si el día no existe en el mapa, lo crea con valor 'false' por defecto
        return registroMensual.computeIfAbsent(dia, k -> new SimpleBooleanProperty(false));
    }

    // Getters para las columnas fijas (Id y Usuario)
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getUsuario() {
        return usuario.get();
    }

    public StringProperty usuarioProperty() {
        return usuario;
    }

    /**
     * Retorna el mapa completo por si necesitas recorrerlo al guardar en la
     * base de datos.
     */
    public Map<Integer, BooleanProperty> getRegistroMensual() {
        return registroMensual;
    }
}
