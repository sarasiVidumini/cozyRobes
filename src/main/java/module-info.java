module lk.ijse.cozyrobes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.jdi;
    requires java.desktop;
    requires static lombok;


    opens lk.ijse.cozyrobes.controller to javafx.fxml;
    exports lk.ijse.cozyrobes;
}