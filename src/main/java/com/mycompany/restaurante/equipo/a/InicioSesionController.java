package com.mycompany.restaurante.equipo.a;

import Modelo.Empleado;
import Modelo.Impl.EmpleadoDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InicioSesionController implements Initializable {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private Button btnReservacion;

    @FXML
    private Button btnVerPassword;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnOcultarPassword;

    @FXML
    private TextField txtPasswordVisible;

    private boolean passwordVisible = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Estado inicial botones Y campo de texto
        txtPasswordVisible.setVisible(false);
        btnOcultarPassword.setVisible(false);
    }

    @FXML
    private void IniciarSesion(ActionEvent event) {
        String usuario = txtUsuario.getText().trim();
        String password = passwordVisible ? txtPasswordVisible.getText() : txtPassword.getText();
        if (usuario.isBlank() || password.isBlank()) {
            mostrarAlerta("Error", "Completa todos los campos", Alert.AlertType.WARNING);
            return;
        }
        try {
            if (this.validarDatos(usuario, password)){
                EmpleadoDAOImpl DAO = new EmpleadoDAOImpl();
                ArrayList<Empleado> listaEmpleados = DAO.obtenerEmpleados();
                int i = 0;
                String rol = null;
                while (i < listaEmpleados.size()){
                    if (listaEmpleados.get(i).getUsuario().equals(usuario)
                           && listaEmpleados.get(i).getPassword().equals(password)){
                        rol = listaEmpleados.get(i).getRol();
                    }
                    i++;
                }
                if (rol == null){
                    this.mostrarAlerta("Error", "No se encontró al usuario", Alert.AlertType.ERROR);
                } else {
                    if (rol.equals("Gerente")){
                        cambiarEscena("/com/mycompany/restaurante/equipo/a/Gerente");
                    } else if (rol.equals("Recepcionista")){
                        cambiarEscena("/com/mycompany/restaurante/equipo/a/GestionMesas");
                    } else if (rol.equals("Mesero")){
                        cambiarEscena("/com/mycompany/restaurante/equipo/a/Mesero");
                    } else if (rol.equals("Chef")){
                        cambiarEscena("/com/mycompany/restaurante/equipo/a/Chef");
                    } else if (rol.equals("Cajero")){
                            cambiarEscena("/com/mycompany/restaurante/equipo/a/Cajero");
                    }
                }
            } else {
                this.mostrarAlerta("Error","Usuario o contraseña inválidos",Alert.AlertType.ERROR);
            }
        } catch (Exception e){
            this.mostrarAlerta("Error", "Hubo un problema al iniciar sesión", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void salirInicio(ActionEvent event) throws IOException {
        App.setRoot("Inicio");
    }

    @FXML
    private void verPassword(ActionEvent event) {
        passwordVisible = true;

        txtPasswordVisible.setVisible(true);
        txtPasswordVisible.setText(txtPassword.getText());
        txtPassword.setVisible(false);

        btnVerPassword.setVisible(false);
        btnOcultarPassword.setVisible(true);
    }

    @FXML
    private void ocultarPassword(ActionEvent event) {
        passwordVisible = false;

        txtPassword.setVisible(true);
        txtPassword.setText(txtPasswordVisible.getText());
        txtPasswordVisible.setVisible(false);

        btnVerPassword.setVisible(true);
        btnOcultarPassword.setVisible(false);
    }

    private void cambiarEscena(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo cargar la vista", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null); // Esto quita el encabezado gris extra
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private boolean validarDatos(String nombre, String contraseña) throws Exception{
        EmpleadoDAOImpl DAO = new EmpleadoDAOImpl();
        if(DAO.isValidCredentials(nombre, contraseña)){
            return true;
        } else {
            return false;
        }
    }
}