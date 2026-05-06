/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurante.equipo.a;

import Modelo.Pedido;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import Modelo.Impl.PedidoDAOImpl;
import Modelo.Impl.ProductoMenuDAOImpl;
import Modelo.ProductoMenu;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 *
 * @author LENOVO CORE i7
 */
public class PedidoController {
    
    private PedidoDAOImpl dbPedidos;
    
    private ProductoMenuDAOImpl dbProductoMenu;
    
    private String platilloSeleccionado;
    
    
  //Seleccion de platillos
    
    @FXML
    private TabPane tabPlatillos;
    
    @FXML
    private Tab vistaEntradas;
    
    @FXML 
    private FlowPane flowEntradas;
    
    @FXML
    private Tab vistaPlatillos;
    
    @FXML
    private FlowPane flowPlatillos;
    
    @FXML
    private Tab vistaBebidas;
    
    @FXML
    private FlowPane flowBebidas;
    
    @FXML
    private Tab vistaPostres;
    
    @FXML
    private FlowPane flowPostres;
    
    //Botones de pedidos
    
    @FXML
    private Button btnAgregarPedido; // Botón para agregar un pedido
    
    @FXML
    private Button btnBuscarPedido; // Botón para buscar un pedido
    
    @FXML
    private TextField tfBuscarPedido; // Campo de texto para ingresar el id y buscar un pedido
            
    @FXML
    private Button btnCancelarPedido; // Botón para cancelar un pedido
            
    @FXML
    private Button btnModificarPedido; // Botón para cancelar un pedido
    
    @FXML
    private Button btnCobrarCuenta; // Botón para cobrar la cuenta de un pedido
    
    @FXML
    private Spinner spnCantidad; // Elemento para seleccionar la cantidad de platillos
    
    @FXML 
    private ChoiceBox <Integer> chbMesa; // Lista desplegable de las mesas
    
    @FXML
    private Button btnSalir;
    
    @FXML
    private TableView<Pedido> pedidosTab; //Tabla de pedidos

    @FXML
    private TableColumn<Pedido, Integer> columnaIdPedido;

    @FXML
    private TableColumn<Pedido, String> columnaProducto;

    @FXML
    private TableColumn<Pedido, Integer> columnaCantidad;
    
    @FXML
    private TableColumn<Pedido, Double> columnaSubtotal;
    
    @FXML
    private TableColumn<Pedido, Boolean> columnaEstado;
    
    @FXML
    private TableColumn<Pedido, Boolean> columnaPreparado;
    
    /**
     * Inicializa el controlador.
     *
     * @throws SQLException Si ocurre un error al conectar con la base de datos.
     */
    @FXML
    public void initialize() throws SQLException, Exception {
        
        columnaIdPedido.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        columnaProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        columnaSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        columnaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        columnaPreparado.setCellValueFactory(new PropertyValueFactory<>("preparado"));
        
        dbPedidos = new PedidoDAOImpl();
        dbProductoMenu = new ProductoMenuDAOImpl();
        this.cargarMenuDinamico();
        //Declarar el numero de mesas en la ChoiceBox
        if (chbMesa != null) {
            chbMesa.getItems().addAll(1,2,3,4,5,6,7,8,9,10);
            
        //Definir un valor por defecto
        
            
  
            //Listener para cambiar de mesa
            chbMesa.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if(newVal != null) {
                    this.cargarPedidosMesa(newVal);
                }
            });
            chbMesa.getSelectionModel().selectFirst();
        }
        else{
        this.cargarPedidos();
        }
        
        
        
        // Configurar el rango del Spinner (del 1 al 100 iniciando en 1)
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spnCantidad.setValueFactory(valueFactory);
        
        // AGREGAR EL LISTENER
        spnCantidad.valueProperty().addListener((observable, oldValue, newValue) -> {   
        });   
    }
    
    @FXML
    private void agregarPedido(ActionEvent event) {
        if (platilloSeleccionado == null) {
            this.mostrarAlerta("Por favor, selecciona primero un platillo del menú.");
            return;
        }

        int cantidad = (int) spnCantidad.getValue();
        int mesa = (int) chbMesa.getValue();
        
        try {
            boolean exito = dbPedidos.agregarPedido(platilloSeleccionado, cantidad, mesa);
            if (exito) {
                // Refrescar la tabla
                    this.cargarPedidosMesa(mesa);
            }
        } catch (Exception e) {
            this.mostrarAlerta("No se pudo agregar el pedido: " + e.getMessage());
        }
    }
    
    @FXML
    private void buscarPedido(ActionEvent event) {
        String textoBusqueda = tfBuscarPedido.getText().trim();
    
        if (textoBusqueda.isEmpty()) {
            this.mostrarAlerta("Por favor, ingresa un ID o el nombre del platillo.");
            return;
        }

        // Obtenemos la mesa seleccionada actualmente en el ChoiceBox
        Integer mesaActual = chbMesa.getValue();
        if (mesaActual == null) {
            this.mostrarAlerta("Por favor, selecciona una mesa primero.");
            return;
        }

        try {
            // Pasamos tanto el texto de búsqueda como la mesa actual al DAO
            ArrayList<Pedido> resultados = dbPedidos.buscarPedidos(textoBusqueda, mesaActual);
        
            if (resultados == null) {
                this.mostrarAlerta("No se encontraron pedidos coincidentes.");
                pedidosTab.getItems().clear(); 
            } else {
                // Evaluamos si la búsqueda fue por ID (si el texto ingresado es numérico)
                boolean esId = textoBusqueda.matches("\\d+");
            
                if (esId) {
                    // Al buscar por ID solo regresará un resultado
                    Pedido pedidoEncontrado = resultados.get(0);
                
                    // Cambiamos el ChoiceBox a la mesa que le pertenece a ese pedido
                    chbMesa.setValue(pedidoEncontrado.getPMesa());
                }
            
                // Actualizamos la tabla con los resultados
                pedidosTab.setItems(FXCollections.observableArrayList(resultados));
                tfBuscarPedido.clear();
            }
        } catch (Exception e) {
            this.mostrarAlerta("Error en la búsqueda: " + e.getMessage());
        }
    }
    
    @FXML
    private void salir(ActionEvent event) {
        // Obtiene la ventana actual y la cierra
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }
    
    private void cargarPedidos() {
        try {
            // 1. Obtener los pedidos desde la BD
            List<Pedido> pedidos = this.dbPedidos.cargarPedidos();

            // 3. Crear un ObservableList y asignarlo a la tabla
            pedidosTab.setItems(FXCollections.observableArrayList(pedidos));

        } catch (Exception e) {
            this.mostrarAlerta("ERROR Carga de datos: " + e.getMessage());
        }
    }
    
    private void cargarPedidosMesa(int pMesa) {
        try{
            List<Pedido> pedidos = this.dbPedidos.cargarPedidosMesa(pMesa);
            
            pedidosTab.setItems(FXCollections.observableArrayList(pedidos));
            
        } catch (Exception e) {
            this.mostrarAlerta("ERROR Carga de datos: " + e.getMessage());
    }
    }
    
    private void cargarMenuDinamico() throws Exception{
        // 1. Limpiar todos los contenedores antes de cargar
        flowEntradas.getChildren().clear();
        flowPlatillos.getChildren().clear();
        flowBebidas.getChildren().clear();
        flowPostres.getChildren().clear();
        ArrayList<ProductoMenu> lProductos = new ArrayList<>();
        lProductos= dbProductoMenu.obtenerProductosMenu();
        for(ProductoMenu producto: lProductos){
            Button btnGenerado= new Button();
            btnGenerado.setPrefSize(120, 120); //Tamaño del boton
            btnGenerado.setContentDisplay(ContentDisplay.TOP);//Elegimos la posicion del contenido a mostrar en la parte de arriba del boton
           
        //Asignar el nombre
            btnGenerado.setText(producto.getNombre());
        
        //Asignar imagen
            try{
            
                Image img = new Image(getClass().getResourceAsStream(producto.getImagenRuta()));
                ImageView imgView = new ImageView(img);
                imgView.setFitWidth(80); // Ajustar tamaño de imagen
                imgView.setFitHeight(80);
                imgView.setPreserveRatio(true);
                btnGenerado.setGraphic(imgView);
            } catch (Exception e) {
                this.mostrarAlerta("No se pudo cargar imagen para: " + producto.getNombre());}
        
            btnGenerado.setOnAction(event -> {
            // Actualizas la variable del nombre
            platilloSeleccionado = producto.getNombre();
            System.out.println("Platillo seleccionado: " + platilloSeleccionado);
            });
            
            //Asignar el boton en la categoria correspondiente
            // Comparamos la categoría que viene de SQL (en minúsculas para seguridad)
            String catSQL = producto.getCategoria().toLowerCase();
        
            switch (catSQL) {
                case "entrada":
                    flowEntradas.getChildren().add(btnGenerado);
                    break;
                case "platillo":
                    flowPlatillos.getChildren().add(btnGenerado);
                    break;
                case "bebida":
                    flowBebidas.getChildren().add(btnGenerado);
                    break;
                case "postre":
                    flowPostres.getChildren().add(btnGenerado);
                    break;
                default:
                    System.err.println("Categoría desconocida para: " + producto.getNombre());
                    // Podrías tener una pestaña "Otros" o ignorarlo
                    break;
            }
            }
        
    }
    
    /**
     * Muestra una alerta de advertencia con un mensaje específico.
     *
     * @param mensaje El mensaje que se mostrará en la alerta.
     */
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("AVISO");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
    
    // Quitamos la línea de getScene().getWindow() que causa el error
    // JavaFX mostrará la alerta en el centro de la pantalla por defecto
    alert.showAndWait();
}    
}