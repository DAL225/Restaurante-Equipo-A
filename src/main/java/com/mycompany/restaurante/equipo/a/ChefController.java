package com.mycompany.restaurante.equipo.a;

import Modelo.Impl.PedidoDAOImpl;
import Modelo.Pedido;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class ChefController implements Initializable {

    @FXML
    private TableView<Pedido> listaOrdenes;
    @FXML
    private TableColumn<Pedido, Integer> columnaIdPedido;
    //Columna del nombre del platillo
    @FXML
    private TableColumn<Pedido, String> columnaProducto;
    //Columna de cantidad
    @FXML
    private TableColumn<Pedido, Integer> columnaCantidad;
    //Columna subtotal
    @FXML
    private TableColumn<Pedido, Double> columnaSubtotal;
    //Columna de Estado
    @FXML
    private TableColumn<Pedido, Boolean> columnaEstado;
    @FXML
    private Button botonAplicarFiltro;
    @FXML
    private Button botonActualizar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button botonSalir;
    @FXML
    private Button botonPreparada;
    @FXML
    private TextField entradaProducto;
    @FXML
    private TextField entradaCantidad;
    @FXML
    private ChoiceBox<String> entradaEstado;
    @FXML
    private Spinner<Integer> entradaMesa;
    @FXML
    private Spinner<Integer> entradaID;

    private ObservableList<Pedido> pedidosOriginales = FXCollections.observableArrayList();
    
    @Override
public void initialize(URL url, ResourceBundle rb) {
    columnaIdPedido.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
    columnaProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
    columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
    columnaSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
    columnaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    
    SpinnerValueFactory<Integer> valueFactoryNumMesa =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);

    SpinnerValueFactory<Integer> valueFactoryID =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);

    entradaMesa.setValueFactory(valueFactoryNumMesa);
    entradaID.setValueFactory(valueFactoryID);

    entradaEstado.getItems().addAll("Todos", "Pendiente", "Preparado", "No preparado");
    entradaEstado.setValue("Todos");
    
    this.cargarPedidos();

    actualizarLista(null);
}
    
    @FXML
private void aplicarFiltros(ActionEvent event) {
    String producto = entradaProducto.getText().trim().toLowerCase();
    String cantidadTexto = entradaCantidad.getText().trim();
    int mesa = entradaMesa.getValue();
    int idPedido = entradaID.getValue();
    String estadoSeleccionado = entradaEstado.getValue();

    ObservableList<Pedido> pedidosFiltrados = FXCollections.observableArrayList();

    for (Pedido pedido : pedidosOriginales) {
        boolean coincide = true;

        if (!producto.isEmpty()) {
            if (pedido.getProducto() == null ||
                    !pedido.getProducto().toLowerCase().contains(producto)) {
                coincide = false;
            }
        }

        if (!cantidadTexto.isEmpty()) {
            try {
                int cantidad = Integer.parseInt(cantidadTexto);

                if (pedido.getCantidad() != cantidad) {
                    coincide = false;
                }

            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "La cantidad debe ser un número", Alert.AlertType.WARNING);
                return;
            }
        }

        if (mesa != 0) {
            if (pedido.getPMesa() != mesa) {
                coincide = false;
            }
        }

        if (idPedido != 0) {
            if (pedido.getIdPedido() != idPedido) {
                coincide = false;
            }
        }

        if (estadoSeleccionado != null && !estadoSeleccionado.equals("Todos")) {
            switch (estadoSeleccionado) {
                case "Pendiente":
                    if (pedido.getPreparado()) {
                        coincide = false;
                    }
                    break;

                case "Preparado":
                    if (!pedido.getPreparado()) {
                        coincide = false;
                    }
                    break;

                case "No preparado":
                    if (pedido.getPreparado()) {
                        coincide = false;
                    }
                    break;
            }
        }

        if (coincide) {
            pedidosFiltrados.add(pedido);
        }
    }

    listaOrdenes.setItems(pedidosFiltrados);

    if (pedidosFiltrados.isEmpty()) {
        mostrarAlerta("Sin resultados", "No se encontraron pedidos con esos filtros", Alert.AlertType.INFORMATION);
    }
}

@FXML
private void actualizarLista(ActionEvent event) {
    try {
        PedidoDAOImpl DAO = new PedidoDAOImpl();

        pedidosOriginales.setAll(DAO.cargarPedidosNoPreparado());
        listaOrdenes.setItems(pedidosOriginales);

    } catch (Exception e) {
        Logger.getLogger(ChefController.class.getName()).log(Level.SEVERE, null, e);
        mostrarAlerta("Error", "No se pudieron cargar los pedidos", Alert.AlertType.ERROR);
    }
}

    @FXML
    private void salir(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource( "/com/mycompany/restaurante/equipo/a/InicioSesion.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) botonSalir.getScene().getWindow();
            stage.setScene(new Scene (root));

        } catch (IOException e) {
        }
    }

    @FXML
    private void marcarComoTerminada(ActionEvent event) {
        Pedido selectedItem = listaOrdenes.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            try {
                PedidoDAOImpl DAO = new PedidoDAOImpl();
                DAO.pedidoPreparado(selectedItem.getIdPedido());
                ObservableList<Pedido> pedidos = FXCollections.observableArrayList(DAO.cargarPedidosNoPreparado());
                listaOrdenes.setItems(pedidos);
            } catch (Exception e) {
                Logger.getLogger(ChefController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

@FXML
private void limpiarFiltros(ActionEvent event) {
    entradaProducto.clear();
    entradaCantidad.clear();

    entradaMesa.getValueFactory().setValue(0);
    entradaID.getValueFactory().setValue(0);

    entradaEstado.setValue("Todos");

    listaOrdenes.setItems(pedidosOriginales);
}    
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null); // Esto quita el encabezado gris extra
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    /**
     * Metodo para recuperar y mostrar todos los pedidos existentes en el sistema
     */
    private void cargarPedidos() {
        try {
            //Se obtienen los pedidos desde la BD
            PedidoDAOImpl DAO = new PedidoDAOImpl();
            List<Pedido> pedidos = DAO.cargarPedidosNoPreparado();

            //Se colocan los pedidos en la tabla
            listaOrdenes.setItems(FXCollections.observableArrayList(pedidos));
        //Si ocurre un error se muestra un mensaje en pantalla
        } catch (Exception e) {
            mostrarAlerta("Error", "Carga de datos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
