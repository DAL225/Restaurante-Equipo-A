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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SpinnerValueFactory;

public class ClienteCancelaController  implements Initializable {

    @FXML
    private Button BotonCancelarReserva;
    @FXML
    private DatePicker EntradaFechaCR;
    @FXML
    private TextField EntradaNombreCR;
    @FXML
    private Button BotonSalir;
    @FXML
    private Spinner<Integer> EntradaNumPersonasCR;
    @FXML
    private Spinner<Integer> EntradaMinCR;
    @FXML
    private Spinner<Integer> EntradaHoraCR;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int PersonasMin = 1; //Acá habría que cambiarlo según la mesa
        int PersonasMax = 100; //Acá habría que cambiarlo según la mesa
        SpinnerValueFactory<Integer> valueFactoryPersonas = new SpinnerValueFactory.IntegerSpinnerValueFactory(PersonasMin, PersonasMax, 1);
        EntradaNumPersonasCR.setValueFactory(valueFactoryPersonas);
        SpinnerValueFactory<Integer> valueFactoryMin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 1);
        EntradaMinCR.setValueFactory(valueFactoryMin);
        SpinnerValueFactory<Integer> valueFactoryHora = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 19, 1);
        EntradaHoraCR.setValueFactory(valueFactoryHora);
    }
    
    @FXML
    private void CancelarReserva(ActionEvent event){
        if (EntradaFechaCR.getValue() == null | EntradaNombreCR.getText() == null){
            String camposVacíos = "Por favor llene todos los campos";
            this.mostrarAlerta(camposVacíos);
        }
        try {
            ReservacionesDAOImpl DAO = new ReservacionesDAOImpl();
            int NumPersonasR = EntradaNumPersonasCR.getValue();
            LocalDate FechaR = EntradaFechaCR.getValue();
            int MinR = EntradaMinCR.getValue();
            int HoraR = EntradaHoraCR.getValue();
            String NombreR = EntradaNombreCR.getText();
        
            LocalTime horaMinuto = LocalTime.of(HoraR, MinR);
            Time horaAUX = Time.valueOf(horaMinuto);
            Date fechaAUX = Date.valueOf(FechaR);
        
            int mesa = DAO.obtenerMesaEspecifica(NumPersonasR, fechaAUX, horaAUX, NombreR);

            if(DAO.reservacionExiste(mesa,NumPersonasR,fechaAUX,horaAUX,NombreR)){
                String confirmación = "Cancelar la reservación del "+FechaR+" a la "+HoraR+" con "+MinR+"?";
                Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION, confirmación);
                confirmar.showAndWait();
                if (confirmar.getResult() == ButtonType.OK){
                    if(DAO.eliminarReservacion(mesa, NumPersonasR, fechaAUX, horaAUX, NombreR)){
                        this.mostrarAlerta("Reservación cancelada exitosamente");
                    } else {
                        this.mostrarAlerta("Hubo un problema al cancelar la reservación");
                    }
                }
            } else {
                this.mostrarAlerta("La reservación no existe");
            }
        } catch (Exception ex) {
            Logger.getLogger(ClienteReservarController.class.getName()).log(Level.SEVERE, null, ex);
            this.mostrarAlerta("Hubo un problema al obtener la reservación");
        }
        EntradaFechaCR.setValue(null);
        EntradaNumPersonasCR.getValueFactory().setValue(1);
        EntradaMinCR.getValueFactory().setValue(0);
        EntradaHoraCR.getValueFactory().setValue(8);
        EntradaNombreCR.setText(null);
    }

    @FXML
    private void Salir(ActionEvent event) throws IOException {
        App.setRoot("ClienteReservar");
    }
    
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("AVISO");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
