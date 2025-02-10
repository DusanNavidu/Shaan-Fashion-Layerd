module lk.ijse.gdse72.shaan_fashion_layerd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.jfoenix;
    requires java.sql;
    requires static lombok;
    requires java.mail;

    opens lk.ijse.gdse72.shaan_fashion_layerd.controller to javafx.fxml;
    opens lk.ijse.gdse72.shaan_fashion_layerd to javafx.fxml;
    opens lk.ijse.gdse72.shaan_fashion_layerd.view.tdm to javafx.base;

    exports lk.ijse.gdse72.shaan_fashion_layerd.controller;
    exports lk.ijse.gdse72.shaan_fashion_layerd;
}
