package com.mycompany.restaurante.equipo.a;

import Modelo.Dao.ProductoAlmacenDAO;
import Modelo.Impl.ProductoAlmacenDAOImpl;
import Modelo.ProductoAlmacen;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GAlmacenController implements Initializable {
    
    // Contenedor principal
    @FXML private AnchorPane pnlAlmacen;
    @FXML private StackPane stckPane;
    
    // Paneles principales (dentro del StackPane)
    @FXML private AnchorPane pnlAgregarProducto;
    @FXML private AnchorPane pnlModificarProducto;
    
    // Subpaneles dentro de Modificar Producto
    @FXML private AnchorPane subpnlCamposModificar;
    @FXML private AnchorPane subpnlCamposRetirar;
    
    // Componentes del Panel: Agregar Producto
    @FXML private TextField txtMarca;
    @FXML private ChoiceBox<String> selecTipo;
    @FXML private Spinner<Integer> spnStock;
    @FXML private TextField txtProveedor;
    @FXML private Button btnAgregar;

    
    // Componentes del Panel: Modificar Producto (Sección Izquierda - Modificar Datos)
    @FXML private Spinner<Integer> spnIdModDatos;
    @FXML private Button btnBuscarModDatos;
    @FXML private TextField txtMarcaModDatos;
    @FXML private ChoiceBox<String> selecTipoModDatos;
    @FXML private Spinner<Integer> spnStockModDatos;
    @FXML private TextField txtProveedorModDatos;
    @FXML private Button btnModificarModDatos;
 
    
    // Componentes del Panel: Modificar Producto (Sección Derecha - Retirar Stock)
    
    @FXML private Spinner<Integer> spnIdRetStock;
    @FXML private Button btnBuscarRetStock;
    @FXML private TextField txtMarcaRetStock;
    @FXML private Spinner<Integer> spnStockRetStock;
    @FXML private Button btnRetirarRetStock;
    
    // Botones del menú lateral izquierdo
    @FXML private Button btnAgregarProducto;
    @FXML private Button btnModificarProducto;
    @FXML private Button btnEliminarProducto;
    @FXML private Button btnVerProductos;
    @FXML private Button btnReportemensual;
    
    private ProductoAlmacenDAO almacenDao;
    private int idModificarDatos;
    private int idRetirarStock;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        stckPane.setVisible(true);
        ocultarSubpaneles();
    }
    
    private void ocultarSubpaneles(){
        pnlAgregarProducto.setVisible(false);
        pnlModificarProducto.setVisible(false);
        // Ocultar también las secciones internas de modificar hasta que se busque un ID válido si lo requieres
    }
    
   

    @FXML
    private void agregar(ActionEvent event) {
        if (this.camposVaciosAgregar()) {
            mostrarAlerta("Campos Vacíos", "Por favor rellene todos los campos para continuar", Alert.AlertType.WARNING);
            return;
        }

        try {
            almacenDao = new ProductoAlmacenDAOImpl();

            String marca = txtMarca.getText().trim();
            String tipo = selecTipo.getValue(); 
            int stock = spnStock.getValueFactory().getValue();
            String proveedor = txtProveedor.getText().trim();

            if (!longitudCaracteresAgregarValida()){
                return;
            }

            ProductoAlmacen producto = new ProductoAlmacen(marca, tipo, stock, proveedor);

            if (almacenDao.agregarProductoAlmacen(producto)) {
                mostrarAlerta("Éxito", "Producto agregado correctamente", Alert.AlertType.INFORMATION);
                limpiarCamposAgregar();
                return;
            }

            mostrarAlerta("Fracaso", "El producto no se pudo agregar", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error ", e.getMessage(), Alert.AlertType.ERROR);
            if (e.getMessage() != null && e.getMessage().equals("Error de acceso a la BD, intente mas tarde")){
                limpiarCamposAgregar();
                this.ocultarSubpaneles();
            }
        }
    }

    @FXML
    private void switchAgregarProducto(ActionEvent event) {
        // Configuración del Spinner de Agregar (Mínimo 0, Máximo 1000, Inicial 0)
        SpinnerValueFactory<Integer> valueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0);
        spnStock.setValueFactory(valueFactory);

        // Configuración del ChoiceBox si está vacío
        if (selecTipo.getItems().isEmpty()) {
            selecTipo.getItems().addAll(
                "Aceite", "Leche", "Arroz", "Frijol", "Mariscos",
                "Carnes", "Pollo", "Pescado", "Verduras",
                "Frutas", "Enlatados", "Pastas", "Cereales",
                "Bebidas", "Lácteos", "Panadería", "Dulces",
                "Snacks", "Condimentos", "Harina", "Huevos",
                "Congelados", "Embutidos", "Limpieza", "Higiene Personal",
                "Mascotas", "Botanas", "Salsas", "Granos", "Otros"
            );
        }
        
        pnlAgregarProducto.setVisible(true);
        pnlModificarProducto.setVisible(false);
    }

    @FXML
    private void switchModificarProducto(ActionEvent event) {
        //Limpia los capos de los subpaneles por si tienen contenido y ademas los oculta
        //El ocultarlos esta dentro del propio limpiar
        limpiarCamposModificar();
        limpiarCamposRetiroStock();
        spnIdModDatos.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99999, 1));
        spnIdRetStock.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99999, 1));
        
        // Rangos para los stocks de la sección de modificaciones
        spnStockModDatos.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
        spnStockRetStock.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1));
        
        // Llenar también las categorías en el ChoiceBox de modificar
        selecTipoModDatos.getItems().addAll(
            "Aceite", "Leche", "Arroz", "Frijol", "Mariscos",
            "Carnes", "Pollo", "Pescado", "Verduras",
            "Frutas", "Enlatados", "Pastas", "Cereales",
            "Bebidas", "Lácteos", "Panadería", "Dulces",
            "Snacks", "Condimentos", "Harina", "Huevos",
            "Congelados", "Embutidos", "Limpieza", "Higiene Personal",
            "Mascotas", "Botanas", "Salsas", "Granos", "Otros"
        );
        System.out.println("Cambiando a panel de Modificar...");
        pnlModificarProducto.setVisible(true);
        pnlAgregarProducto.setVisible(false);
    }

    @FXML
    private void switchEliminarProducto(ActionEvent event) {
        System.out.println("Cambiando a panel de Eliminar...");
        pnlAgregarProducto.setVisible(false);
        pnlModificarProducto.setVisible(false);
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

            System.out.println("Mostrando lista de productos...");

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista de la tabla", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void generarReporte(ActionEvent event) {
        System.out.println("Generando reporte mensual PDF/Excel...");
    }
    
    @FXML
    private void buscarModDatos(ActionEvent event) {
        int idBuscar = spnIdModDatos.getValue();
        System.out.println("Buscando producto para modificar con ID: " + idBuscar);

        ProductoAlmacen producto = null;

        try {
            almacenDao = new ProductoAlmacenDAOImpl();

            producto = almacenDao.obtenerProductoAlmacen(idBuscar);

            if (producto == null) {
                mostrarAlerta("Null", "Elemento no encontrado", Alert.AlertType.INFORMATION);
                limpiarCamposModificar();
                return;
            }
            //Asignacion de valores a id global, para posterior modificacion
            //Asignacion de valores a los campos para que el usuario los visualice
            //y actualice lo requerido
            idModificarDatos = producto.getId();
            txtMarcaModDatos.setText(producto.getMarca());
            selecTipoModDatos.setValue(producto.getTipo());
            spnStockModDatos.getValueFactory().setValue(producto.getStock());
            txtProveedorModDatos.setText(producto.getProveedor());
            
            //Muestra los campos anteriores de modificacion que solo estaran visibles
            //cuando se encuentre el producto a modificar
            subpnlCamposModificar.setVisible(true);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
        }
        
    }

    @FXML
    private void modificar(ActionEvent event) {
        System.out.println("Ejecutando acción de modificar datos del producto...");
        // Tu lógica para recuperar los datos de los txt/spn del subpnlCamposModificar y enviarlos al DAO
        if (this.camposVaciosModificar()) {
            mostrarAlerta("Campos Vacíos", "Por favor rellene todos los campos para continuar", Alert.AlertType.WARNING);
            return;
        }

        try {
            almacenDao = new ProductoAlmacenDAOImpl();

            int id = idModificarDatos;
            String marca = txtMarcaModDatos.getText().trim();
            String tipo = selecTipoModDatos.getValue(); 
            int stock = spnStockModDatos.getValueFactory().getValue();
            String proveedor = txtProveedorModDatos.getText().trim();

            if (!longitudCaracteresModificarValida()){
                return;
            }
            
            //Confirmacion
            boolean confirmar = mostrarConfirmacion(
                    "Confirmación",
                    "¿Está seguro de MODIFICAR LOS DATOS?"
            );

            // Si la opcion selecionada es "no", sale del metodo
            if (!confirmar) {
                return;
            }

            ProductoAlmacen producto = new ProductoAlmacen(id, marca, tipo, stock, proveedor);

            if (almacenDao.modificarProductoAlmacen(producto)) {
                mostrarAlerta("Éxito", "Producto modificado correctamente", Alert.AlertType.INFORMATION);
                limpiarCamposModificar();
                return;
            }

            mostrarAlerta("Fracaso", "El producto no se pudo agregar", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error ", e.getMessage(), Alert.AlertType.ERROR);
            if (e.getMessage() != null && e.getMessage().equals("Error de acceso a la BD, intente mas tarde")){
                limpiarCamposModificar();
                this.ocultarSubpaneles();
            }
        }
        
    }

    @FXML
    private void buscarRetStock(ActionEvent event) {
        int idBuscar = spnIdRetStock.getValue();
        System.out.println("Buscando producto para retirar stock con ID: " + idBuscar);
        
        ProductoAlmacen producto = null;

        try {
            almacenDao = new ProductoAlmacenDAOImpl();

            producto = almacenDao.obtenerProductoAlmacen(idBuscar);

            if (producto == null) {
                mostrarAlerta("Null", "Elemento no encontrado", Alert.AlertType.INFORMATION);
                limpiarCamposRetiroStock();
                return;
            }
            //Asignacion de valores a id global, para posterior disminucion de stock
            //Asignacion de valores al campo marca para que el usuario lo visualice
            //y actualice lo requerido
            idRetirarStock = producto.getId();
            txtMarcaRetStock.setText(producto.getMarca());
            
            //Muestra los campos anteriores de retiro que solo estaran visibles
            //cuando se encuentre el producto a retirar stock
            subpnlCamposRetirar.setVisible(true);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void retirarStock(ActionEvent event) {
        System.out.println("Ejecutando acción de retirar cantidad del stock...");
        
        try {
            almacenDao = new ProductoAlmacenDAOImpl();

            int id = idRetirarStock;
            int cantidad = spnStockRetStock.getValue();

            if (cantidad == 0){
                mostrarAlerta("Invalido", "Seleccione una cantidad mayor a --0--", Alert.AlertType.INFORMATION);
                return;
            }
            
            //Confirmacion
            boolean confirmar = mostrarConfirmacion(
                    "Confirmación",
                    "¿Está seguro de REALIZAR EL RETIRO?"
            );

            // Si la opcion selecionada es "no", sale del metodo
            if (!confirmar) {
                return;
            }

            if (almacenDao.retirarStock(id, cantidad)) {
                mostrarAlerta("Éxito", "Cantidad retirada correctamente", Alert.AlertType.INFORMATION);
                limpiarCamposRetiroStock();
                return;
            }

            mostrarAlerta("Fracaso", "El producto no se pudo agregar", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error ", e.getMessage(), Alert.AlertType.ERROR);
            if (e.getMessage() != null && e.getMessage().equals("Error de acceso a la BD, intente mas tarde")){
                limpiarCamposModificar();
                this.ocultarSubpaneles();
            }
        }
    }

    private void limpiarCamposAgregar() {
        txtMarca.clear();
        txtProveedor.clear();
        selecTipo.getSelectionModel().clearSelection();
        if (spnStock.getValueFactory() != null) {
            spnStock.getValueFactory().setValue(0);
        }
    }
    
    private boolean camposVaciosAgregar() {
        return txtMarca.getText().trim().isBlank() || 
                selecTipo.getValue() == null || 
                txtProveedor.getText().trim().isBlank();
    }
    
    private boolean longitudCaracteresAgregarValida(){
        if(txtMarca.getText().trim().length() > 50){
            mostrarAlerta("Error", "Límite de 50 caracteres excedido en Marca", Alert.AlertType.WARNING);
            return false;
        }
        
        if (txtProveedor.getText().trim().length() > 50) {
            mostrarAlerta("Error", "Límite de 50 caracteres excedido en Proveedor", Alert.AlertType.WARNING);
            return false;
        }
        
        return true;
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null); 
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    private void limpiarCamposModificar() {
        if (spnIdModDatos.getValueFactory() != null) {
            spnIdModDatos.getValueFactory().setValue(1);
        }
        txtMarcaModDatos.clear();
        txtProveedorModDatos.clear();
        selecTipoModDatos.getSelectionModel().clearSelection();
        if (spnStockModDatos.getValueFactory() != null) {
            spnStockModDatos.getValueFactory().setValue(0);
        }
        //Oculta la seccion de los campos de modificar
        subpnlCamposModificar.setVisible(false);
    }
    
    private boolean camposVaciosModificar() {
        return txtMarcaModDatos.getText().trim().isBlank() || 
                selecTipoModDatos.getValue() == null || 
                txtProveedorModDatos.getText().trim().isBlank();
    }
    
    private boolean longitudCaracteresModificarValida(){
        if(txtMarcaModDatos.getText().trim().length() > 50){
            mostrarAlerta("Error", "Límite de 50 caracteres excedido en Marca", Alert.AlertType.WARNING);
            return false;
        }
        
        if (txtProveedorModDatos.getText().trim().length() > 50) {
            mostrarAlerta("Error", "Límite de 50 caracteres excedido en Proveedor", Alert.AlertType.WARNING);
            return false;
        }
        
        return true;
    }
    
    private void limpiarCamposRetiroStock() {
        if (spnIdRetStock.getValueFactory() != null) {
            spnIdRetStock.getValueFactory().setValue(1);
        }
        this.txtMarcaRetStock.clear();
        if (spnStockRetStock.getValueFactory() != null) {
            spnStockRetStock.getValueFactory().setValue(1);
        }
        //Oculta la seccion de los campos de Retirar Stock
        subpnlCamposRetirar.setVisible(false);
    }
    
    /**
     * Muestra una ventana de confirmación con opciones (OK / Cancelar).
     *
     * @param titulo título de la ventana
     * @param mensaje mensaje de confirmación
     * @return true si el usuario presiona OK, false en caso contrario
     */
    private boolean mostrarConfirmacion(String titulo, String mensaje) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);

        Optional<ButtonType> r = a.showAndWait();
        return r.isPresent() && r.get() == ButtonType.OK;
    }
}