package com.mycompany.restaurante.equipo.a;

import Modelo.Mesa;
import Modelo.Impl.MesasDAOImpl; // Importamos tu DAO
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class ListaMesasController {

    @FXML private Label lblListaMesas;
    @FXML private TableView<Mesa> tableMesas;
    @FXML private TableColumn<Mesa, Integer> columMesa;
    @FXML private TableColumn<Mesa, String> columEstado;
    @FXML private TableColumn<Mesa, Integer> columNumpersonas;
    @FXML private Button btnOrdenar;
    @FXML private ChoiceBox<String> selectEstado;
    @FXML private Label lblOrdenar;

    private ObservableList<Mesa> listaMesas;

    @FXML
    public void initialize() {
        // 1. Configurar columnas
        columMesa.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject()
        );
        columEstado.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEstado())
        );
        columNumpersonas.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getCantidadPersonas()).asObject()
        );

        // 2. Cargar datos usando el DAO
        cargarDatosEnTabla();

        // 3. Opciones del ChoiceBox y Eventos
        selectEstado.setItems(FXCollections.observableArrayList("Mesa", "Estado", "NumPersonas"));
        btnOrdenar.setOnAction(e -> ordenarTabla());
    }

    private void cargarDatosEnTabla() {
        try {
            // Instanciamos tu DAO
            MesasDAOImpl mesasDAO = new MesasDAOImpl();
            
            // Obtenemos el ArrayList de tu base de datos
            ArrayList<Mesa> mesasDesdeBD = mesasDAO.obtenerMesas();
            
            // Convertimos el ArrayList normal a un ObservableList para JavaFX
            listaMesas = FXCollections.observableArrayList(mesasDesdeBD);
            
            // Llenamos la tabla
            tableMesas.setItems(listaMesas);

        } catch (Exception e) {
            System.err.println("Error al cargar las mesas desde el DAO: " + e.getMessage());
            e.printStackTrace();
        }
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