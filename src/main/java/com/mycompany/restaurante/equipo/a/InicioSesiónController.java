package com.mycompany.restaurante.equipo.a;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author aburt
 */
public class InicioSesiónController implements Initializable {

    @FXML
    private TextField nombreUsuario;
    @FXML
    private PasswordField contraseña;
    @FXML
    private Button botonIniciarSesion;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
  
    @FXML
    private void IniciarSesion(ActionEvent event) {
        String x = nombreUsuario.getText();
        String y = contraseña.getText();
        System.out.println(x+y);
    }
}