package file_system;

import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class PopUp {

    private static void display(String mode) {
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);

        popupWindow.setTitle("Add file");

        switch (mode) {
            case Constants.FILE_CREATED_SUCCESFULLY:
                popupWindow.setScene(fileCreatedScene(popupWindow));
                break;
            default:
                break;
        }

        popupWindow.showAndWait();

    }

    public static void FileCreatedSuccesfullyPopUp() {
        display(Constants.FILE_CREATED_SUCCESFULLY);
    }

    private static Scene fileCreatedScene(Stage stage) {
        Label label = new Label("Se creó el archivo correctamente");

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
