package com.mycompany.restaurante.equipo.a;

import Modelo.Dao.ProductoAlmacenDAO;
import Modelo.Impl.ProductoAlmacenDAOImpl;
import Modelo.Impl.ProductoMenuDAOImpl;
import Modelo.ProductoAlmacen;
import Modelo.ProductoMenu;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GAlmacenController implements Initializable {
    
    // Panel de Agregar
    @FXML private AnchorPane pnlAgregarProducto;
    @FXML private TextField txtMarca;
    @FXML private ChoiceBox<String> selecTipo;
    @FXML private TextField txtProveedor;
    @FXML private Spinner<Integer> spnStock;
    
    // Botones laterales
    @FXML private Button btnAgregarProducto;
    @FXML private Button btnModificarProducto;
    @FXML private Button btnEliminarProducto;
    @FXML private Button btnVerProductos;
    @FXML private Button btnReportemensual;
    @FXML private Button btnAgregar;
    
    private ProductoAlmacenDAO almacenDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        ocultarSubpaneles();
    }
    
    private void ocultarSubpaneles(){
        pnlAgregarProducto.setVisible(false);
        // resto se Subpaneles eliminar, modificar, etc.
    }

    @FXML
    private void agregar(ActionEvent event) {
        // 1. Validar campos
        if (this.camposVaciosAgregar()) {
            mostrarAlerta("Campos Vacíos", "Por favor rellene todos los campos para continuar", Alert.AlertType.WARNING);
            return; // detiene el método aquí mismo
        }

        // Intenta agregar el producto
        try {
            almacenDao = new ProductoAlmacenDAOImpl();

            String marca = txtMarca.getText().trim();
            String tipo = selecTipo.getValue(); // El valor del ChoiceBox ya es String o el objeto seleccionado
            int stock = spnStock.getValueFactory().getValue();
            String proveedor = txtProveedor.getText().trim();

            if (!longitudCaracteresAgregarValida()){
                return;
            }

            ProductoAlmacen producto = new ProductoAlmacen(marca, tipo, stock, proveedor);

            if (almacenDao.agregarProductoAlmacen(producto)) {

                // Aquí lógica de guardado...
                mostrarAlerta("Éxito", "Producto agregado correctamente", Alert.AlertType.INFORMATION);
                
                limpiarCamposAgregar();
                
                return;
            }

            mostrarAlerta("Fracaso", "El producto no se pudo agregar", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error ", e.getMessage(), Alert.AlertType.ERROR);
            if (e.getMessage().equals("Error de acceso a la BD, intente mas tarde")){
                limpiarCamposAgregar();
                this.ocultarSubpaneles();
            }
        }
    }

    @FXML
    private void switchAgregarProducto(ActionEvent event) {
        // Configuración del Spinner (Mínimo 0, Máximo 1000, Inicial 0)
        SpinnerValueFactory<Integer> valueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0);
        spnStock.setValueFactory(valueFactory);

        // Configuración del ChoiceBox (Tipos de producto)
        selecTipo.getItems().addAll(
                "Aceite", "Leche", "Arroz", "Frijol", "Mariscos",
                "Carnes", "Pollo", "Pescado", "Verduras",
                "Frutas", "Enlatados", "Pastas", "Cereales",
                "Bebidas", "Lácteos", "Panadería", "Dulces",
                "Snacks", "Condimentos", "Harina", "Huevos",
                "Congelados", "Embutidos", "Limpieza", "Higiene Personal",
                "Mascotas", "Botanas", "Salsas", "Granos", "Otros"
        );
        pnlAgregarProducto.setVisible(true);
        // Podrías ocultar otros paneles aquí si los tuvieras
    }

    @FXML
    private void switchModificarProducto(ActionEvent event) {
        System.out.println("Cambiando a panel de Modificar...");
        pnlAgregarProducto.setVisible(false);
    }

    @FXML
    private void switchEliminarProducto(ActionEvent event) {
        System.out.println("Cambiando a panel de Eliminar...");
        pnlAgregarProducto.setVisible(false);
    }

    @FXML
    private void verProductos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/mycompany/restaurante/equipo/a/TableProductosAlmacen.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Almacen");
            stage.setScene(new Scene(root));
            stage.show();

            System.out.println("Mostrando lista de empleados...");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void generarReporte(ActionEvent event) {
        System.out.println("Generando reporte mensual PDF/Excel...");
    }
    
    private void limpiarCamposAgregar() {
        txtMarca.clear();
        txtProveedor.clear();
        selecTipo.getSelectionModel().clearSelection();
        spnStock.getValueFactory().setValue(0);
    }
    
    private boolean camposVaciosAgregar() {
        return txtMarca.getText().trim().isBlank() || 
                selecTipo.getValue() == null || 
                txtProveedor.getText().trim().isBlank();
    }
    
    private boolean longitudCaracteresAgregarValida(){
        if(txtMarca.getText().trim().length() > 50){
            mostrarAlerta("Error", "Limite de 50 caracteres excedido en Marca", Alert.AlertType.WARNING);
            return false;
        }
        
        if (txtProveedor.getText().trim().length() > 50) {
            mostrarAlerta("Error", "Limite de 50 caracteres excedido en Proveedor", Alert.AlertType.WARNING);
            return false;
        }
        
        return true;
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null); // Esto quita el encabezado gris extra
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}