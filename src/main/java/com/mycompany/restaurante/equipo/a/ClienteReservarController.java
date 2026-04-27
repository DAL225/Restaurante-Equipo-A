
package com.mycompany.restaurante.equipo.a;

import Modelo.Impl.ReservacionesDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
        SpinnerValueFactory<Integer> valueFactoryMin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 1);
        EntradaMinR.setValueFactory(valueFactoryMin);
        SpinnerValueFactory<Integer> valueFactoryHora = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 19, 1);
        EntradaHoraR.setValueFactory(valueFactoryHora);
    }

    @FXML
    private void ReservarMesa(ActionEvent event) {
        if (EntradaFechaR.getValue() == null | EntradaNombreR.getText() == null){
            String camposVacíos = "Por favor llene todos los campos";
            this.mostrarAlerta(camposVacíos);
        } else {
            if (this.verificarDatos(EntradaFechaR.getValue())){
                int NumPersonasR = EntradaNumPersonasR.getValue();
                LocalDate FechaR = EntradaFechaR.getValue();
                int MinR = EntradaMinR.getValue();
                int HoraR = EntradaHoraR.getValue();
                String NombreR = EntradaNombreR.getText();
                String confirmación = "Reservar la mesa el "+FechaR+" a la "+HoraR+" con "+MinR+"?";
                Alert confirmar = new Alert(AlertType.CONFIRMATION, confirmación);
                confirmar.showAndWait();
                if (confirmar.getResult() == ButtonType.OK){
                    LocalTime horaMinuto = LocalTime.of(HoraR, MinR);
                    Time horaAUX = Time.valueOf(horaMinuto);
                    Date fechaAUX = Date.valueOf(FechaR);
                    try {
                        ReservacionesDAOImpl DAO = new ReservacionesDAOImpl();
                        if(DAO.reservarMesa(NumPersonasR, fechaAUX, horaAUX, NombreR)){
                            this.mostrarAlerta("Mesa reservada exitosamente");
                        } else {
                            this.mostrarAlerta("No hay mesas disponibles para reservar en esa fecha y hora");
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(ClienteReservarController.class.getName()).log(Level.SEVERE, null, ex);
                        this.mostrarAlerta("Hubo un problema al reservar la mesa");
                    }                
                } else {
                    this.mostrarAlerta("No se reservó la mesa");
                }
            }
        }
        EntradaFechaR.setValue(null);
        EntradaNumPersonasR.getValueFactory().setValue(1);
        EntradaMinR.getValueFactory().setValue(0);
        EntradaHoraR.getValueFactory().setValue(8);
        EntradaNombreR.setText(null);
    }

    @FXML
    private void Salir(ActionEvent event) throws IOException {
        App.setRoot("Inicio");
    }
    
    private boolean verificarDatos(LocalDate fecha) {
        LocalDate fechaActual = LocalDate.now();
        if (fecha.isAfter(fechaActual)){
            return true;
        } else {
            this.mostrarAlerta("La fecha de reservación debe ser posterior a la fecha de reserva");
            return false;
        }
    }
    
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("AVISO");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}