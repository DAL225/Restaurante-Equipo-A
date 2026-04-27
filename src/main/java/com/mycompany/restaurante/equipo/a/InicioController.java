package com.mycompany.restaurante.equipo.a;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class InicioController implements Initializable{

    @FXML
    private Button botonTarbajador;
    
    @FXML
    private Button botonCliente;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void ingresarTrabajador (ActionEvent event) throws IOException {
        App.setRoot("InicioSesion");
    }
    
    @FXML
    private void ingresarCliente (ActionEvent event) throws IOException {
        App.setRoot("ClienteReservar");
    }
}