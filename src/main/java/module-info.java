module com.mycompany.restaurante.equipo.a {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires jbcrypt;
    requires org.apache.pdfbox;
    requires javafx.swing;
    requires java.desktop;

    opens com.mycompany.restaurante.equipo.a to javafx.fxml;
    opens Modelo to javafx.base;
    exports com.mycompany.restaurante.equipo.a;
    
    
}
