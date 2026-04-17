package com.mycompany.restaurante.equipo.a;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;

public class GestionMesasController {

    // 🔗 Componentes del FXML
    @FXML private AnchorPane root;
    @FXML private AnchorPane panelFormulario;

    @FXML private Label lblMesas;
    @FXML private Label lblidMesa;
    @FXML private Label Estado;

    @FXML private Spinner<Integer> numMesa;
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
        // TODO: mostrar lista de mesas
        System.out.println("Ver lista de mesas");
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