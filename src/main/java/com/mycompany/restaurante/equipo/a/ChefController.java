package com.mycompany.restaurante.equipo.a;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class ChefController implements Initializable {


    @FXML
    private ListView<?> listaOrdenes;
    @FXML
    private Button botonActualizar;
    @FXML
    private Button botonSalir;
    @FXML
    private Button botonPreparada;
    @FXML
    private TextField entradaProducto;
    @FXML
    private TextField entradaCantidad;
    @FXML
    private Spinner<?> entradaMesa;
    @FXML
    private Spinner<?> entradaID;
    @FXML
    private ChoiceBox<?> entradaEstado;
    @FXML
    private Button botonAplicarFiltro;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void actualizarLista(ActionEvent event) {
    }

    @FXML
    private void salir(ActionEvent event) {
    }

    @FXML
    private void marcarComoTerminada(ActionEvent event) {
    }

    @FXML
    private void aplicarFiltros(ActionEvent event) {
    }

}