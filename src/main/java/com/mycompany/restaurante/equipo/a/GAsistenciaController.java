package com.mycompany.restaurante.equipo.a;

import Modelo.Asistencia;
import Modelo.Dao.AsistenciaDAO;
import Modelo.Impl.AsistenciaDAOImpl;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class GAsistenciaController {

    @FXML private TableView<Asistencia> tblAsistencia;
    @FXML private TableColumn<Asistencia, Integer> colId;
    @FXML private TableColumn<Asistencia, String> colUsuario;

    @FXML private Spinner<Integer> spnYear;
    @FXML private Spinner<Integer> spnMes;

    @FXML private TextField txtHoy;
    @FXML private TextField txtMes;
    
    @FXML private Button btnSalir;

    private LocalDate fechaHoy;
    private AsistenciaDAO asistenciaDao;

    @FXML
    public void initialize() {

        // Inicializar DAO
        try{
            asistenciaDao = new AsistenciaDAOImpl();
        }catch(Exception e){
            
        }
        

        // Columnas fijas
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));

        // Spinners
        spnYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2020, 2030, 2026));
        spnMes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 5));

        // Tabla
        tblAsistencia.setEditable(true);
        tblAsistencia.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        // Fecha actual
        fechaHoy = LocalDate.now(); 
        txtHoy.setText(fechaHoy.toString());
        txtMes.setText(fechaHoy.getMonthValue() + "/" + fechaHoy.getYear());
        

        configurarTabla(fechaHoy.getYear(), fechaHoy.getMonthValue());
        cargarDatos(fechaHoy.getYear(), fechaHoy.getMonthValue());
        bloquearMarcado();
    }

    /**
     * Configura la tabla, carga los datos al mes seleccionado
     */
    @FXML
    private void buscarMes() {
        int year = spnYear.getValue();
        int mes = spnMes.getValue();

        txtMes.setText(mes + "/" + year);

        configurarTabla(year, mes);
        cargarDatos(year, mes);
        bloquearMarcado();
    }

    /**
     * Guarda la asistencia marcada en la columna el dia hoy
     */
    @FXML
    private void guardarAsistencia() throws Exception {
        // Sila asistencia ya fue registrada
        if(asistenciaDao.diaRegistrado(fechaHoy)){
            mostrarAlerta("Accion innecesaria", "La asistencia para la fecha: "+ fechaHoy.toString() + ", ya fue registrada", Alert.AlertType.INFORMATION);
            return;
        }

        //Confirmacion
        boolean confirmar = mostrarConfirmacion(
                "Confirmación",
                "¿Está seguro de GUARDAR LA ASISTENCIA?"
        );

        // Si la opcion selecionada es "no", sale del metodo
        if (!confirmar){
            return;}

        //Genera una lista con cada elemento de la tabla
        ObservableList<Asistencia> lista = tblAsistencia.getItems();

        //Bandera de exito en todo el proceso de guardado
        boolean exitoBandera = true;

        // Por cada elemto en la lista es decir cada usuario
        for (Asistencia fila : lista) {

            //Obtiene el dia de hoy y rn el usuario de la lista si asistio o no
            int diaHoy = fechaHoy.getDayOfMonth();
            BooleanProperty asistio = fila.getRegistroMensual().get(diaHoy);

            //Veifica asistio sea distinto de null, lo cual es muy poco probable
            if (asistio != null) {
                try {
                    //Guarda la asistencia en la BD
                    boolean guardadoCorrecto = asistenciaDao.guardarAsistencia(fila, fechaHoy);

                    //Si el guardado no fue correcto, la bandera queda en false
                    if (!guardadoCorrecto) exitoBandera = false;

                } catch (Exception e) {
                    e.printStackTrace();
                    //Si ocurre alguna excepcion, la bandera queda en false
                    exitoBandera = false;
                }
            }
        }

        //Si la bandera es true, carga los datos y muestra guardado exitoso
        if (exitoBandera) {
            cargarDatos(fechaHoy.getYear(), fechaHoy.getMonthValue());
            bloquearMarcado();
            mostrarAlerta("Éxito", "Asistencia guardada correctamente", Alert.AlertType.INFORMATION);
        } else {
            //Si no avisa de error
            mostrarAlerta("Error", "Algunos registros fallaron", Alert.AlertType.ERROR);
        }
    }

    /**
     * Configura la tabla con las columnas necesarias de acuerdo a los dias del mes y anio seleccionado
     * @param year anio a configurar
     * @param mes mes a configurar
     */
    private void configurarTabla(int year, int mes) {

        // Limpia todas las columnas de dia, dejando solo las de los datos de usuarios
        tblAsistencia.getColumns().removeIf(col ->
                !col.getText().equals("Id") && !col.getText().equals("Usuario"));

        //Calcula el numero de dias/columnas necesarios
        int dias = YearMonth.of(year, mes).lengthOfMonth();

        //Por cada dia
        for (int i = 1; i <= dias; i++) {

            final int dia = i;

            //Crea una nueva columna con el dia como titulo
            TableColumn<Asistencia, Boolean> col = new TableColumn<>(String.valueOf(dia));
            col.setPrefWidth(35);
            col.setStyle("-fx-alignment: CENTER;");

            //Establece una propiedad boolean para cada celda de esa columna
            col.setCellValueFactory(data ->
                    data.getValue().insertarDiaProperty(dia)
            );
            //Establece un CheckBox para cada celda de la columna
            col.setCellFactory(CheckBoxTableCell.forTableColumn(col));

            //Agrega la columna a la tabla
            tblAsistencia.getColumns().add(col);
        }
    }

    private void cargarDatos(int year, int mes) {
        //Verifica si existe algun empleado que no ha sido bloqueada su asistencia el dia hoy
        try {
            if (!asistenciaDao.diaRegistrado(fechaHoy)) {
                //Genera asistencia por defecto a los empleados no han sido bloqueados
                asistenciaDao.generarAsistenciaDefecto(fechaHoy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {//Obtiene la lista de asistencias del mes y anio seleccionados
            ObservableList<Asistencia> lista = FXCollections.observableArrayList(
                    asistenciaDao.obtenerAsistencias(year, mes)
            );

            //Si la lista esta vacia muestra un aviso y limpia la tabla
            if (lista.isEmpty()) {
                mostrarAlerta("Vacío", "No hay datos", Alert.AlertType.INFORMATION);
                tblAsistencia.getItems().clear();
                return;
            }
            //Carga los elementos de la lista a la tabla
            tblAsistencia.setItems(lista);

        } catch (Exception e) {
            //Muestra error si ocurre alguna excepcion
            mostrarAlerta("Error", "Error al cargar datos", Alert.AlertType.ERROR);
        }
    }

    /**
     * Bloque el marcado en dias que no son hoy y si hoy ya fue registrado tambien
     */
    private void bloquearMarcado() {
        try {//Por cada columna en la tabla
            for (TableColumn<Asistencia, ?> columna : tblAsistencia.getColumns()) {
                //Si el titulo de la columna no es igual al dia de hoy
                if (!columna.getText().equals(String.valueOf(fechaHoy.getDayOfMonth()))) {
                    //La edicion no esta disponible para esa columna
                    columna.setEditable(false);
                }
            }
            //Verifica en la BD ya ha sido bloqueada la asistencia en cada empleado para el dia de hoy
            if (asistenciaDao.diaRegistrado(fechaHoy)) {
                //La edicion no esta disponible para esa columna(hoy)
                tblAsistencia.getColumns().get(fechaHoy.getDayOfMonth() + 1).setEditable(false);
            }
        } catch (Exception e) {
            //Muestra error si ocurre alguna excepcion
            this.mostrarAlerta("Error", "Error de bloqueo", Alert.AlertType.INFORMATION);
        }
    }

    /**
 * Muestra una ventana de alerta con un mensaje al usuario.
 *
 * @param t    título de la ventana
 * @param m    mensaje que se mostrará
 * @param tipo tipo de alerta (INFORMATION, ERROR, WARNING, etc.)
 */
private void mostrarAlerta(String t, String m, Alert.AlertType tipo) {
    Alert a = new Alert(tipo);
    a.setTitle(t);
    a.setHeaderText(null);
    a.setContentText(m);
    a.showAndWait();
}

    /**
     * Muestra una ventana de confirmación con opciones (OK / Cancelar).
     *
     * @param titulo título de la ventana
     * @param mensaje mensaje de confirmación
     * @return true si el usuario presiona OK, false en caso contrario
     */
    private boolean mostrarConfirmacion(String titulo, String mensaje) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);

        Optional<ButtonType> r = a.showAndWait();
        return r.isPresent() && r.get() == ButtonType.OK;
    }

    /**
     * Cierra la stage de asistencia
     */
    @FXML
    private void salir() {
        // Obtiene el Stage actual y lo cierra
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
    }
}
