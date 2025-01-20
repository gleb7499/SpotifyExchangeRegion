module org.example.spotifyexchangeregion {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.seleniumhq.selenium.api;
    requires org.jetbrains.annotations;
    requires org.seleniumhq.selenium.chrome_driver;
    requires org.seleniumhq.selenium.support;
    requires dev.failsafe.core;

    opens org.example.spotifyexchangeregion.main to javafx.fxml;
    exports org.example.spotifyexchangeregion.main;
    exports org.example.spotifyexchangeregion.models;
}