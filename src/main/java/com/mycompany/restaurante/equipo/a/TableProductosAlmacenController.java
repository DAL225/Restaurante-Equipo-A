package com.mycompany.restaurante.equipo.a;

import Modelo.Dao.ProductoAlmacenDAO;
import Modelo.Impl.ProductoAlmacenDAOImpl;
import Modelo.ProductoAlmacen;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableProductosAlmacenController {
 
    // TableView
    @FXML
    private TableView<ProductoAlmacen> tblProductosAlmacen;

    // Columnas
    @FXML
    private TableColumn<ProductoAlmacen, Integer> colId;

    @FXML
    private TableColumn<ProductoAlmacen, String> colMarca;

    @FXML
    private TableColumn<ProductoAlmacen, String> colTipo;

    @FXML
    private TableColumn<ProductoAlmacen, Integer> colStock;

    @FXML
    private TableColumn<ProductoAlmacen, String> colProveedor;

    // Botón
    @FXML
    private Button btnCerrar;
    
    @FXML
    private Button btnRecargarInfo;
    
    private ProductoAlmacenDAO almacenDao;

    // Inicialización automática
    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        // Mapear columnas con atributos del modelo
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colProveedor.setCellValueFactory(new PropertyValueFactory<>("proveedor"));
        
        
        // Cargar datos 
        cargarDatos();
    }

    // Método cerrar ventana
    @FXML
    private void cerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void recargarInfo(ActionEvent event) {
        this.cargarDatos();
    }

    // Carga de datos a la tabla
    private void cargarDatos() {
        ObservableList<ProductoAlmacen> lista = FXCollections.observableArrayList();

        try {
            almacenDao = new ProductoAlmacenDAOImpl();

            lista = FXCollections.observableArrayList(almacenDao.obtenerProductosAlmacen());

            if (lista == null || lista.isEmpty()) {
                mostrarAlerta("Vacio", "No hay elementos para mostrar ", Alert.AlertType.INFORMATION);

                forzarCierre();
                return;
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
            forzarCierre();
        }

        tblProductosAlmacen.setItems(lista);

    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null); // Esto quita el encabezado gris extra
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    private void forzarCierre(){
        javafx.application.Platform.runLater(() -> {
                Stage stage = (Stage) tblProductosAlmacen.getScene().getWindow();

                if (stage != null) {
                    stage.close();
                }
            });
    }
}