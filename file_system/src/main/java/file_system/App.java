package file_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */

public class App extends Application {

    private static Parent startup, window;
    private static Stage stage;

    @Override
    public void start(Stage pStage) throws IOException {

        stage = pStage;
        startup = loadFXML("startup");
        window = loadFXML("window");

        // Initial setup
        Scene scene = new Scene(startup);
        stage.setScene(scene);
        stage.show();
    }

    static void switchScene(String sceneName) {
        if (sceneName == "window") {
            stage.setScene(new Scene(window));
        }
        if (sceneName == "startup") {
            stage.setScene(new Scene(startup));
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}