package com.mycompany.restaurante.equipo.a;

import Modelo.Dao.PedidoDAO;
import Modelo.Dao.ProductoMenuDAO;
import Modelo.Impl.PedidoDAOImpl;
import Modelo.Impl.ProductoMenuDAOImpl;
import Modelo.Pedido;
import Modelo.ProductoMenu;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class GMenuController implements Initializable {

    @FXML private AnchorPane pnlMenu;
    @FXML private StackPane stckPane; // Contenedor StackPane mapeado
    
    // Botones del menú lateral izquierdo
    @FXML private Button btnAgregarProducto;
    @FXML private Button btnModificarProducto;
    @FXML private Button btnEliminarProducto;
    @FXML private Button btnVerProductos;
    @FXML private Button btnReporteDiario;
    @FXML private Button btnReporteMensual;

    // PANEL: Agregar Producto
    @FXML private AnchorPane pnlAgregarProducto;
    @FXML private TextField txtNombre;
    @FXML private ChoiceBox<String> selecCategoria;
    @FXML private Button btnSeleccionar;
    @FXML private TextField txtPrecio;
    @FXML private TextArea txtIngredientes;
    @FXML private Button btnAgregar;

    // PANEL: Modificar Producto
    @FXML private AnchorPane pnlModificarProducto;
    @FXML private Spinner<Integer> spnIdModDatos;
    @FXML private Button btnBuscarModDatos;
    @FXML private AnchorPane subpnlCamposModificar;
    
    // Campos de edición internos del panel Modificar
    @FXML private TextField txtNombreModDatos;
    @FXML private ChoiceBox<String> selecCategoriaModDatos;
    @FXML private Button btnSeleccionarModDatos;
    @FXML private TextField txtPrecioModDatos;
    @FXML private TextArea txtIngredientesModDatos;
    @FXML private ToggleButton btnDisponibilidadModDatos;
    @FXML private Button btnModificarModDatos;

    private String imageUrl;
    private int idModificarDatos;
    private ProductoMenuDAO menuDao;
    private PedidoDAOImpl dbPedidos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        stckPane.setVisible(true);
        ocultarSubpaneles();
        try {
            dbPedidos = new PedidoDAOImpl();
        } catch (Exception ex) {
            System.getLogger(GMenuController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private void ocultarSubpaneles(){
        pnlAgregarProducto.setVisible(false);
        pnlModificarProducto.setVisible(false);
    }
    
    // ==========================================
    //   ACCIONES DEL MENÚ LATERAL (SWITCH)
    // ==========================================

    @FXML
    private void switchAgregarProducto(ActionEvent event) {
        limpiarCamposAgregar();
        
        if (selecCategoria.getItems().isEmpty()) {
            selecCategoria.getItems().addAll("platillo", "bebida", "postre", "entrada");
        }
        
        pnlAgregarProducto.setVisible(true);
        pnlModificarProducto.setVisible(false);
    }

    @FXML
    private void switchModificarProducto(ActionEvent event) {
        limpiarCamposModificar();
        
        // Inicializa el Spinner con el rango del FXML (1 a 9999)
        spnIdModDatos.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999, 1));
        
        if (selecCategoriaModDatos.getItems().isEmpty()) {
            selecCategoriaModDatos.getItems().addAll("platillo", "bebida", "postre", "entrada");
        }
        
        System.out.println("Cambiando a modo modificar...");
        pnlModificarProducto.setVisible(true);
        pnlAgregarProducto.setVisible(false);
    }

    @FXML
    private void switchEliminarProducto(ActionEvent event) {
        System.out.println("Cambiando a modo eliminar...");
        ocultarSubpaneles();
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
            mostrarAlerta("Error", "No se pudo cargar la vista de la tabla", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void generarReporte(ActionEvent event) throws Exception {
        System.out.println("Generando reporte diario...");
        LocalDate fechaActual = LocalDate.now();
        File carpetaDiarios = new File("reportes/diarios");
        if (!carpetaDiarios.exists()) {
        carpetaDiarios.mkdirs();
    }
        List<Pedido> pedidos = this.dbPedidos.cargarPedidos();
         try {
            FileWriter archivo = new FileWriter("reportes/diarios/Reporte"+fechaActual+".txt");
            for (Pedido pedido : pedidos) {
                archivo.write(pedido.toString()+"\n");
            }
            archivo.close();
            System.out.println("Archivo generado correctamente.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
        @FXML
    private void generarReporteM(ActionEvent event) throws Exception {
        System.out.println("Generando reporte mensual...");
    YearMonth mesActual = YearMonth.now();
    File carpetaDiarios = new File("reportes/diarios");
    File carpetaMensual = new File("reportes/mensuales");
    if (!carpetaMensual.exists()) {
        carpetaMensual.mkdirs();
    }
    File archivoMensual = new File(
        carpetaMensual,
        "ReporteMensual_" + mesActual + ".txt"
    );
    try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivoMensual))) {

        escritor.write("===== REPORTE MENSUAL =====");
        escritor.newLine();
        escritor.write("Mes: " + mesActual);
        escritor.newLine();
        escritor.write("===========================");
        escritor.newLine();
        escritor.newLine();

        for (int dia = 1; dia <= mesActual.lengthOfMonth(); dia++) {

            LocalDate fecha = mesActual.atDay(dia);

            File archivoDiario = new File(
                carpetaDiarios,
                "Reporte" + fecha + ".txt"
            );
            if (archivoDiario.exists()) {
                escritor.write("---------- " + fecha + " ----------");
                escritor.newLine();
                List<String> lineas = Files.readAllLines(archivoDiario.toPath());
                for (String linea : lineas) {
                    escritor.write(linea);
                    escritor.newLine();
                }
                escritor.newLine();
                escritor.write("-----------------------------------");
                escritor.newLine();
                escritor.newLine();
            }
        }
        System.out.println("Reporte mensual generado en:");
        System.out.println(archivoMensual.getAbsolutePath());

    } catch (IOException e) {
        System.out.println("Error al generar reporte mensual: " + e.getMessage());
    }
    }
    // ==========================================
    //   LÓGICA OPERATIVA (AGREGAR)
    // ==========================================

    @FXML
    private void agregar(ActionEvent event) {
        // 1. Validar campos
        if (this.camposVaciosAgregar()) {
            mostrarAlerta("Campos Vacíos", "Por favor rellene todos los campos para continuar", Alert.AlertType.WARNING);
            return; 
        }

        // Intenta agregar el producto
        try {
            menuDao = new ProductoMenuDAOImpl();
            
            // Si no se selecciona imagen, se asigna una por defecto
            if(imageUrl == null || imageUrl.isBlank() ){
                imageUrl = "img_productos/porDefecto.jpg";
            }

            String nombre = txtNombre.getText().trim();
            String categoria = selecCategoria.getValue(); 
            String ingredientes = txtIngredientes.getText().trim();
            String imagenRuta = imageUrl;

            String precioStr = txtPrecio.getText().trim();
            double precio = Double.parseDouble(precioStr);

            if (!longitudCaracteresAgregarValida()){
                return;
            }

            ProductoMenu producto = new ProductoMenu(nombre, categoria, imagenRuta, precio, ingredientes, true);

            if (menuDao.agregarProductoMenu(producto)) {
                mostrarAlerta("Éxito", "Producto agregado correctamente", Alert.AlertType.INFORMATION);
                limpiarCamposAgregar();
                return;
            }

            mostrarAlerta("Fracaso", "El producto no se pudo agregar", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Formato", "El precio debe ser un número válido.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error ", e.getMessage(), Alert.AlertType.ERROR);
            if (e.getMessage() != null && e.getMessage().equals("Error de acceso a la BD, intente mas tarde")){
                limpiarCamposAgregar();
                this.pnlAgregarProducto.setVisible(false);
            }
        }
    }

    // ==========================================
    //   LÓGICA OPERATIVA (MODIFICAR Y BUSCAR)
    // ==========================================

    @FXML
    private void buscarModDatos(ActionEvent event) {
        int idBuscar = spnIdModDatos.getValue();
        System.out.println("Buscando producto del menú para modificar con ID: " + idBuscar);

        try {
            menuDao = new ProductoMenuDAOImpl();
            ProductoMenu producto = menuDao.obtenerProductoMenu(idBuscar);

            if (producto == null) {
                mostrarAlerta("Null", "Elemento no encontrado", Alert.AlertType.INFORMATION);
                limpiarCamposModificar();
                return;
            }

            // Mapeo de datos recuperados hacia los componentes editables
            idModificarDatos = producto.getId();
            txtNombreModDatos.setText(producto.getNombre());
            selecCategoriaModDatos.setValue(producto.getCategoria());
            txtPrecioModDatos.setText(String.valueOf(producto.getPrecio()));
            txtIngredientesModDatos.setText(producto.getIngredientes());
            
            // Sincronizar el ToggleButton de disponibilidad
            btnDisponibilidadModDatos.setSelected(producto.getDisponibilidad());
            btnDisponibilidadModDatos.setText(producto.getDisponibilidad() ? "Disponible" : "No Disponible");
            btnDisponibilidadModDatos.setStyle(producto.getDisponibilidad() ? "-fx-background-color: green;" : "-fx-background-color: red;");
            
            // Mantener la ruta de la imagen actual por si no se cambia
            imageUrl = producto.getImagenRuta(); 
            
            //Muestra el subpanel que contiene los campos de modificacion anteriores
            subpnlCamposModificar.setVisible(true);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void modificar(ActionEvent event) {
        if (this.camposVaciosModificar()) {
            mostrarAlerta("Campos Vacíos", "Por favor rellene todos los campos para continuar", Alert.AlertType.WARNING);
            return;
        }

        try {
            menuDao = new ProductoMenuDAOImpl();

            int id = idModificarDatos;
            String nombre = txtNombreModDatos.getText().trim();
            String categoria = selecCategoriaModDatos.getValue();
            String ingredientes = txtIngredientesModDatos.getText().trim();
            String imagenRuta = imageUrl; 
            boolean disponible = btnDisponibilidadModDatos.isSelected();

            String precioStr = txtPrecioModDatos.getText().trim();
            double precio = Double.parseDouble(precioStr);

            if (!longitudCaracteresModificarValida()) {
                return;
            }

            boolean confirmar = mostrarConfirmacion("Confirmación", "¿Está seguro de MODIFICAR LOS DATOS?");
            if (!confirmar) {
                return;
            }

            // Construcción del objeto con los nuevos datos
            ProductoMenu producto = new ProductoMenu(id, nombre, categoria, imagenRuta, precio, ingredientes, disponible);

            if (menuDao.modificarProductoMenu(producto)) { // Asumiendo que existe este método en tu DAO
                mostrarAlerta("Éxito", "Producto modificado correctamente", Alert.AlertType.INFORMATION);
                limpiarCamposModificar();
                return;
            }

            mostrarAlerta("Fracaso", "El producto no se pudo modificar", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Formato", "El precio modificado debe ser un número válido.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error ", e.getMessage(), Alert.AlertType.ERROR);
            if (e.getMessage() != null && e.getMessage().equals("Error de acceso a la BD, intente mas tarde")){
                limpiarCamposModificar();
                this.pnlModificarProducto.setVisible(false);
            }
        }
    }

    // Lógica reutilizable del FileChooser para ambos casos
    private void procesarSeleccionImagen(Button botonOrigen) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"
        );
        fileChooser.getExtensionFilters().add(extFilter);

        Stage stage = (Stage) botonOrigen.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                File carpetaDestino = new File("img_productos");
                if (!carpetaDestino.exists()) {
                    carpetaDestino.mkdir();
                }

                File destino = new File(carpetaDestino, file.getName());

                if (destino.exists()) {
                    this.mostrarAlerta("Nombre de imagen existente", "Usando el existente...", Alert.AlertType.INFORMATION);
                    imageUrl = "img_productos/" + file.getName();
                } else {
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

    @FXML
    private void agregarImagen(ActionEvent event) {
        procesarSeleccionImagen(btnSeleccionar);
    }

    @FXML
    private void agregarImagenModDatos(ActionEvent event) {
        procesarSeleccionImagen(btnSeleccionarModDatos);
    }

    @FXML
    private void cambiarDisponibilidad(ActionEvent event) {
        if (btnDisponibilidadModDatos.isSelected()) {
            btnDisponibilidadModDatos.setText("Disponible");
            btnDisponibilidadModDatos.setStyle("-fx-background-color: green;");
        } else {
            btnDisponibilidadModDatos.setText("No Disponible");
            btnDisponibilidadModDatos.setStyle("-fx-background-color: red;");
        }
    }

    // ==========================================
    //   VALIDACIONES Y LIMPIEZA
    // ==========================================

    private boolean camposVaciosAgregar() {
        return txtNombre.getText().trim().isBlank()
                || txtPrecio.getText().trim().isBlank()
                || txtIngredientes.getText().trim().isBlank()
                || selecCategoria.getValue() == null || selecCategoria.getValue().trim().isBlank();
    }

    private boolean camposVaciosModificar() {
        return txtNombreModDatos.getText().trim().isBlank()
                || txtPrecioModDatos.getText().trim().isBlank()
                || txtIngredientesModDatos.getText().trim().isBlank()
                || selecCategoriaModDatos.getValue() == null || selecCategoriaModDatos.getValue().trim().isBlank();
    }

    private void limpiarCamposAgregar() {
        this.imageUrl = "";
        txtNombre.clear();
        selecCategoria.getSelectionModel().clearSelection();
        txtPrecio.clear();
        txtIngredientes.clear();
    }

    private void limpiarCamposModificar() {
        this.imageUrl = "";
        if (spnIdModDatos.getValueFactory() != null) {
            spnIdModDatos.getValueFactory().setValue(1);
        }
        txtNombreModDatos.clear();
        selecCategoriaModDatos.getSelectionModel().clearSelection();
        txtPrecioModDatos.clear();
        txtIngredientesModDatos.clear();
        btnDisponibilidadModDatos.setSelected(true);
        btnDisponibilidadModDatos.setText("Disponible");
        
        //Oculta el subpanel de modificacion
        subpnlCamposModificar.setVisible(false);
    }

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

    private boolean longitudCaracteresModificarValida(){
        if(txtNombreModDatos.getText().trim().length() > 100){
            mostrarAlerta("Error", "Limite de 100 caracteres excedido en nombre", Alert.AlertType.WARNING);
            return false;
        }
        if (txtIngredientesModDatos.getText().trim().length() > 500) {
            mostrarAlerta("Error", "Limite de 500 caracteres excedido en ingredientes", Alert.AlertType.WARNING);
            return false;
        }
        if (txtPrecioModDatos.getText().trim().length() > 10) {
            mostrarAlerta("Error", "Limite de 10 caracteres(incluyendo decimales) excedido en precio", Alert.AlertType.WARNING);
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

    private boolean mostrarConfirmacion(String titulo, String mensaje) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);

        Optional<ButtonType> r = a.showAndWait();
        return r.isPresent() && r.get() == ButtonType.OK;
    }
}