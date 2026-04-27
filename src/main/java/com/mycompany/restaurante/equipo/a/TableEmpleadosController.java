package com.mycompany.restaurante.equipo.a;

import Modelo.Dao.EmpleadoDAO;
import Modelo.Empleado;
import Modelo.Impl.EmpleadoDAOImpl;
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
    
    private EmpleadoDAO empleadoDao;

    // Lista observable para los datos de la tabla
    private ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar cómo se deben llenar las columnas usando los atributos de tu clase Empleado
        this.colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        this.colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        this.colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        
        cargarDatos();
    }

    @FXML
    private void cerrar(ActionEvent event) {
        // Obtiene el Stage actual y lo cierra
        Stage stage = (Stage) this.btnCerrar.getScene().getWindow();
        stage.close();
    }

    // Carga de datos a la tabla
    private void cargarDatos() {
        ObservableList<Empleado> lista = FXCollections.observableArrayList();
        
        try{
            empleadoDao = new EmpleadoDAOImpl();
            
            lista = FXCollections.observableArrayList(empleadoDao.obtenerEmpleados());
            
            if (lista.isEmpty() || lista == null){
                mostrarAlerta("Vacio", "No hay elementos para mostrar ", Alert.AlertType.INFORMATION);
                //cerrar();
                return;
            }
        }catch(Exception e) {
            mostrarAlerta("Error ", "Error al cargar los datos", Alert.AlertType.ERROR);
        }
        
        tblEmpleados.setItems(lista);
        
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null); // Esto quita el encabezado gris extra
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
