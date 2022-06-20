package file_system;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class PopUp {

    public static void display(String mode) {
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);

        popupWindow.setTitle("Add file");

        switch (mode) {
            case Constants.FILE_CREATED_SUCCESFULLY:
                popupWindow.setScene(createScene(popupWindow, "File created succesfully!"));
                break;
            case Constants.FILE_DELETED_SUCCESFULLY:
                popupWindow.setScene(createScene(popupWindow, "File deleted succesfully!"));
            case Constants.ERROR_NO_AVAILABLE_MEMORY:
                popupWindow.setScene(createScene(popupWindow, "There's not enough memory to write the file! :("));

            default:
                break;
        }

        popupWindow.showAndWait();

    }

    private static Scene createScene(Stage stage, String message) {
        Label label = new Label(message);

        Button button = new Button("Aceptar");

        button.setOnAction(e -> {
            stage.close();
        });

        VBox layout = new VBox(10);

        layout.getChildren().addAll(label, button);

        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 250);

        return scene;
    }
}
