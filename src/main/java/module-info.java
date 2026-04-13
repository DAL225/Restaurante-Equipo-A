module com.mycompany.restaurante.equipo.a {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.mycompany.restaurante.equipo.a to javafx.fxml;
    opens Modelo to javafx.base;
    exports com.mycompany.restaurante.equipo.a;
}
