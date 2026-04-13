package com.mycompany.restaurante.equipo.a;

import Modelo.Impl.ProductoMenuDAOImpl;
import Modelo.ProductoMenu;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
    
    private ProductoMenuDAOImpl menuDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Ejemplo de cómo llenar el ChoiceBox al iniciar
        selecCategoria.getItems().addAll("Platillo", "Bebida", "Postre", "Entrada");
        menuDao = new ProductoMenuDAOImpl();
    }

    @FXML
    private void switchAgregarProducto(ActionEvent event) {
        pnlAgregarProducto.setVisible(true);
    }

    @FXML
    private void switchModificarProducto(ActionEvent event) {
        System.out.println("Cambiando a modo modificar...");
    }

    @FXML
    private void switchiEliminarProducto(ActionEvent event) {
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
        if (this.CamposVaciosAgregar()) {
            mostrarAlerta("Campos Vacíos", "Por favor rellene todos los campos para continuar", Alert.AlertType.WARNING);
            return; // <--- Súper importante: detiene el método aquí mismo
        }

        // Intenta agregar el producto
        try {

            String nombre = txtNombre.getText().trim();
            String categoria = selecCategoria.getValue(); // El valor del ChoiceBox ya es String o el objeto seleccionado
            String ingredientes = txtIngredientes.getText().trim();
            String imagenRuta = imageUrl;

            String precioStr = txtPrecio.getText().trim();
            // Validar que el precio sea un número
            double precio = Double.parseDouble(precioStr);

            //Limita los caracteres evitando reduccion del texto en la BD.
            if (ingredientes.length() > 500) {
                mostrarAlerta("Error", "Máximo 500 caracteres en ingredientes", Alert.AlertType.WARNING);
                return;
            }

            ProductoMenu producto = new ProductoMenu(nombre, categoria, imagenRuta, precio, ingredientes, true);

            if (menuDao.agregarProductoMenu(producto)) {
                //System.out.println("Agregando producto: " + nombre + " ($" + precio + ") Categoria: " + categoria);

                // Aquí tu lógica de guardado...
                mostrarAlerta("Éxito", "Producto agregado correctamente", Alert.AlertType.INFORMATION);
                return;
            }

            mostrarAlerta("Fracaso", "El producto no se pudo agregar", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            // Esto evita que el programa truene si ponen letras en el precio
            mostrarAlerta("Error de Formato", "El precio debe ser un número válido.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            // Esto evita que el programa truene si ponen letras en el precio
            mostrarAlerta("Error ", e.getMessage(), Alert.AlertType.ERROR);
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
                // 1. Crear la carpeta de destino si no existe
                File carpetaDestino = new File("img_productos");
                if (!carpetaDestino.exists()) {
                    carpetaDestino.mkdir();
                }

                // 2. Definir el archivo de destino (mismo nombre, pero en nuestra carpeta)
                // Tip: Puedes usar System.currentTimeMillis() + "_" + file.getName() para evitar duplicados
                File destino = new File(carpetaDestino, file.getName());

                // 3. Copiar físicamente el archivo
                Files.copy(file.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // 4. Guardar la ruta RELATIVA para la base de datos
                // Usamos forward slashes (/) para que sea compatible con todos los sistemas
                imageUrl = "img_productos/" + file.getName();

                System.out.println("Imagen copiada y lista para BD: " + imageUrl);

            } catch (IOException e) {
                System.err.println("Error al copiar la imagen: " + e.getMessage());
                // Aquí podrías mostrar una alerta al usuario
            }
        }

    }

    private boolean CamposVaciosAgregar() {
        return txtNombre.getText().trim().isBlank()
                || txtPrecio.getText().trim().isBlank()
                || txtIngredientes.getText().trim().isBlank()
                || imageUrl.isBlank() || imageUrl == null
                || selecCategoria.getValue().trim().isBlank();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null); // Esto quita el encabezado gris extra
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}
