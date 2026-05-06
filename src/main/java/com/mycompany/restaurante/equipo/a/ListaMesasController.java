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
    @FXML
    private void ordenarTabla() {
        // 1. Obtenemos qué eligió el usuario en el ChoiceBox
        String criterio = selectEstado.getValue();

        // 2. Si no eligió nada, no hacemos nada (o mostramos una alerta)
        if (criterio == null) {
            System.out.println("Por favor selecciona un criterio para ordenar.");
            return; 
        }

        // 3. Aplicamos la regla de ordenamiento según el texto seleccionado
        switch (criterio) {
            case "Mesa":
                // Compara números enteros (ID 1, ID 2, ID 3...)
                FXCollections.sort(listaMesas, (a, b) -> Integer.compare(a.getId(), b.getId()));
                break;
                
            case "Estado":
                // Compara texto alfabéticamente ("Libre" va antes que "Ocupada")
                FXCollections.sort(listaMesas, (a, b) -> a.getEstado().compareTo(b.getEstado()));
                break;
                
            case "NumPersonas":
                // Compara números enteros (capacidad de menor a mayor)
                FXCollections.sort(listaMesas, (a, b) -> Integer.compare(a.getCantidadPersonas(), b.getCantidadPersonas()));
                break;
        }
    }
    
}