package com.mycompany.restaurante.equipo.a;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Modelo.Impl.MesasDAOImpl;

/**
 * Controlador de la vista de gestión de mesas.
 * 
 * Permite agregar mesas, modificar su estado, consultar la lista de mesas
 * y regresar a la pantalla de inicio de sesión.
 */
public class GestionMesasController {

    private MesasDAOImpl mesasDAO;
    //Componentes del FXML
    @FXML private AnchorPane root;
    @FXML private AnchorPane panelFormulario;

    @FXML private Label lblMesas;
    @FXML private Label lblidMesa;
    @FXML private Label Personas;
    @FXML private Label Estado;

    @FXML private Spinner<Integer> NumMesa;
    @FXML private Spinner<Integer> Numpersonas;
    @FXML private ChoiceBox<String> selecEstado;

    @FXML private Button btnAgregarMesa;
    @FXML private Button btnVerLista;
    @FXML private Button btnModificar;
    @FXML private Button btnSalir;

    // Inicialización (opcional pero útil)
     /**
     * Inicializa los componentes de la interfaz y la conexión con la base de datos.
     * Configura los Spinners y carga los estados disponibles para una mesa.
     */
    @FXML
    public void initialize() {
       try{
           mesasDAO = new MesasDAOImpl();
           SpinnerValueFactory<Integer> valueFactoryMesa = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        NumMesa.setValueFactory(valueFactoryMesa);

    // 2. Configurar Spinner de Personas (Min: 1, Max: 20, Inicial: 2)
        SpinnerValueFactory<Integer> valueFactoryPers = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 2);
        Numpersonas.setValueFactory(valueFactoryPers);
        selecEstado.getItems().addAll("Disponible", "Ocupada", "Reservada");
       } catch(Exception e){
           mostrarAlerta("Error de Conexión", "No se pudo conectar con la base de datos.", Alert.AlertType.ERROR);
            e.printStackTrace();
            
       }
    
       


        // Escuchar cambios en el selector de estado
        selecEstado.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.equals("Disponible")) {
                // Opcional: resetear a un valor por defecto si se desactiva
                Numpersonas.getValueFactory().setValue(1); 
            } else {
            }
    
            });
            }

    /**
     * Agrega una nueva mesa usando la cantidad de personas seleccionada.
     * Muestra una alerta indicando si la operación fue exitosa o si ocurrió un error.
     */
    @FXML
    private void agregarMesa() {
       try {
            int personas = Numpersonas.getValue();
            boolean exito = mesasDAO.agregarMesa(personas);
            
            if (exito) {
                mostrarAlerta("Éxito", "Mesa añadida correctamente", Alert.AlertType.INFORMATION);
                // Opcional: refrescar el límite del Spinner NumMesa aquí
            }
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
        System.out.println("Agregar mesa");
    }
    /**
     * Agrega una nueva mesa usando la cantidad de personas seleccionada.
     * Muestra una alerta indicando si la operación fue exitosa o si ocurrió un error.
     */
    @FXML
    private void verListaMesas() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/mycompany/restaurante/equipo/a/ListaMesas.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("ListaMesas");
            stage.setScene(new Scene(root));
            stage.show();

            System.out.println("Ver lista de mesas");

// Cambia esto: catch (IOException e)
// Por esto:
} catch (Exception e) {
  
}
        
    }
    /**
     * Modifica el estado de una mesa existente.
     * Valida que se haya seleccionado un estado antes de actualizar.
     */
    @FXML
    private void modificarMesa() {
    // 1. Obtener valores de la interfaz
    Integer idSeleccionado = NumMesa.getValue();
    String estadoSeleccionado = selecEstado.getValue();
    Integer cantidadPersonas = Numpersonas.getValue();

    // 2. Validar que se haya seleccionado un estado
    if (estadoSeleccionado == null) {
        mostrarAlerta("Atención", "Por favor, selecciona un estado (Disponible, Ocupada o Reservada).", Alert.AlertType.WARNING);
        return;
    }

    try {
        // 3. Ejecutar la actualización mediante el DAO
        boolean exito = mesasDAO.actualizarEstadoMesa(idSeleccionado, estadoSeleccionado, cantidadPersonas);

        if (exito) {
            mostrarAlerta("Éxito", "La mesa " + idSeleccionado + " ahora está " + estadoSeleccionado, Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "La mesa numero "+ idSeleccionado +" no existe", Alert.AlertType.ERROR);
        }

    } catch (Exception e) {
        mostrarAlerta("Error de Base de Datos", e.getMessage(), Alert.AlertType.ERROR);
        e.printStackTrace();
    }
    System.out.println("Modificar mesa");
    }
    

     /**
     * Regresa a la pantalla de inicio de sesión.
     *
     * @throws IOException si ocurre un error al cargar la vista.
     */
    @FXML
    private void salir() throws IOException {
        System.out.println("Saliendo...");
        volverMenu();
        }
    /**
     * Cambia la escena actual al menú o pantalla de inicio de sesión.
     */
    private void volverMenu() {
        try {///////////
            FXMLLoader loader = new FXMLLoader(getClass().getResource( "/com/mycompany/restaurante/equipo/a/InicioSesion.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.setScene(new Scene (root));

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista", Alert.AlertType.ERROR);
        }
    }

    /**
     * Muestra una alerta en pantalla.
     *
     * @param titulo título de la alerta
     * @param mensaje mensaje que se mostrará
     * @param tipo tipo de alerta: información, error, advertencia, etc.
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null); // Esto quita el encabezado gris extra
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
     
}

