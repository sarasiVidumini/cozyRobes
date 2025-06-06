module lk.ijse.cozyrobes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.jdi;
    requires lombok;
    requires jdk.unsupported.desktop;
    requires java.mail;
    requires activation;
    requires net.sf.jasperreports.core;


    opens lk.ijse.cozyrobes.controller to javafx.fxml;
    opens lk.ijse.cozyrobes.dto.tm to javafx.base;
    exports lk.ijse.cozyrobes;
}