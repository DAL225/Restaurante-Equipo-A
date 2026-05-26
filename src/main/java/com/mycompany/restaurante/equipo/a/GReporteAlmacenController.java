package com.mycompany.restaurante.equipo.a;

import Modelo.Dao.ProductoAlmacenDAO;
import Modelo.Dao.RegistroReporteAlmacenDAO;
import Modelo.Impl.ProductoAlmacenDAOImpl;
import Modelo.Impl.RegistroReporteAlmacenDAOImpl;
import Modelo.ProductoAlmacen;
import Modelo.RegistroReporteAlmacen;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class GReporteAlmacenController implements Initializable {

    @FXML
    private ChoiceBox<LocalDate> selecMes;

    @FXML
    private Button btnGenerarReporte;
    @FXML
    private Button btnCerrar;

    private ProductoAlmacenDAO almacenDao;
    private RegistroReporteAlmacenDAO registroAlmacenDao;
    

    // Este método se ejecuta automáticamente al cargar la vista FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicializar();
    }

    @FXML
    private void generarReporte(ActionEvent event) {

    ArrayList<RegistroReporteAlmacen> reportes = obtenerHistorialRegistrosMes();

    // Generar gráficas
    ArrayList<WritableImage> imagenesGraficas = crearGraficaStockActual();
    
    if ((reportes == null || reportes.isEmpty())
            && (imagenesGraficas == null || imagenesGraficas.isEmpty())){
        this.mostrarAlerta("Error", "No existe información registrada para el mes seleccionado", Alert.AlertType.INFORMATION);
        return;
    }

    try (PDDocument documento = new PDDocument()) {

        PDPage pagina = new PDPage();
        documento.addPage(pagina);

        PDPageContentStream contenido = new PDPageContentStream(documento, pagina);

        float margenIzquierdo = 50;
        float posicionY = 750;
        float espacioLinea = 20;

        // ====================================
        // TÍTULO
        // ====================================
        contenido.beginText();
        contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
        contenido.newLineAtOffset(margenIzquierdo, posicionY);
        contenido.showText("REPORTE ALMACEN - " + LocalDate.now());
        contenido.endText();

        posicionY -= 40;

        // ====================================
        // CONTENIDO DE TEXTO
        // ====================================
        if (reportes == null || reportes.isEmpty()){
            contenido.beginText();
            contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contenido.newLineAtOffset(margenIzquierdo, posicionY);
            contenido.showText("NO EXISTE HISTORIAL DE MOVIMIENTOS PARA ESTE MES");
            contenido.endText();
        } else {
            contenido.beginText();
            contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contenido.newLineAtOffset(margenIzquierdo, posicionY);

            for (RegistroReporteAlmacen reporte : reportes) {

                // Cada reporte ocupará 2 líneas escritas, validamos espacio suficiente (mínimo 60 unidades)
                if (posicionY <= 80) {
                    contenido.endText();
                    contenido.close();

                    pagina = new PDPage();
                    documento.addPage(pagina);

                    contenido = new PDPageContentStream(documento, pagina);
                    posicionY = 750;

                    contenido.beginText();
                    contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                    contenido.newLineAtOffset(margenIzquierdo, posicionY);
                }

                // Línea 1: Datos generales
                String linea1 = "IDProducto: " + reporte.getIdproducto()
                        + " | Fecha: " + reporte.getFecha()
                        + " | Movimiento: " + reporte.getTipo()
                        + " | Cantidad: " + reporte.getCantidadproducto();
                contenido.showText(linea1);
                
                // Salto manual a la Línea 2
                contenido.newLineAtOffset(0, -espacioLinea);
                posicionY -= espacioLinea;

                // Línea 2: Marca del producto
                String linea2 = " | Marca: " + reporte.getMarca();
                contenido.showText(linea2);

                // Salto de separación para el siguiente registro
                contenido.newLineAtOffset(0, -espacioLinea);
                posicionY -= espacioLinea;
            }
            contenido.endText();
        }
        
        // Cerramos el stream de la última página de texto usada
        contenido.close();

        // ====================================
        // INSERTAR GRÁFICAS
        // ====================================
        if (imagenesGraficas == null || imagenesGraficas.isEmpty()) {
            // Si no hay gráficas, creamos una página de aviso
            pagina = new PDPage();
            documento.addPage(pagina);

            try (PDPageContentStream contenidoGrafica = new PDPageContentStream(documento, pagina)) {
                contenidoGrafica.beginText();
                contenidoGrafica.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
                
                // Lo posicionamos en una zona central de la página
                contenidoGrafica.newLineAtOffset(margenIzquierdo, 500); 
                contenidoGrafica.showText("NO EXISTEN GRÁFICAS DISPONIBLES PARA ESTE PERIODO");
                contenidoGrafica.endText();
            }
        } else {
            // Si sí hay gráficas, las recorremos e insertamos una por página
            for (WritableImage imagenFX : imagenesGraficas) {

                pagina = new PDPage();
                documento.addPage(pagina);

                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imagenFX, null);
                File archivoTemporal = File.createTempFile("grafica", ".png");

                ImageIO.write(bufferedImage, "png", archivoTemporal);

                PDImageXObject imagenPDF = PDImageXObject.createFromFile(archivoTemporal.getAbsolutePath(), documento);

                // Usamos un bloque try-with-resources aquí también para asegurar el cierre del stream
                try (PDPageContentStream contenidoGrafica = new PDPageContentStream(documento, pagina)) {
                    // Dibujar la gráfica en su respectiva página
                    contenidoGrafica.drawImage(imagenPDF, 40, 250, 520, 300);
                }

                archivoTemporal.delete();
            }
        }

        // ====================================
        // GUARDAR PDF
        // ====================================
        this.elegirGuardadoPdf(documento);

        mostrarAlerta("Éxito", "PDF generado correctamente en Descargas", Alert.AlertType.INFORMATION);

    } catch (IOException e) {
        e.printStackTrace();
        mostrarAlerta("Error", "No se pudo generar el PDF de forma correcta", Alert.AlertType.ERROR);
    }
}

    @FXML
    private void cerrar(ActionEvent event) {
        // Obtiene el Stage actual y lo cierra
        Stage stage = (Stage) this.btnCerrar.getScene().getWindow();
        stage.close();
    }

    private void inicializar() {

        selecMes.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter formateador = DateTimeFormatter.ofPattern("MM/yyyy");

            //Convierte la fecha en el String requerido
            @Override
            public String toString(LocalDate f) {
                return f != null ? f.format(formateador) : "";
            }

            //Regresa la fecha en LocalDate
            @Override
            public LocalDate fromString(String s) {
                return s != null ? LocalDate.parse("01/" + s, DateTimeFormatter.ofPattern("d/MM/yyyy")) : null;
            }
        });

        LocalDate mesActual = LocalDate.now().withDayOfMonth(1);
        // Aseguramos que la fecha de inicio empiece en el día 1 para comparar meses limpios
        //* Esta es la fecha en que permitimos se seleccione el reporte
        LocalDate iterador = LocalDate.of(2026, 1, 1).withDayOfMonth(1);

        // Bucle para ir agregando los meses desde el actual hacia atrás hasta llegar al límite
        while (!mesActual.isBefore(iterador)) {
            selecMes.getItems().add(mesActual);
            mesActual = mesActual.minusMonths(1); // Retrocede un mes
        }

        // Seleccionar el mes actual por defecto
        selecMes.getSelectionModel().selectFirst();
    }

    private ArrayList<WritableImage> crearGraficaStockActual() {
        ArrayList<WritableImage> imagenesGraficas = new ArrayList();

        ArrayList<ProductoAlmacen> lista = obtenerStockActual();
        

        if (lista == null || lista.isEmpty()) {
            return imagenesGraficas;
        }

        // Cantidad de gráficas necesarias
        int numGraficas = (lista.size() + 9) / 10;

        for (int numGrafica = 0; numGrafica < numGraficas; numGrafica++) {

            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();

            BarChart<String, Number> barChart
                    = new BarChart<>(xAxis, yAxis);

            xAxis.setLabel("Productos");
            yAxis.setLabel("Stock");

            XYChart.Series<String, Number> series
                    = new XYChart.Series<>();

            // Inicio y fin de productos para esta gráfica
            int inicio = numGrafica * 10;
            int fin = Math.min(inicio + 10, lista.size());

            for (int i = inicio; i < fin; i++) {

                ProductoAlmacen prodAux = lista.get(i);

                XYChart.Data<String, Number> data
                        = new XYChart.Data<>(
                                "ID: " + prodAux.getId()
                                + "\n" + prodAux.getMarca()
                                + "\n" + "Stock: " + prodAux.getStock(),
                                prodAux.getStock()
                        );

                series.getData().add(data);
            }

            barChart.getData().add(series);

            barChart.setPrefSize(800, 600);
            new Scene(new StackPane(barChart));
            barChart.applyCss();
            barChart.layout();

            // Guardar imagen
            imagenesGraficas.add(
                    barChart.snapshot(
                            new SnapshotParameters(),
                            null
                    )
            );
        }
        return imagenesGraficas;
    }

    private ArrayList<RegistroReporteAlmacen> obtenerHistorialRegistrosMes() {
        ArrayList<RegistroReporteAlmacen> lista = new ArrayList();
        try {
            registroAlmacenDao = new RegistroReporteAlmacenDAOImpl();

            lista = registroAlmacenDao.obtenerRegistrosMes(selecMes.getValue());
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar los datos de registros de reportes", Alert.AlertType.ERROR);
        }

        return lista;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null); // Esto quita el encabezado gris extra
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private ArrayList<ProductoAlmacen> obtenerStockActual() {
        ArrayList<ProductoAlmacen> lista = new ArrayList();
        try {
            almacenDao = new ProductoAlmacenDAOImpl();

            lista = almacenDao.obtenerProductosAlmacen();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar los datos de stock actual", Alert.AlertType.ERROR);
        }

        return lista;
    }
    
    private void elegirGuardadoPdf(PDDocument documento) throws IOException {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Guardar reporte");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "PDF Files",
                        "*.pdf"
                )
        );

        File archivo = fileChooser.showSaveDialog(
                btnGenerarReporte.getScene().getWindow()
        );

        if (archivo != null) {
            documento.save(archivo);
        }
    }
}
