module com.example.paoim_lab6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    opens com.example.paoim_lab6 to javafx.fxml;
    exports com.example.paoim_lab6;
}