package com.mycompany.restaurante.equipo.a;

import Modelo.Empleado;
import Modelo.Impl.EmpleadoDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GEmpleadosController implements Initializable {

    @FXML
    private AnchorPane pnlEmpleados;
    @FXML
    private AnchorPane pnlAgregarEmpleado;
    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtPassword;

    @FXML
    private ChoiceBox<String> selecRol;

    @FXML
    private Button btnAgregar;
    
    private EmpleadoDAOImpl empleadoDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ocultarSubpaneles();
        
        // Cargar roles al ChoiceBox
        selecRol.getItems().addAll("Mesero", "Chef", "Cajero", "Recepcionista");
    }
    
    private void ocultarSubpaneles(){
        pnlAgregarEmpleado.setVisible(false);
        // resto se Subpaneles eliminar, modificar, etc.
    }
    
    @FXML
    private void switchAgregarEmpleado() {
        pnlAgregarEmpleado.setVisible(true);
        //resto de paneles false
    }

    @FXML
    private void agregarEmpleado() {

        // 1. Validar campos
        if (this.camposVaciosAgregar()) {
            mostrarAlerta("Campos Vacíos", "Por favor rellene todos los campos para continuar", Alert.AlertType.WARNING);
            return; // detiene el método aquí mismo
        }

        String usuario = txtUsuario.getText().trim();
        String password = txtPassword.getText().trim();
        String rol = selecRol.getValue();

        if (!longitudCaracteresAgregarValida()) {
            return;
        }

        Empleado empleado = new Empleado(0, usuario, password, rol);

        // Intenta agregar el empleado
        try {
            empleadoDao = new EmpleadoDAOImpl();
            
            if (empleadoDao.agregarEmpleado(empleado)) {

                mostrarAlerta("Éxito", "Empleado agregado correctamente", Alert.AlertType.INFORMATION);
                limpiarCamposAgregar();
                return;
            }

            mostrarAlerta("Fracaso", "El empleado no se pudo agregar", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error ", e.getMessage(), Alert.AlertType.ERROR);
            if (e.getMessage().equals("Error de acceso a la BD, intente mas tarde")){
                limpiarCamposAgregar();
                this.pnlAgregarEmpleado.setVisible(false);
            }
        }
    }

    private boolean camposVaciosAgregar() {
        return txtUsuario.getText().trim().isBlank()
                || txtPassword.getText().trim().isBlank()
                || selecRol.getValue() == null;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null); // Esto quita el encabezado gris extra
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    private void limpiarCamposAgregar() {
        txtUsuario.clear();
        txtPassword.clear();
        selecRol.setValue(null);
    }
    
    /*
        Limita los caracteres evitando cortes del texto en la BD.
    */
    private boolean longitudCaracteresAgregarValida(){
        if(txtUsuario.getText().trim().length() > 50){
            mostrarAlerta("Error", "Limite de 50 caracteres excedido en usuario", Alert.AlertType.WARNING);
            return false;
        }
        
        if (txtPassword.getText().trim().length() != 8) {
            mostrarAlerta("Error", "El password debe tener exactamente 8 caracteres", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    @FXML
    private void switchModificarEmpleado() {
        System.out.println("Vista: Modificar empleado");
    }

    @FXML
    private void switchEliminarEmpleado() {
        System.out.println("Vista: Eliminar empleado");
    }

    @FXML
    private void verEmpleados() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/mycompany/restaurante/equipo/a/TableEmpleados.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Empleados");
            stage.setScene(new Scene(root));
            stage.show();

            System.out.println("Mostrando lista de empleados...");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void asistencia() {
        System.out.println("Vista: Asistencia");
    }
}