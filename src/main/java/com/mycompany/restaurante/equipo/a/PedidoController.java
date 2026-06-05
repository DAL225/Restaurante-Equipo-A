/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurante.equipo.a;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import Modelo.Impl.PedidoDAOImpl;
import Modelo.Impl.ProductoMenuDAOImpl;
import Modelo.Impl.VentasDAOImpl;
import Modelo.Pedido;
import Modelo.ProductoMenu;
import Modelo.Ventas;
import javafx.scene.layout.AnchorPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;



/**
 *
 * @author LENOVO CORE i7
 */
public class PedidoController {
    
    //ObjetoDaoImpl de Pedidos
    private PedidoDAOImpl dbPedidos;
    //ObjetoDaoImpl de ProductoMenu
    private ProductoMenuDAOImpl dbProductoMenu;
    //ObjetoDaoImpl de Ventas
    private VentasDAOImpl dbVentas;
    //Variable que almacena el nombre del platillo seleccionado en el menu
    private String platilloSeleccionado;
    //Variable que almacena el pedido seleccionado en la tabla
    private Pedido pedidoSeleccionado;
    //Variable para almacenar el id de un pedido
    private int p_Id;
    //Variable para almacenar el nombre de un platillo
    private String p_platillo;
    //Variable que almacena la cantidad deseada de un platillo
    private int p_cantidad;
    //Bandera para activar bloqueo
    private boolean bloqueo = false;
    //Bandera para evitar bucles al actualizar la tabla de Pedidos
    private boolean exito = false;
    //HashMap que almacena los botones generados del menu
    private HashMap<String, Button> botones = new HashMap<>();
    //Boton
    private Button boton;
    //Bandera para indicar que el pago es en efectivo
    private Boolean efectivo=false;
    //Bandera para indicar que el pago es con tarjeta
    private Boolean tarjeta=false;
    //Variable que almacena el subtotal
    private double v_subtotal;
    //Variable que almacena el tipo de tarjeta
    private String tipoTarjeta;
    //Variable para el objeto venta
    private Ventas venta;
    //Carpeta para almacenar los Tickets de venta
    private final String carpetaTickets = "src/main/resources/com/mycompany/restaurante/equipo/a/tickets";
    //Carpeta para almacenar los Tickets de venta
    private final String carpetaFacturas = "src/main/resources/com/mycompany/restaurante/equipo/a/facturas";
    
  //Seleccion de platillos
    
    //Inicializacion del VBOX
    @FXML
    private VBox rootVBox;
    
    @FXML
    private AnchorPane apPedidos;
    //Contenedor de las pestañas para los platillos
    @FXML
    private TabPane tabPlatillos;
    //Pestaña para las entradas
    @FXML
    private Tab vistaEntradas;
    //Panel para acomodar los botones
    @FXML 
    private FlowPane flowEntradas;
    //Pestaña para los platillos
    @FXML
    private Tab vistaPlatillos;
    //Panel para acomodar los botones
    @FXML
    private FlowPane flowPlatillos;
    //Pestaña para las bebidas
    @FXML
    private Tab vistaBebidas;
    //Panel para acomodar los botones
    @FXML
    private FlowPane flowBebidas;
    //Pestaña para los postres
    @FXML
    private Tab vistaPostres;
    //Panel para acomodar los botones
    @FXML
    private FlowPane flowPostres;
    
//Botones de pedidos    
    // Botón para agregar un pedido
    @FXML
    private Button btnAgregarPedido;
    
    // Botón para buscar un pedido
    @FXML
    private Button btnBuscarPedido;
    
    // Campo de texto para ingresar el id y buscar un pedido
    @FXML
    private TextField tfBuscarPedido;
    
    // Botón para cancelar un pedido
    @FXML
    private Button btnCancelarPedido; 
    
    // Botón para modificar un pedido
    @FXML
    private Button btnModificarPedido;
    
    // Botón para cobrar la cuenta de una mesa
    @FXML
    private Button btnCobrarCuenta;
    
    // Elemento para seleccionar la cantidad de platillos
    @FXML
    private Spinner<Integer> spnCantidad; 
    
    // Lista desplegable de las mesas
    @FXML 
    private ChoiceBox <Integer> chbMesa; 
    
    // Boton para salir de la ventana
    @FXML
    private Button btnSalir;
    
    // Tabla de pedidos
    @FXML
    private TableView<Pedido> pedidosTab;
    //Columna de id
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
    //ColumnaPreparado
    @FXML
    private TableColumn<Pedido, Boolean> columnaPreparado;
    //Elementos FXML
    
    //Panel del area de pedidos
    @FXML
    private Pane panelPedidos;
    //Panel de los datos de Cuenta
    @FXML
    private Pane panelDatosCuenta;
    //TextField para mostrar el subtotal de la cuenta
    @FXML
    private TextField tf1;
    //TextField para mostrar el iva de la cuenta
    @FXML
    private TextField tf2;
    //TextField para mostrar el total de la cuenta
    @FXML
    private TextField tf3;
    //Panel para seleccionar el tipo de pago
    @FXML
    private Pane panelSeleccionPago;
    //Boton para seleccionar el tipo de pago en Efectivo
    @FXML
    private Button btnEfectivo;
    //Boton para seleccionar el tipo de pago en Tarjeta
    @FXML
    private Button btnTarjeta;
    //Panel para la seccion de confirmar el pago;
    @FXML
    private Pane panelConfirmarPago;
    //Etiqueta para indicar que seleccione el tipo de tarjeta
    @FXML
    private Label tituloTipoDePago;
    //ChoiceBox para seleccionar el tipo de tarjeta
    @FXML
    private ChoiceBox chbTipoDeTarjeta;
    //Boton para confirmar el pago
    @FXML
    private Button btnConfirmarPago;
    
    //Panel de seccion generar factura
    @FXML
    private Pane panelGenerarFactura;
    //TextField para el subtotal
    @FXML
    private TextField tfgf_subtotal;
    //TextField para el iva
    @FXML
    private TextField tfgf_iva;
    //TextField para el monto
    @FXML
    private TextField tfgf_monto;
    //TextField para el nombre
    @FXML
    private TextField tfgf_nombre;
    //TextField para el rfc
    @FXML
    private TextField tfgf_rfc;
    //TextField para el correo electronico
    @FXML
    private TextField tfgf_correo;
    //TextField para el regimen
    @FXML
    private TextField tfgf_regimen;
    @FXML
    private Button btnElaborarFactura;  
    @FXML
    private Button btnGenerarFactura;
    @FXML
    private Button btnRegresarAlMenu;
    @FXML
    private Button btnregresarSfacturas; 
    /**
     * Inicializa el controlador.
     * @throws SQLException Si ocurre un error al conectar con la base de datos.
     */
    @FXML
    public void initialize() throws SQLException, Exception {
        //Desactivamos botones especificos
        btnElaborarFactura.setDisable(true);
        btnElaborarFactura.setVisible(false);
        btnElaborarFactura.setManaged(false); 
        btnRegresarAlMenu.setDisable(true);
        btnRegresarAlMenu.setVisible(false);
        btnRegresarAlMenu.setManaged(false);
        //Se crean los objetos DAOImpl
        dbPedidos = new PedidoDAOImpl();
        dbProductoMenu = new ProductoMenuDAOImpl();
        dbVentas= new VentasDAOImpl();
        //Se declaran los valores de la tabla
        columnaIdPedido.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        columnaProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        columnaSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        columnaPreparado.setCellValueFactory(new PropertyValueFactory<>("preparado"));

        columnaPreparado.setCellValueFactory(new PropertyValueFactory<>("preparado"));
        
        // 2. Configurar la apariencia (la lógica que definimos antes)
        columnaPreparado.setCellFactory(column -> new TableCell<Pedido, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Terminado" : "Pendiente");
                }
            }
        });
        
        //Se cargan los botones del menu
        this.cargarMenuDinamico();
        //Se declara el numero de mesas en la ChoiceBox
        if (chbMesa != null) {
            chbMesa.getItems().addAll(1,2,3,4,5,6,7,8,9,10);
            
            //Listener que detecta los cambios de seleccion de las mesas
            chbMesa.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                //Bandera para evitar bucles
                if(!exito){
                    //Si no se estan modificando pedidos
                    if(!bloqueo){
                        if(newVal != null) {
                            //Se cargan los pedidos la mesa selecionada en la tabla
                            this.cargarPedidosMesa(newVal);
                        }
                    //Si se estan modificando pedidos
                    }else{
                        //El choicebox se queda con el antiguo valor ya que no se puede seleccionar otra mientras se modifica un pedido
                        //exito es una bandera que nos sirve para evitar un bucle y que no se accione el listener
                        exito=true;
                        chbMesa.getSelectionModel().select(oldVal);
                        this.mostrarAlerta("No puedes seleccionar otra mesa");
                        exito=false;
                    }
                }else{
                    return;
                }}
        );
            //Se cargan los pedidos de la primera mesa por defecto
            chbMesa.getSelectionModel().selectFirst();
        }
        else{
        this.cargarPedidos();
        }
        //Listener para detectar el tipo de tarjeta seleccionada
        if(chbTipoDeTarjeta != null){
            chbTipoDeTarjeta.getItems().addAll("Debito","Credito");
            chbTipoDeTarjeta.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)->{
                //El valor seleccionado se guarda en la variable "tipoTarjeta"
                if(newVal!=null){
                   tipoTarjeta=newVal.toString();
                }else{
                    this.mostrarAlerta("Porfavor selecciona un tipo de tarjeta");
                }
            });
        }else{
            this.mostrarAlerta("El pago con tarjeta esta inhabilitado");
        }
        
        
        
        // Configurar el rango del Spinner (del 1 al 100 iniciando en 1)
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spnCantidad.setValueFactory(valueFactory);
        
        // Listener del Spinner para marcar la cantidad deseada
        spnCantidad.valueProperty().addListener((observable, oldValue, newValue) -> {   
        });
        
        // Listener de la tabla de pedidos para detectar los pedidos seleccionados
        pedidosTab.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            //Si no se estan modificando pedidos
            if(!bloqueo){
                //Si se selecciono un pedido con anterioridad
                if(newVal != null) {
                    //Se guarda el pedido seleccionado
                    pedidoSeleccionado=newVal;
                }
            //Si se esta modificando un pedido entonces regresa null y no permite seleccionar otro
            } else {
                this.mostrarAlerta("No puedes seleccionar un nuevo pedido");
                
                return;
            }    
            });
        //Listener del boton "Efectivo"
        btnEfectivo.setOnAction(event->{
            //Levantamos las banderas para indicar cual es el tipo de pago
            efectivo=true;
            tarjeta=false;
            //Activamos el panel para confirmar el pago
            panelConfirmarPago.setDisable(false);
            panelConfirmarPago.setVisible(true);
            panelConfirmarPago.setManaged(true);
            //Desactivamos elementos innecesarios
            tituloTipoDePago.setVisible(false);
            chbTipoDeTarjeta.setDisable(true);
            chbTipoDeTarjeta.setVisible(false);
            chbTipoDeTarjeta.setManaged(false);
            panelConfirmarPago.toFront();
            //Desactivamos el panel de la seleccion de pago
            panelSeleccionPago.setDisable(true);
            panelSeleccionPago.setVisible(false);
            panelSeleccionPago.setManaged(false);
        });
        //Listener para el boton "Tarjeta"
        btnTarjeta.setOnAction(event->{
            //Levantamos las banderas para indicar cual es el tipo de pago
            tarjeta=true;
            efectivo=false;
            //Activamos el panel para confirmar el pago
            panelConfirmarPago.setDisable(false);
            panelConfirmarPago.setVisible(true);
            panelConfirmarPago.setManaged(true);
            panelConfirmarPago.toFront();
            //Desactivamos el panel de la seleccion de pago
            panelSeleccionPago.setDisable(true);
            panelSeleccionPago.setVisible(false);
            panelSeleccionPago.setManaged(false);
        });
        
        
        //Listener para el boton "Confirmar Pago"
        btnConfirmarPago.setOnAction(event->{
            //Si el pago es en efectivo
            if (efectivo){
                    try{
                        // 1. Ejecutamos el método del DAO que devuelve un boolean
                        
                        venta = dbVentas.agregarVenta(this.obtenerDescripccionCuenta(), v_subtotal, "Efectivo");
                        
                        if (venta!=null) {
                            this.mostrarAlerta("El cobro se ha realizado correctamente");
                            handleGenerarTicket(venta);
                            
                        } else {
                            this.mostrarAlerta("No se pudo procesar el cobro en la base de datos.");
                        }
                        
                    }catch(Exception e){
                        this.mostrarAlerta("Ha ocurrido un error al cobrar la cuenta: "+e);
                    //Por ultimo se desactiva la bandera, la vista regresa al Menu y el bloqueo se levanta
                    }finally{
                        efectivo=false;
                        this.desactivarBotonConfirmarPago();
                        this.habilitarBotonesPreFactura();
                        //this.cargarPanelSeleccionMenu();
                        //bloqueo=false;
                        this.limpiarPedidosTab();
                        this.cargarPedidosMesa(chbMesa.getValue());
                    }
                  
            }else if(tarjeta){
                //Se verifica que se haya seleccionado un tipo de tarjeta
                if(chbTipoDeTarjeta.getValue()==null){
                    this.mostrarAlerta("Porfavor ingresa el tipo de tarjeta");
                    return;
                }
                    try{
                        // 1. Ejecutamos el método del DAO que devuelve un boolean
                        venta = dbVentas.agregarVenta(this.obtenerDescripccionCuenta(), v_subtotal, "Tarjeta de "+tipoTarjeta);
                        
                        if (venta!=null) {
                            this.mostrarAlerta("El cobro se ha realizado correctamente");

                            // 3. Generamos el Ticket PDF
                            handleGenerarTicket(venta);
                           
                        } else {
                            this.mostrarAlerta("No se pudo procesar el cobro en la base de datos.");
                        }
                    }catch(Exception e){
                    //Por ultimo se desactiva la bandera, la vista regresa al Menu y el bloqueo se levanta
                        this.mostrarAlerta("Ha ocurrido un error al cobrar la cuenta: "+e);
                    }finally{
                        tarjeta=false;
                        this.desactivarBotonConfirmarPago();
                        this.habilitarBotonesPreFactura();
                        //this.cargarPanelSeleccionMenu();
                        //bloqueo=false;
                        this.limpiarPedidosTab();
                        this.cargarPedidosMesa(chbMesa.getValue());
                    }
            }else{
                this.mostrarAlerta("Porfavor seleccione un metodo de pago");
            }
        });
        
        // Añadimos el filtro al contenedor raíz
        rootVBox.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            
            // Verificamos si el clic fue directamente en el VBox o en uno de los Paneles
            // Esto evita que se dispare si haces clic en un botón, tabla o textField
            if(!bloqueo){
                if (event.getTarget() == rootVBox || event.getTarget() instanceof Pane) {
                    System.out.println("Clic en espacio vacío, limpiando elementos...");
                    pedidosTab.getSelectionModel().clearSelection();
                    pedidoSeleccionado=null;
                
                    // AQUÍ TUS ACCIONES
                    // Por ejemplo: deseleccionar filas de la tabla o resetear estados
                    tfBuscarPedido.clear();
                }
            }else{
                System.out.println("La accion debe continuar");
            }
        });
    }
    /**
     * Metodo para agregarPedido
     * @param event 
     */
    @FXML
    private void agregarPedido(ActionEvent event) {
        //Si se esta modificando el pedido
        if(!bloqueo){
        //Si no se selecciono ningun platillo muestra un mensaje
            if (platilloSeleccionado == null) {
                this.mostrarAlerta("Por favor, selecciona primero un platillo del menú.");
                return;
            }
            // Se recupera la cantidad deseada 
            int cantidad = (int) spnCantidad.getValue();
            // Se recupera la mesa del pedido
            int mesa = (int) chbMesa.getValue();
        
            // Se agrega el pedido a la base de datos y se refresca la tabla
            try {
                boolean exito = dbPedidos.agregarPedido(platilloSeleccionado, cantidad, mesa);
                if (exito) {
                    // Refrescar la tabla
                        this.cargarPedidosMesa(mesa);
                        //Se vacia el platilloSeleccionado
                        platilloSeleccionado = null;
                        //Reiniciamos el spnCantidad en 1
                        spnCantidad.getValueFactory().setValue(1);
                }
            //Si falla la operacion
            } catch (Exception e) {
                this.mostrarAlerta("No se pudo agregar el pedido: " + e.getMessage());
            }
        }else{
            this.mostrarAlerta("No puedes realizar esta accion");
        }
    }
    
    /**
     * Metodo para buscarPedido
     * @param event 
     */
    @FXML
    private void buscarPedido(ActionEvent event) {
        if(!bloqueo){
            //Se recupera el contenido escrito en el campo de texto
            String textoBusqueda = tfBuscarPedido.getText().trim();
            //Si el campo de texto esta vacio se muestra un mensaje indicando las instrucciones
            if (textoBusqueda.isEmpty()) {
                this.mostrarAlerta("Por favor, ingresa un ID o el nombre del platillo.");
                return;
            }
        
            // Obtenemos la mesa seleccionada actualmente en el ChoiceBox
            Integer mesaActual = chbMesa.getValue();
            //Si no se selecciono una mesa mostramos un mensaje para indicarle al usuario
            if (mesaActual == null) {
                this.mostrarAlerta("Por favor, selecciona una mesa primero.");
                return;
            }
            //Recuperamos los pedidos de la base de datos
            try {
                // Pasamos tanto el texto de búsqueda como la mesa actual al DAO
                ArrayList<Pedido> resultados = dbPedidos.buscarPedidos(textoBusqueda, mesaActual);
                //Si no hay pedidos que coincidad se le informa al usuario
                if (resultados == null) {
                    this.mostrarAlerta("No se encontraron pedidos coincidentes.");
                    //Se limpia la tabla
                    pedidosTab.getItems().clear();
                    //Si se encuentran pedidos que coincidan se evalua la busqueda  
                } else {
                    // Evaluamos si la búsqueda fue por ID (si el texto ingresado es numérico)
                    boolean esId = textoBusqueda.matches("\\d+");
                    //Si la busqueda fue por id se muestra el primer resultado y unico que se recupero
                    if (esId) {
                        // Al buscar por ID solo regresará un resultado
                        Pedido pedidoEncontrado = resultados.get(0);
                
                        // Cambiamos el ChoiceBox a la mesa que le pertenece a ese pedido
                        chbMesa.setValue(pedidoEncontrado.getPMesa());
                    }
            
                    // Actualizamos la tabla con los resultados (Si se realizo la busqueda por el nombre del platillo se mostraran todos)
                    pedidosTab.setItems(FXCollections.observableArrayList(resultados));
                    tfBuscarPedido.clear();
                }
            } catch (Exception e) {
                this.mostrarAlerta("Error en la búsqueda: " + e.getMessage());
            }
        }else{
            this.mostrarAlerta("No puedes realizar esta accion");
        }   
    }
    
    /**
     * Metodo para modificar los pedidos
     * @param event Presionar el boton de "modificar Pedido"
     * @throws Exception 
     */
    @FXML
    private void modificarPedido(ActionEvent event)throws Exception{
        //Si no se esta modificando un pedido (Primer click del boton)
        if(!bloqueo){
            //Si se selecciono un pedido de la tabla anteriormente
            if(pedidoSeleccionado!=null){
                if(!pedidoSeleccionado.getPreparado()){
                    //Si el pedido seleccionado no esta cancelado
                    if(pedidoSeleccionado.getEstado()){
                        //Se activa el bloqueo que previene presionar otros botones
                        bloqueo= true;
                        //Se muestran las indicaciones
                        this.mostrarAlerta("Porfavor ingresa el nuevo platillo y la cantidad deseada");
                        //Se recupera el boton del antiguo platillo seleccionado y se marca
                        boton=this.obtenerBoton(pedidoSeleccionado.getProducto());
                        boton.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
                        //Se muestra la cantidad anterior en el spinner
                        spnCantidad.getValueFactory().setValue(pedidoSeleccionado.getCantidad());
                        //Se recupera el id del pedido
                        p_Id=pedidoSeleccionado.getIdPedido();
                   
                    }else{
                        this.mostrarAlerta("El pedido ya esta cancelado selecciona otro");
                    }
                }else{
                    this.mostrarAlerta("Un pedido terminado no puede modificarse");
                }
            }else{
                this.mostrarAlerta("No se ha seleccionado ningun platillo");
            }
        //Si se esta modificando un pedido (Segundo ckick del boton) Esto finalizara la modificacion
        } else{
            //Se recupera la nueva cantidad seleccionada
            p_cantidad=(int) spnCantidad.getValue();
            //Se modifica el pedido en la base de datos
            dbPedidos.modificarPedido(p_Id, platilloSeleccionado, p_cantidad);
            //Se muestra el mensaje de confirmacion en la pantalla
            this.mostrarAlerta("El platillo ha sido modificado exitosamente");
            //Se desactiva el bloqueo
            bloqueo=false;
            //Se refresca la tabla de pedidos
            this.cargarPedidosMesa(pedidoSeleccionado.getPMesa());
            pedidoSeleccionado=null;
            //Reiniciamos el spnCantidad en 1
            spnCantidad.getValueFactory().setValue(1);
            boton.setStyle("");
                    }
    }
    
    
    
    
    
    
    /**
     * Metodo para cancelar pedidos
     * @param event Presionar el boton "cancelar pedido"
     * @throws Exception 
     */
    @FXML
    private void cancelarPedido(ActionEvent event)throws Exception{
        //Si no se esta modificando un pedido
        if(!bloqueo){
            //Si se selecciono algun pedido anteriormente
            if(pedidoSeleccionado!=null){
                //Se cancela el pedido desde la base de datos
                try{
                    dbPedidos.cancelarPedido(pedidoSeleccionado.getIdPedido());
                    this.cargarPedidosMesa(pedidoSeleccionado.getPMesa());
                    this.mostrarAlerta("El pedido ha sido cancelado exitosamente");
                    pedidoSeleccionado=null;
                }catch(Exception e){
                    this.mostrarAlerta("Un error ha ocurrido"+e);
                }
             
            }else{
                this.mostrarAlerta("No se ha seleccionado ningun pedido");
            }
        }else{
            this.mostrarAlerta("No puedes realizar esta accion");
        }
    }
    
    @FXML
    private void cobrarCuenta(ActionEvent event){
        var pedidos = pedidosTab.getItems();
        if(!pedidos.isEmpty()){
            bloqueo=true;
            this.cargarPanelDatosCuenta();
            this.mostrarAlerta("Seleccione el tipo de pago");
        }else{
            this.mostrarAlerta("Error, No puedes cobrar la cuenta de una mesa que no tiene pedidos");
        }
    }

    @FXML
    private void handleGenerarTicket(Ventas ventaAsignada) throws Exception{
        File carpetaDestino = new File(carpetaTickets);
        
        if (!carpetaDestino.exists()) {
            carpetaDestino.mkdirs(); 
        }
        
        String nombreArchivo = "Ticket de mesa numero "+ventaAsignada.getIdVenta()+ ".pdf";
        File archivoPdf = new File(carpetaDestino, nombreArchivo);
        
        boolean exito = crearTicket(archivoPdf, ventaAsignada);
        
        if (exito) {
            this.mostrarAlerta("Ticket generado correctamente en: " + archivoPdf.getAbsolutePath());
        } else {
            mostrarAlerta("Error, No se pudo generar el PDF.");
        }
    }
    
    private boolean crearTicket(File destino,Ventas venta) {
        // Si por alguna razón llegó vacío sin lanzar excepción
        if (venta == null) {
            this.mostrarAlerta("Error, No se encontraron datos de la venta para generar el ticket.");
            return false;
        }

        // Ahora que tenemos los datos asegurados, procedemos a construir el PDF
        try (PDDocument documento = new PDDocument()) {
            PDPage pagina = new PDPage();
            documento.addPage(pagina);
            
            try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina)) {
                
                // Iniciamos bloque de texto de forma limpia y corrida
                contenido.beginText();
                contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
                contenido.newLineAtOffset(50, 700);
            
                contenido.showText("Ticket de venta numero " + venta.getIdVenta());
            
                // Cuerpo del ticket
                contenido.newLineAtOffset(0, -30);
                contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contenido.showText("Datos de la venta:");
            
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Id de la venta: " + venta.getIdVenta());
            
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Platillos ordenados: ");
            
                // Nota: Si venta.getDescripcion() es muy largo o contiene saltos de línea (\n), 
                // showText fallará. Asegúrate de que sea un texto corto, o límpialo de saltos de línea.
              
                String[] platillos = venta.getDescripcion().split("\n"); 
                
                for (String platillo : platillos) {
                    contenido.newLineAtOffset(15, -20); // Movemos 15 hacia la derecha (sangría de lista) y 20 hacia abajo
                    contenido.showText("• " + platillo.trim()); // Dibujamos el platillo con una viñeta
                    contenido.newLineAtOffset(-15, 0); // Regresamos la sangría a su posición original para el siguiente ciclo
                }
            
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Subtotal: $" + venta.getSubtotal());
            
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Iva: $" + venta.getIva());
            
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Total: $" + venta.getTotal());
            
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Tipo de pago: " + venta.getTipoPago());
            
                contenido.endText();
            }

            // Guardar el archivo en la ruta especificada
            documento.save(destino);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @FXML
    private void handleGenerarFactura(Ventas ventaAsignada) throws Exception{
        File carpetaDestino = new File(carpetaFacturas);
        
        if (!carpetaDestino.exists()) {
            carpetaDestino.mkdirs(); 
        }
        
        String nombreArchivo = "Factura de venta numero "+ventaAsignada.getIdVenta()+ ".pdf";
        File archivoPdf = new File(carpetaDestino, nombreArchivo);
        
        boolean exito = crearFactura(archivoPdf, ventaAsignada);
        
        if (exito) {
            this.mostrarAlerta("Ticket generado correctamente en: " + archivoPdf.getAbsolutePath());
        } else {
            mostrarAlerta("Error, No se pudo generar el PDF.");
        }
    }
    
    private boolean crearFactura(File destino, Ventas venta) {
        // Si por alguna razón llegó vacío sin lanzar excepción
        if (venta == null) {
            this.mostrarAlerta("Error, No se encontraron datos de la venta para generar el ticket.");
            return false;
        }

        // Ahora que tenemos los datos asegurados, procedemos a construir el PDF
        try (PDDocument documento = new PDDocument()) {
            PDPage pagina = new PDPage();
            documento.addPage(pagina);
            
            try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina)) {
                
                // Iniciamos bloque de texto de forma limpia y corrida
                contenido.beginText();
                contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
                contenido.newLineAtOffset(50, 700);
            
                contenido.showText("Factura de venta numero " + venta.getIdVenta());
            
                // Cuerpo del ticket
                contenido.newLineAtOffset(0, -30);
                contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contenido.showText("Datos de la factura:");
            
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Id de la venta: " + venta.getIdVenta());
            
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Platillos ordenados: ");
            
                // Nota: Si venta.getDescripcion() es muy largo o contiene saltos de línea (\n), 
                // showText fallará. Asegúrate de que sea un texto corto, o límpialo de saltos de línea.
              
                String[] platillos = venta.getDescripcion().split("\n"); 
                
                for (String platillo : platillos) {
                    contenido.newLineAtOffset(15, -20); // Movemos 15 hacia la derecha (sangría de lista) y 20 hacia abajo
                    contenido.showText("• " + platillo.trim()); // Dibujamos el platillo con una viñeta
                    contenido.newLineAtOffset(-15, 0); // Regresamos la sangría a su posición original para el siguiente ciclo
                }
            
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Subtotal: $" + venta.getSubtotal());
            
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Iva: $" + venta.getIva());
            
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Total: $" + venta.getTotal());
            
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Tipo de pago: " + venta.getTipoPago());
                
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Nombre: " + tfgf_nombre.getText());

                contenido.newLineAtOffset(0, -20);
                contenido.showText("RFC: " + tfgf_rfc.getText());
                
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Correo electronico: " + tfgf_correo.getText());
                
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Regimen fiscal: " + tfgf_regimen.getText());
                contenido.endText();
            }

            // Guardar el archivo en la ruta especificada
            documento.save(destino);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @FXML
    public void generarFactura (ActionEvent event) throws Exception{
        if(tfgf_nombre.getText() != null && !tfgf_nombre.getText().isEmpty() && 
        tfgf_rfc.getText() != null && !tfgf_rfc.getText().isEmpty() && 
        tfgf_correo.getText() != null && !tfgf_correo.getText().isEmpty() && 
        tfgf_regimen.getText() != null && !tfgf_regimen.getText().isEmpty()){
            handleGenerarFactura(venta);
            this.mostrarAlerta("La factura se ha generado correctamente en la ruta: "+carpetaFacturas);
        }else{
            this.mostrarAlerta("Todos los campos deben estar llenos");
        }
    }
    
    @FXML
    private void regresarAlMenu1(ActionEvent event){
        //Desactivamos los paneles anteriores
        panelConfirmarPago.setDisable(true);
        panelConfirmarPago.setVisible(false);
        panelConfirmarPago.setManaged(false);
        panelDatosCuenta.setDisable(true);
        panelDatosCuenta.setVisible(false);
        panelDatosCuenta.setManaged(false);
        //Activamos el panel del menu
        panelPedidos.setDisable(false);
        panelPedidos.setVisible(true);
        panelPedidos.setManaged(true);
        //Activamos el apPedidos.
        bloqueo=false;
    }
    
    @FXML
    private void regresarAlMenu2(ActionEvent event){
        //Desactivamos los paneles anteriores
        panelConfirmarPago.setDisable(true);
        panelConfirmarPago.setVisible(false);
        panelConfirmarPago.setManaged(false);
        panelDatosCuenta.setDisable(true);
        panelDatosCuenta.setVisible(false);
        panelDatosCuenta.setManaged(false);
        panelGenerarFactura.setDisable(true);
        panelGenerarFactura.setVisible(false);
        panelGenerarFactura.setManaged(false);
        //Activamos el panel del menu
        panelPedidos.setDisable(false);
        panelPedidos.setVisible(true);
        panelPedidos.setManaged(true);
        //Activamos el apPedidos.
        apPedidos.setDisable(false);
        apPedidos.setVisible(true);
        apPedidos.setManaged(true);
        bloqueo=false;
    }
    
    @FXML
    private void cargarPanelGenerarFactura(ActionEvent event){
        btnConfirmarPago.setDisable(true);
        btnConfirmarPago.setVisible(false);
        btnConfirmarPago.setManaged((false));
        panelGenerarFactura.setDisable(false);
        panelGenerarFactura.setVisible(true);
        panelGenerarFactura.setManaged(true);
        panelGenerarFactura.toFront();
        tfgf_subtotal.setText(String.valueOf(tf1.getText()));
        tfgf_subtotal.setEditable(false);
        tfgf_iva.setText(String.valueOf(tf2.getText()));
        tfgf_iva.setEditable(false);
        tfgf_monto.setText(String.valueOf(tf3.getText()));
        tfgf_monto.setEditable(false);
        this.ocultarPaneles();
    }
            

    /**
     * Metodo para salir de la ventana
     * @param event 
     */
    @FXML
    private void salir(ActionEvent event) {
        // Obtiene la ventana actual y la cierra
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }
    
    
    
    /**
     * Metodo para recuperar y mostrar todos los pedidos existentes en el sistema
     */
    private void cargarPedidos() {
        try {
            //Se obtienen los pedidos desde la BD
            List<Pedido> pedidos = this.dbPedidos.cargarPedidos();

            //Se colocan los pedidos en la tabla
            pedidosTab.setItems(FXCollections.observableArrayList(pedidos));
        //Si ocurre un error se muestra un mensaje en pantalla
        } catch (Exception e) {
            this.mostrarAlerta("ERROR Carga de datos: " + e.getMessage());
        }
    }
    
    
    /**
     * Metodo para recuperar los pedidos por mesa y mostrarlos en la tabla
     * @param pMesa El numero de mesa
     */
    private void cargarPedidosMesa(int pMesa) {
        //Se recuperan los pedidos de la base ded datos y se muestran en la tabla
        try{
            List<Pedido> pedidos = this.dbPedidos.cargarPedidosMesa(pMesa);
            pedidosTab.setItems(FXCollections.observableArrayList(pedidos));
            
        } catch (Exception e) {
            this.mostrarAlerta("ERROR Carga de datos: " + e.getMessage());
    }
    }
    
    /**
     * Metodo para cargar los botones del Menu
     * @throws Exception 
     */
    private void cargarMenuDinamico() throws Exception{
        // Se limpian los contenedores en la ventana
        flowEntradas.getChildren().clear();
        flowPlatillos.getChildren().clear();
        flowBebidas.getChildren().clear();
        flowPostres.getChildren().clear();
        // Se crea un arraylist para recuperar los platillos disponibles en el menu
        ArrayList<ProductoMenu> lProductos = new ArrayList<>();
        lProductos= dbProductoMenu.obtenerProductosMenu();
        // Para cada uno de los platillos se creara un boton
        for(ProductoMenu producto: lProductos){
            Button btnGenerado= new Button(); //Creacion del boton
            btnGenerado.setPrefSize(120, 120); //Tamaño del boton
            btnGenerado.setContentDisplay(ContentDisplay.TOP);//Elegimos la posicion del contenido a mostrar en la parte de arriba del boton
           
        //Asignar el nombre
            btnGenerado.setText(producto.getNombre());
        
        //Asignar el boton al hashmap
            botones.put(btnGenerado.getText(),btnGenerado);
        //Asignar imagen
            try{
                Image img = new Image(getClass().getResourceAsStream(producto.getImagenRuta()));
                ImageView imgView = new ImageView(img);
                imgView.setFitWidth(80); // Ajustar tamaño de imagen
                imgView.setFitHeight(80);
                imgView.setPreserveRatio(true);
                btnGenerado.setGraphic(imgView);
        //En caso de que alguna imagen no se pueda cargar en el menu se avisara antes de cargar la ventana de Pedidos
            } catch (Exception e) {
                this.mostrarAlerta("No se pudo cargar imagen para: " + producto.getNombre());}
            //Listener para detectar los botones del menu que han sido presionados
            btnGenerado.setOnAction(event -> {
            // Recuperamos el nombre del platillo
            platilloSeleccionado = producto.getNombre();
            // En consola se puede comprobar el nombre del platillo que se selecciono
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
     * Metodo para obtener un boton especifico del menu
     * @param platillo Nombre del platillo
     * @return Objeto Button
     */
    private Button obtenerBoton(String platillo){
        if(botones.containsKey(platillo)){
            return botones.get(platillo);
        } else {
            return null;
        }
    }
    /**
     * Metodo para cargar la seccion de "Datos de cuenta"
     */
    private void cargarPanelDatosCuenta(){
        //Escondemos el panel de pedidos
        panelPedidos.setDisable(true);
        panelPedidos.setVisible(false);
        panelPedidos.setManaged(false);
        
        //Mostramos el panel de los datos de la Cuenta y calculamos sus valores
        panelDatosCuenta.setDisable(false);
        panelDatosCuenta.setVisible(true);
        panelDatosCuenta.setManaged(true);
        panelDatosCuenta.toFront();
        v_subtotal=0;
        double iva=0.16;
        double total;
        List<Pedido> pedidosMesa = pedidosTab.getItems();
        for(Pedido pedido:pedidosMesa){
            v_subtotal = v_subtotal+pedido.getSubtotal();
        }
        tf1.setText(String.valueOf(v_subtotal));
        tf1.setEditable(false);
       
        iva=iva*v_subtotal;
        tf2.setText(String.valueOf(iva));
        tf2.setEditable(false);
        total=v_subtotal+iva;
        tf3.setText(String.valueOf(total));
        tf3.setEditable(false);
        panelSeleccionPago.setDisable(false);
        panelSeleccionPago.setVisible(true);
        panelSeleccionPago.setManaged(true);
        panelSeleccionPago.toFront();
    }
    
    /**
     * Metodo para obtener la lista de pedidos que realizo el cliente
     * @return Descripccion de la cuenta
     */
    private String obtenerDescripccionCuenta(){
        String descripcion="";
        List<Pedido> pedidosMesa = pedidosTab.getItems();
        for(Pedido pedido: pedidosMesa){
            descripcion=descripcion+pedido.getProducto()+" x"+pedido.getCantidad()+"\n";
        }
        System.out.println(descripcion);
        return descripcion;
    }
    
    private void limpiarPedidosTab(){
        int v_id;
        List<Pedido> pedidosMesa = pedidosTab.getItems();
        try{
            for(Pedido pedido:pedidosMesa){
                v_id = pedido.getIdPedido();
                dbPedidos.eliminarPedido(v_id);
            }
        }catch(Exception e){
            this.mostrarAlerta("Ha ocurrido un error al eliminar los pedidos");
        }
    }
    
    /**
     * Muestra una alerta de advertencia con un mensaje específico.
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
    
    /**
     * Habilita los botones "btnElaborarFactura" y "btnRegresarAlMenu" despues de confirmar el pago
     */
    private void habilitarBotonesPreFactura(){
        btnElaborarFactura.setDisable(false);
        btnElaborarFactura.setVisible(true);
        btnElaborarFactura.setManaged(true); 
        btnRegresarAlMenu.setDisable(false);
        btnRegresarAlMenu.setVisible(true);
        btnRegresarAlMenu.setManaged(true); 
    }
    
    private void desactivarBotonConfirmarPago(){
        btnConfirmarPago.setDisable(true);
        btnConfirmarPago.setVisible(false);
        btnConfirmarPago.setManaged((false));
    }
    private void ocultarPaneles(){
        
        apPedidos.setDisable(true);
        apPedidos.setVisible(false);
        apPedidos.setManaged((false));
        
        panelPedidos.setDisable(true);
        panelPedidos.setVisible(false);
        panelPedidos.setManaged((false));
        
        panelDatosCuenta.setDisable(true);
        panelDatosCuenta.setVisible(false);
        panelDatosCuenta.setManaged((false));
        
        panelConfirmarPago.setDisable(true);
        panelConfirmarPago.setVisible(false);
        panelConfirmarPago.setManaged((false));
    }
}