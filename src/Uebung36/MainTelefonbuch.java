package Uebung36;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Uebung36.datenbank.Datenbank;

public class MainTelefonbuch extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/Telefonbuch.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() {
        // Trennen der Datenbankverbindung
        Datenbank.getInstance().disconnect();
    }
}
