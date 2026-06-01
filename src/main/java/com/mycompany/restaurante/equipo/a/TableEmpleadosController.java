package com.mycompany.restaurante.equipo.a;

import Modelo.Dao.EmpleadoDAO;
import Modelo.Empleado;
import Modelo.Impl.EmpleadoDAOImpl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class TableEmpleadosController {

    @FXML
    private TableView<Empleado> tblEmpleados;

    @FXML
    private TableColumn<Empleado, Integer> colId;

    @FXML
    private TableColumn<Empleado, String> colUsuario;

    @FXML
    private TableColumn<Empleado, String> colPassword;
    
    @FXML
    private TableColumn<Empleado, String> colRol;

    @FXML
    private Button btnCerrar;
    
    @FXML
    private Button btnRecargarInfo;
    
    private EmpleadoDAO empleadoDao;

    // Inicialización automática
    /**
     * Inicializa los componentes del controlador después de cargar el archivo
     * FXML.
     *
     * @param url ubicación utilizada para resolver rutas relativas
     * @param rb recursos de internacionalización
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar cómo se deben llenar las columnas usando los atributos de tu clase Empleado
        this.colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        this.colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        this.colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        
        cargarDatos();
    }

    /**
     * Cierra la ventana actual.
     * @param event evento bajo el que se llamara a esta funcion.
     */
    @FXML
    private void cerrar(ActionEvent event) {
        // Obtiene el Stage actual y lo cierra
        Stage stage = (Stage) this.btnCerrar.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Recarga la informacion volviendo a consultar la BD,
     * mediante el metodo cargarDatos.
     */
    @FXML
    private void recargarInfo(ActionEvent event) {
        this.cargarDatos();
    }

    /**
     * Carga de datos a la tabla.
     */
    private void cargarDatos() {
        ObservableList<Empleado> lista = FXCollections.observableArrayList();
        
        try{
            empleadoDao = new EmpleadoDAOImpl();
            
            lista = FXCollections.observableArrayList(empleadoDao.obtenerEmpleados());
            
            if (lista == null || lista.isEmpty()){
                mostrarAlerta("Vacio", "No hay elementos para mostrar ", Alert.AlertType.INFORMATION);
                forzarCierre();
                return;
            }
        } catch (Exception e) {
        mostrarAlerta("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
        forzarCierre();
    }
        
        tblEmpleados.setItems(lista);
        
    }
    
    /**
     * Muestra una alerta con el título, mensaje y tipo especificados.
     *
     * @param titulo título de la ventana de alerta
     * @param mensaje contenido mostrado en la alerta
     * @param tipo tipo de alerta a mostrar
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null); // Esto quita el encabezado gris extra
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    /**
     * Forza el cierre de la ventana en caso de que no haya informacion 
     * para mostrar.
     */
    private void forzarCierre(){
        javafx.application.Platform.runLater(() -> {
                Stage stage = (Stage) tblEmpleados.getScene().getWindow();

                if (stage != null) {
                    stage.close();
                }
            });
    }
}
