module lk.ijse.cozyrobes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.jdi;
    requires java.desktop;
    requires lombok;


    opens lk.ijse.cozyrobes.controller to javafx.fxml;
    opens lk.ijse.cozyrobes.dto.tm to javafx.base;
    exports lk.ijse.cozyrobes;
}