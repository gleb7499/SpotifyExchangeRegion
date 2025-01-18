package org.example.spotifyexchangeregion.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.spotifyexchangeregion.parsing.Parsing;
import org.example.spotifyexchangeregion.models.Account;

import java.io.IOException;

public class Main extends Application {
    public static void changeRegion(Account account) {
        try (final Parsing parsing = new Parsing(account)) {
            parsing.changeRegion("USA");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/org/example/spotifyexchangeregion/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Spotify Exchange Region");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}