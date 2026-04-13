package com.mycompany.restaurante.equipo.a;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GerenteController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private AnchorPane pnlSecundario;

    @FXML
    private Button btnMenu;

    @FXML
    private Button btnAlmacen;

    @FXML
    private Button btnEmpleados;

    @FXML
    private Button btnSalir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Aquí puedes inicializar lógica cuando se cargue la vista
    }    

    @FXML
    private void switchMenu(ActionEvent event) {
        try {
            // 1. Cargar el archivo FXML de la nueva vista 
            AnchorPane view = FXMLLoader.load(getClass().getResource("/com/mycompany/restaurante/equipo/a/GMenu.fxml"));

            // 2. Limpiar el panel secundario por si ya tenía algo cargado
            pnlSecundario.getChildren().clear();

            // 3. Añadir la nueva vista al panel
            pnlSecundario.getChildren().add(view);

            // 4. (Opcional) Ajustar la vista para que ocupe todo el panel
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

        } catch (IOException e) {
            System.err.println("Error al cargar la vista de Menú: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void switchAlmacen(ActionEvent event) {
        System.out.println("Cambiando a vista de Almacén...");
        // Lógica para cargar la vista de almacén dentro de pnlSecundario
    }

    @FXML
    private void switchEmpleados(ActionEvent event) {
        System.out.println("Cambiando a vista de Empleados...");
        // Lógica para cargar la vista de empleados dentro de pnlSecundario
    }

    @FXML
    private void salir(ActionEvent event) {
        // Obtiene la ventana actual y la cierra
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

}
