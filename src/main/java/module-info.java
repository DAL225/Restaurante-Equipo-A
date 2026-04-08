module com.mycompany.restaurante.equipo.a {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.restaurante.equipo.a to javafx.fxml;
    exports com.mycompany.restaurante.equipo.a;
}
