
package com.mycompany.restaurante.equipo.a;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
    private TextField EntradaNombreR;
    @FXML
    private Button BotonSalir;
    @FXML
    private Spinner<Integer> EntradaNumPersonasR;
    @FXML
    private Spinner<Integer> EntradaMinR;
    @FXML
    private Spinner<Integer> EntradaHoraR;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int PersonasMin = 1; //Acá habría que cambiarlo según la mesa
        int PersonasMax = 100; //Acá habría que cambiarlo según la mesa
        SpinnerValueFactory<Integer> valueFactoryPersonas = new SpinnerValueFactory.IntegerSpinnerValueFactory(PersonasMin, PersonasMax, 1);
        EntradaNumPersonasR.setValueFactory(valueFactoryPersonas);
        SpinnerValueFactory<Integer> valueFactoryMin = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, 1);
        EntradaMinR.setValueFactory(valueFactoryMin);
        SpinnerValueFactory<Integer> valueFactoryHora = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, 1);
        EntradaHoraR.setValueFactory(valueFactoryHora);
    }

    @FXML
    private void ReservarMesa(ActionEvent event) {
        System.out.println(EntradaNumPersonasR.getValue());
        System.out.println(EntradaFechaR.getValue());
        System.out.println(EntradaMinR.getValue());
        System.out.println(EntradaHoraR.getValue());
        System.out.println(EntradaNombreR.getText());
    }

    @FXML
    private void Salir(ActionEvent event) throws IOException {
        App.setRoot("InicioSesión");
    }
}