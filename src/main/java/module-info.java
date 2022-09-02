module com.example.javafxmavenobs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires json.simple;


    opens com.example.javafxmavenobs to javafx.fxml;
    exports com.example.javafxmavenobs;
}