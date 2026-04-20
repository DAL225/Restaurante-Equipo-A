/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurante.equipo.a;

/**
 *
 * @author donts
 */
import Modelo.Mesa;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListaMesasController {

    @FXML
    private Label lblListaMesas;

    @FXML
    private TableView<Mesa> tableMesas;

    @FXML
    private TableColumn<Mesa, Integer> columMesa;

    @FXML
    private TableColumn<Mesa, String> columEstado;

    @FXML
    private TableColumn<Mesa, Integer> columNumpersonas;

    @FXML
    private Button btnOrdenar;

    @FXML
    private ChoiceBox<String> selectEstado;

    @FXML
    private Label lblOrdenar;

    private ObservableList<Mesa> listaMesas;

    @FXML
    public void initialize() {
        // Inicializar datos
        listaMesas = FXCollections.observableArrayList(
                new Mesa(1, "Libre", 4),
                new Mesa(2, "Ocupada", 2),
                new Mesa(3, "Reservada", 6)
        );

        // Configurar columnas
       columMesa.setCellValueFactory(cellData -> 
    new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject()
);

columEstado.setCellValueFactory(cellData -> 
    new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEstado())
);

columNumpersonas.setCellValueFactory(cellData -> 
    new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getCantidadPersonas()).asObject()
);

        // Cargar datos en tabla
        tableMesas.setItems(listaMesas);

        // Opciones del ChoiceBox
        selectEstado.setItems(FXCollections.observableArrayList(
                "Mesa",
                "Estado",
                "NumPersonas"
        ));

        // Evento botón
        btnOrdenar.setOnAction(e -> ordenarTabla());
    }

    private void ordenarTabla() {
        String criterio = selectEstado.getValue();

        if (criterio == null) return;

        switch (criterio) {
            case "Mesa":
                FXCollections.sort(listaMesas, (a, b) -> Integer.compare(a.getId(), b.getId()));
                break;
            case "Estado":
                FXCollections.sort(listaMesas, (a, b) -> a.getEstado().compareTo(b.getEstado()));
                break;
            case "NumPersonas":
                FXCollections.sort(listaMesas, (a, b) -> Integer.compare(a.getCantidadPersonas(), b.getCantidadPersonas()));
                break;
        }
    }
}
