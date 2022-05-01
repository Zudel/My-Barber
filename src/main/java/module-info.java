module it.barbergroup {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires mysql.connector.java;
    requires jsr305;
    requires java.mail;


    exports first_view;
    exports first_view.pickers;
    exports first_view.general;
    exports first_view.barber;
    exports first_view.client;
    opens first_view to javafx.fxml;
    opens first_view.client to javafx.fxml;
    opens first_view.barber to javafx.fxml;
    opens first_view.general to javafx.fxml;
    opens first_view.pickers to javafx.fxml;



    opens second_view to javafx.fxml;
    exports second_view;
    opens second_view.general to javafx.fxml;
    opens second_view.barber to javafx.fxml;
    opens second_view.client to javafx.fxml;
    exports application_controller;
    opens application_controller to javafx.fxml;

    exports start to javafx.graphics ;
}