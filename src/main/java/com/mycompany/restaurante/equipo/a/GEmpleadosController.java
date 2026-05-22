package com.mycompany.restaurante.equipo.a;

import Modelo.Dao.EmpleadoDAO;
import Modelo.Empleado;
import Modelo.Impl.EmpleadoDAOImpl;
import java.io.IOException;
import static java.lang.Integer.parseInt;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
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
    private TextField txtId;
    @FXML
    private ChoiceBox<String> selecRol;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Ellipse paOcultarRol;
    @FXML
    private Circle paOcultarID;
    
    private EmpleadoDAO empleadoDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ocultarSubpaneles();
        selecRol.getItems().addAll("Mesero", "Chef", "Cajero", "Recepcionista");

    }
    
    private void ocultarSubpaneles(){
        pnlAgregarEmpleado.setVisible(false);
        // resto se Subpaneles eliminar, modificar, etc.
    }
    
    @FXML
    private void switchAgregarEmpleado() {
        // Cargar roles al ChoiceBox
        selecRol.setVisible(true);
        paOcultarRol.setVisible(false);
        pnlAgregarEmpleado.setVisible(true);
        paOcultarID.setVisible(true);
        txtId.setVisible(false);
        txtPassword.setVisible(true);
        txtUsuario.setVisible(true);
        btnAgregar.setVisible(true);
        btnModificar.setVisible(false);
        btnEliminar.setVisible(false);
        //resto de paneles false
    }
    
    @FXML
    private void switchModificarEmpleado() {
        selecRol.setVisible(true);
        paOcultarRol.setVisible(false);
        pnlAgregarEmpleado.setVisible(true);
        paOcultarID.setVisible(false);
        txtId.setVisible(true);
        txtPassword.setVisible(true);
        txtUsuario.setVisible(true);
        btnModificar.setVisible(true);
        btnAgregar.setVisible(false);
        btnEliminar.setVisible(false);
    }

    @FXML
    private void switchEliminarEmpleado() {
        selecRol.setVisible(false);
        paOcultarRol.setVisible(true);
        pnlAgregarEmpleado.setVisible(true);
        paOcultarID.setVisible(false);
        txtId.setVisible(true);
        txtPassword.setVisible(false);
        txtUsuario.setVisible(false);
        btnEliminar.setVisible(true);
        btnAgregar.setVisible(false);
        btnModificar.setVisible(false);
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

    @FXML
    private void modificarEmpleado() {
        if (this.idVacia()) {
            mostrarAlerta("Campo Vacío", "Por favor rellene el campo de ID para continuar", Alert.AlertType.WARNING);
            return; // detiene el método aquí mismo
        }
        try {
            int ID = parseInt(txtId.getText().trim());
            try {
                empleadoDao = new EmpleadoDAOImpl();
                int fallo = 0;
                if (!txtUsuario.getText().trim().isBlank()){
                    String usuario = txtUsuario.getText().trim();
                    if(empleadoDao.setEmpleadoUsuario(ID, usuario)){
                        System.out.println("Usuario");
                    } else {
                        fallo ++;
                    }
                }
                if (!txtPassword.getText().trim().isBlank()){
                    String password = txtPassword.getText().trim();
                    if(empleadoDao.setEmpleadoPassword(ID, password)){
                        System.out.println("Password");
                    } else {
                        fallo ++;
                    }
                }
                if (selecRol.getValue() != null){
                    String rol = selecRol.getValue();
                    if(empleadoDao.setEmpleadoRol(ID, rol)){
                        System.out.println("Rol");
                    } else {
                        fallo ++;
                    }
                }
                if (fallo > 0){
                    mostrarAlerta("Fracaso", "No se pudo encontrar al Empleado", Alert.AlertType.INFORMATION);
                } else {
                    mostrarAlerta("Exito", "Los datos se actualizaron correctamente", Alert.AlertType.INFORMATION);
                }
            } catch (Exception e){
                mostrarAlerta("Error ", e.getMessage(), Alert.AlertType.ERROR);
                if (e.getMessage().equals("Error de acceso a la BD, intente mas tarde")){
                    limpiarCamposAgregar();
                    this.pnlAgregarEmpleado.setVisible(false);
                }
            }
        } catch (NumberFormatException e){
            mostrarAlerta("Fracaso", "Por favor introduzca un número como ID", Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void eliminarEmpleado() {
        if (this.idVacia()) {
            mostrarAlerta("Campo Vacío", "Por favor rellene el campo de ID para continuar", Alert.AlertType.WARNING);
            return; // detiene el método aquí mismo
        }
        try {
            int ID = parseInt(txtId.getText().trim());
            
            try {
                empleadoDao = new EmpleadoDAOImpl();
                if (empleadoDao.removeEmpleado(ID)) {
                    mostrarAlerta("Éxito", "Empleado eliminado correctamente", Alert.AlertType.INFORMATION);
                    limpiarCamposAgregar();
                    return;
                }
                mostrarAlerta("Fracaso", "El empleado no se pudo eliminar", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                mostrarAlerta("Error ", e.getMessage(), Alert.AlertType.ERROR);
                if (e.getMessage().equals("Error de acceso a la BD, intente mas tarde")){
                    limpiarCamposAgregar();
                    this.pnlAgregarEmpleado.setVisible(false);
                }
            }
        } catch (NumberFormatException e){
            mostrarAlerta("Fracaso", "Por favor introduzca un número como ID", Alert.AlertType.INFORMATION);
        }
    }
    
    private boolean camposVaciosAgregar() {
        return txtUsuario.getText().trim().isBlank()
                || txtPassword.getText().trim().isBlank()
                || selecRol.getValue() == null;
    }
    
    private boolean idVacia(){
        return txtId.getText().trim().isBlank();
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
        txtId.clear();
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
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/mycompany/restaurante/equipo/a/Asistencia.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Registro de Asistencia");
            stage.setScene(new Scene(root));
            stage.show();

            System.out.println("Mostrando seccion Asistencia...");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}