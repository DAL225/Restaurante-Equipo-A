package com.mycompany.restaurante.equipo.a;

import Modelo.Dao.ProductoMenuDAO;
import Modelo.Impl.ProductoMenuDAOImpl;
import Modelo.ProductoMenu;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GMenuController implements Initializable {

    @FXML
    private AnchorPane pnlMenu;
    @FXML
    private Label lblMenu;
    @FXML
    private AnchorPane pnlAgregarProducto;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextArea txtIngredientes;
    @FXML
    private ChoiceBox<String> selecCategoria;
    @FXML
    private Button btnSeleccionar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnAgregarProducto;
    @FXML
    private Button btnModificarProducto;
    @FXML
    private Button btnEliminarProducto;
    @FXML
    private Button btnVerProductos;
    @FXML
    private Button btnReporteDiario;

    private String imageUrl;

    private ProductoMenuDAO menuDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ocultarSubpaneles();
        // Ejemplo de cómo llenar el ChoiceBox al iniciar
        selecCategoria.getItems().addAll("Platillo", "Bebida", "Postre", "Entrada");
    }

    private void ocultarSubpaneles(){
        pnlAgregarProducto.setVisible(false);
        // resto se Subpaneles eliminar, modificar, etc.
    }
    
    @FXML
    private void switchAgregarProducto(ActionEvent event) {
        pnlAgregarProducto.setVisible(true);
        // resto de paneles false
    }

    @FXML
    private void switchModificarProducto(ActionEvent event) {
        System.out.println("Cambiando a modo modificar...");
    }

    @FXML
    private void switchEliminarProducto(ActionEvent event) {
        System.out.println("Cambiando a modo modificar...");
    }

    @FXML
    private void verProductos(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/mycompany/restaurante/equipo/a/TableProductosMenu.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Productos del Menú");
            stage.setScene(new Scene(root));
            stage.show();

            System.out.println("Mostrando lista de productos...");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void generarReporte(ActionEvent event) {
        System.out.println("Generando reporte diario...");
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
            menuDao = new ProductoMenuDAOImpl();
            
            // Si no se selecciona imagen, se asigna una por defecto
            if(imageUrl == null || imageUrl.isBlank() ){
                imageUrl = "img_productos/porDefecto.jpg";
            }

            String nombre = txtNombre.getText().trim();
            String categoria = selecCategoria.getValue(); // El valor del ChoiceBox ya es String o el objeto seleccionado
            String ingredientes = txtIngredientes.getText().trim();
            String imagenRuta = imageUrl;

            String precioStr = txtPrecio.getText().trim();
            // Validar que el precio sea un número
            double precio = Double.parseDouble(precioStr);

            if (!longitudCaracteresAgregarValida()){
                return;
            }

            ProductoMenu producto = new ProductoMenu(nombre, categoria, imagenRuta, precio, ingredientes, true);

            if (menuDao.agregarProductoMenu(producto)) {
                //System.out.println("Agregando producto: " + nombre + " ($" + precio + ") Categoria: " + categoria);

                // Aquí tu lógica de guardado...
                mostrarAlerta("Éxito", "Producto agregado correctamente", Alert.AlertType.INFORMATION);
                
                limpiarCamposAgregar();
                
                return;
            }

            mostrarAlerta("Fracaso", "El producto no se pudo agregar", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            // Esto evita que el programa truene si ponen letras en el precio
            mostrarAlerta("Error de Formato", "El precio debe ser un número válido.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error ", e.getMessage(), Alert.AlertType.ERROR);
            if (e.getMessage().equals("Error de acceso a la BD, intente mas tarde")){
                limpiarCamposAgregar();
                this.pnlAgregarProducto.setVisible(false);
            }
        }
    }

    @FXML
    private void agregarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"
        );
        fileChooser.getExtensionFilters().add(extFilter);

        Stage stage = (Stage) this.btnAgregar.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                // 1. Asegurar que la carpeta existe
                File carpetaDestino = new File("img_productos");
                if (!carpetaDestino.exists()) {
                    carpetaDestino.mkdir();
                }

                // 2. Definir el destino con el nombre ORIGINAL
                File destino = new File(carpetaDestino, file.getName());

                // 3. VERIFICACIÓN: ¿Ya existe un archivo con este nombre?
                if (destino.exists()) {
                    this.mostrarAlerta("Nombre de imagen existente", "Usando el existente...", Alert.AlertType.INFORMATION);
                    // No copiamos, solo asignamos la ruta que ya conocemos
                    imageUrl = "img_productos/" + file.getName();
                } else {
                    // Si no existe, procedemos a copiarlo físicamente/ validacion por si existe
                    Files.copy(file.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    imageUrl = "img_productos/" + file.getName();
                    System.out.println("Archivo nuevo copiado: " + imageUrl);
                }

            } catch (IOException e) {
                System.err.println("Error al procesar la imagen: " + e.getMessage());

                this.mostrarAlerta("Error de Archivo", "No se pudo guardar la imagen", Alert.AlertType.ERROR);
            }
        }
    }

    private boolean camposVaciosAgregar() {
        return txtNombre.getText().trim().isBlank()
                || txtPrecio.getText().trim().isBlank()
                || txtIngredientes.getText().trim().isBlank()
                || selecCategoria.getValue() == null || selecCategoria.getValue().trim().isBlank();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null); // Esto quita el encabezado gris extra
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    private void limpiarCamposAgregar() {
        this.imageUrl = "";
        txtNombre.clear();
        selecCategoria.getSelectionModel().clearSelection();
        txtPrecio.clear();
        txtIngredientes.clear();
    }
    
    /*
        Limita los caracteres evitando cortes del texto en la BD.
    */
    private boolean longitudCaracteresAgregarValida(){
        if(txtNombre.getText().trim().length() > 100){
            mostrarAlerta("Error", "Limite de 100 caracteres excedido en nombre", Alert.AlertType.WARNING);
            return false;
        }
        
        if (txtIngredientes.getText().trim().length() > 500) {
            mostrarAlerta("Error", "Limite de 500 caracteres excedido en ingredientes", Alert.AlertType.WARNING);
            return false;
        }

        if (txtPrecio.getText().trim().length() > 10) {
            mostrarAlerta("Error", "Limite de 10 caracteres(incluyendo decimales) excedido en precio", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }
    
    

}
