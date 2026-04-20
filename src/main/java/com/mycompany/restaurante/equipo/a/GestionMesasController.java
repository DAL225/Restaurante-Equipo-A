package com.mycompany.restaurante.equipo.a;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GestionMesasController {

    // 🔗 Componentes del FXML
    @FXML private AnchorPane root;
    @FXML private AnchorPane panelFormulario;

    @FXML private Label lblMesas;
    @FXML private Label lblidMesa;
    @FXML private Label Personas;
    @FXML private Label Estado;

    @FXML private Spinner<Integer> numMesa;
    @FXML private Spinner<Integer> numpersonas;
    @FXML private ChoiceBox<String> selecEstado;

    @FXML private Button btnAgregarMesa;
    @FXML private Button btnVerLista;
    @FXML private Button btnModificar;
    @FXML private Button btnSalir;

    // 🚀 Inicialización (opcional pero útil)
    @FXML
    public void initialize() {
        // TODO: inicializar datos
        selecEstado.getItems().addAll("Disponible", "Ocupada", "Reservada");
    }

    // 🎯 Métodos que usa tu interfaz

    @FXML
    private void agregarMesa() {
        // TODO: lógica para agregar mesa
        System.out.println("Agregar mesa");
    }

    @FXML
    private void verListaMesas() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/mycompany/restaurante/equipo/a/ListaMesas.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("ListaMesas");
            stage.setScene(new Scene(root));
            stage.show();

            System.out.println("Ver lista de mesas");

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    private void modificarMesa() {
        // TODO: modificar mesa
        System.out.println("Modificar mesa");
    }

    
    @FXML
    private void salir() {
        System.out.println("Saliendo...");
        System.exit(0);
    }
}