
package com.mycompany.restaurante.equipo.a;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SpinnerValueFactory;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author aburt
 */
public class ClienteReservarController implements Initializable {


    @FXML
    private Button BotonReservar;
    @FXML
    private DatePicker EntradaFechaR;
    @FXML
    private TextField SalidaEstadoMesa;
    @FXML
    private TextField EntradaNombreR;
    @FXML
    private Button BotonSalir;
    @FXML
    private Spinner<Integer> EntradaNumMesaR;
    @FXML
    private Spinner<Integer> EntradaNumPersonasR;
    @FXML
    private Spinner<Integer> EntradaMinR;
    @FXML
    private Spinner<Integer> EntradaHoraR;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int MesaMin = 1;
        int MesaMax = 10; //Acá habrá que traer directamente el número máximo de mesas existentes
        int PersonasMin = 1; //Acá habría que cambiarlo según la mesa
        int PersonasMax = 10; //Acá habría que cambiarlo según la mesa
        SpinnerValueFactory<Integer> valueFactoryMesa = new SpinnerValueFactory.IntegerSpinnerValueFactory(MesaMin, MesaMax, 1);
        EntradaNumMesaR.setValueFactory(valueFactoryMesa);
        SpinnerValueFactory<Integer> valueFactoryPersonas = new SpinnerValueFactory.IntegerSpinnerValueFactory(PersonasMin, PersonasMax, 1);
        EntradaNumPersonasR.setValueFactory(valueFactoryPersonas);
        SpinnerValueFactory<Integer> valueFactoryMin = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, 1);
        EntradaMinR.setValueFactory(valueFactoryMin);
        SpinnerValueFactory<Integer> valueFactoryHora = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, 1);
        EntradaHoraR.setValueFactory(valueFactoryHora);
    }

    @FXML
    private void ReservarMesa(ActionEvent event) {
    }

    @FXML
    private void Salir(ActionEvent event) {
    }
}