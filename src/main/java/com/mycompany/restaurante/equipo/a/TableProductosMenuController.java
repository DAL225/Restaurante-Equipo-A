package com.mycompany.restaurante.equipo.a;

import Modelo.Impl.ProductoMenuDAOImpl;
import Modelo.ProductoMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TableProductosMenuController {

    // TableView
    @FXML
    private TableView<ProductoMenu> tblProductosMenu;

    // Columnas
    @FXML
    private TableColumn<ProductoMenu, Integer> colId;

    @FXML
    private TableColumn<ProductoMenu, String> colNombre;

    @FXML
    private TableColumn<ProductoMenu, String> colCategoria;

    @FXML
    private TableColumn<ProductoMenu, Double> colPrecio;

    @FXML
    private TableColumn<ProductoMenu, String> colIngredientes;

    @FXML
    private TableColumn<ProductoMenu, String> colImagen;

    @FXML
    private TableColumn<ProductoMenu, Boolean> colDisponibilidad;

    // Botón
    @FXML
    private Button btnCerrar;
    
    private ProductoMenuDAOImpl menuDao;

    // Inicialización automática
    @FXML
    public void initialize() {

        // Mapear columnas con atributos del modelo
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colIngredientes.setCellValueFactory(new PropertyValueFactory<>("ingredientes"));
        colImagen.setCellValueFactory(new PropertyValueFactory<>("imagenRuta"));
        colDisponibilidad.setCellValueFactory(new PropertyValueFactory<>("disponibilidad"));

        // Columna de imagen 
        colImagen.setCellFactory(column -> new TableCell<ProductoMenu, String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    try {
                        Image image = new Image("file:" + item, 60, 60, true, true);
                        imageView.setImage(image);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        setGraphic(null);
                    }
                }
            }
        });

        
        // Cargar datos 
        cargarDatos();
        
        
    }

    // Método cerrar ventana
    @FXML
    private void cerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    // Carga de datos a la tabla
    private void cargarDatos() {
        ObservableList<ProductoMenu> lista = FXCollections.observableArrayList();
        
        try{
            menuDao = new ProductoMenuDAOImpl();
            
            lista = FXCollections.observableArrayList(menuDao.obtenerProductosMenu());
            
            if (lista.isEmpty() || lista == null){
                mostrarAlerta("Vacio", "No hay elementos para mostrar ", Alert.AlertType.INFORMATION);
                //cerrar();
                return;
            }
        }catch(Exception e) {
            mostrarAlerta("Error ", "Error al cargar los datos", Alert.AlertType.ERROR);
            
            //cerrar();
        }
        
        tblProductosMenu.setItems(lista);
        
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null); // Esto quita el encabezado gris extra
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}