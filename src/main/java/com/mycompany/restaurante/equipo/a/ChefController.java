package com.mycompany.restaurante.equipo.a;

import Modelo.Impl.PedidoDAOImpl;
import Modelo.Pedido;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class ChefController implements Initializable {

    @FXML
    private ListView<Pedido> listaOrdenes;
    @FXML
    private Button botonAplicarFiltro;
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
    private ChoiceBox entradaEstado;
    @FXML
    private Spinner<Integer> entradaMesa;
    @FXML
    private Spinner<Integer> entradaID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory<Integer> valueFactoryCantidad = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1);
        SpinnerValueFactory<Integer> valueFactoryNumMesa = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1);
        entradaMesa.setValueFactory(valueFactoryNumMesa);
        try {
            PedidoDAOImpl DAO = new PedidoDAOImpl();
            ObservableList<Pedido> pedidos = FXCollections.observableArrayList(DAO.cargarPedidos());
            System.out.println(pedidos);
            listaOrdenes.setItems(pedidos);
        } catch (Exception e) {
            Logger.getLogger(ChefController.class.getName()).log(Level.SEVERE, null, e);
        }
    }    
    
    @FXML
    private void aplicarFiltros(ActionEvent event) {
    }

    @FXML
    private void actualizarLista(ActionEvent event) {
        try {
            PedidoDAOImpl DAO = new PedidoDAOImpl();
            ObservableList<Pedido> pedidos = FXCollections.observableArrayList(DAO.cargarPedidos());
            listaOrdenes.setItems(pedidos);
        } catch (Exception e) {
            Logger.getLogger(ChefController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void salir(ActionEvent event) {
    }

    @FXML
    private void marcarComoTerminada(ActionEvent event) {
        Pedido selectedItem = listaOrdenes.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            try {
                PedidoDAOImpl DAO = new PedidoDAOImpl();
                DAO.pedidoPreparado(selectedItem.getIdPedido());
                ObservableList<Pedido> pedidos = FXCollections.observableArrayList(DAO.cargarPedidos());
                listaOrdenes.setItems(pedidos);
            } catch (Exception e) {
                Logger.getLogger(ChefController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

}
