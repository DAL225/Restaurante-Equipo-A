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
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class ChefController implements Initializable {

    @FXML
    private ListView<Pedido> listaPedidos;
    @FXML
    private Button botonFiltrar;
    @FXML
    private Button botonRecargar;
    @FXML
    private Button botonSalir;
    @FXML
    private Button botonPreparado;
    @FXML
    private TextField entradaProducto;
    @FXML
    private Spinner<Integer> entradaCantidad;
    @FXML
    private MenuButton entradaEstado;
    @FXML
    private Spinner<Integer> entradaNumMesa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory<Integer> valueFactoryCantidad = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1);
        entradaCantidad.setValueFactory(valueFactoryCantidad);
        SpinnerValueFactory<Integer> valueFactoryNumMesa = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1);
        entradaNumMesa.setValueFactory(valueFactoryNumMesa);
        try {
            PedidoDAOImpl DAO = new PedidoDAOImpl();
            ObservableList<Pedido> pedidos = FXCollections.observableArrayList(DAO.cargarPedidos());
            System.out.println(pedidos);
            listaPedidos.setItems(pedidos);
        } catch (Exception e) {
            Logger.getLogger(ChefController.class.getName()).log(Level.SEVERE, null, e);
        }
    }    
    
    @FXML
    private void Filtrar(ActionEvent event) {
    }

    @FXML
    private void recargar(ActionEvent event) {
        try {
            PedidoDAOImpl DAO = new PedidoDAOImpl();
            ObservableList<Pedido> pedidos = FXCollections.observableArrayList(DAO.cargarPedidos());
            listaPedidos.setItems(pedidos);
        } catch (Exception e) {
            Logger.getLogger(ChefController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void salir(ActionEvent event) {
    }

    @FXML
    private void marcarPreparado(ActionEvent event) {
        Pedido selectedItem = listaPedidos.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            try {
                PedidoDAOImpl DAO = new PedidoDAOImpl();
                DAO.pedidoPreparado(selectedItem.getIdPedido());
                ObservableList<Pedido> pedidos = FXCollections.observableArrayList(DAO.cargarPedidos());
                listaPedidos.setItems(pedidos);
            } catch (Exception e) {
                Logger.getLogger(ChefController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

}
