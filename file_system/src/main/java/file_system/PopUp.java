package file_system;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class PopUp {

    private static TreeView<SimulationFile> treeView;

    public static void display(String message) {
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);

        popupWindow.setTitle("Add file");
        popupWindow.setScene(createScene(popupWindow, message));

        popupWindow.showAndWait();
    }

    public static void showFileSystem(FileSystem fileSystem) {
        treeView = new TreeView<SimulationFile>();
        TreeItem<SimulationFile> root = new TreeItem<SimulationFile>(FileSystem.root);

        traverseTree(root, fileSystem.root.getFiles());

        treeView.setRoot(root);

        treeView.setCellFactory(new Callback<TreeView<SimulationFile>, TreeCell<SimulationFile>>() {

            @Override
            public TreeCell<SimulationFile> call(TreeView<SimulationFile> param) {
                return new TreeCell<SimulationFile>() {
                    @Override
                    protected void updateItem(SimulationFile item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(item.getName());
                            if (item.isDirectory())
                                setGraphic(new ImageView(new Image("folder.png", 20, 20, false, false)));
                            else
                                setGraphic(new ImageView(new Image("file.png", 20, 20, false, false)));
                        }
                    }
                };
            }
        });

        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                // TODO Auto-generated method stub
                TreeItem<SimulationFile> selected = treeView.getSelectionModel().getSelectedItem();
                System.out.println("Selecciono " + selected.getValue().getName());
                FileSystem.destinationDirectory = selected.getValue();
            }

        });

        Button button = new Button("Aceptar");

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        button.setOnAction(e -> {
            stage.close();
        });

        VBox layout = new VBox(10);

        layout.getChildren().addAll(treeView, button);

        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 250);

        stage.setScene(scene);

        stage.showAndWait();

    }

    private static void traverseTree(TreeItem<SimulationFile> treeItem, ArrayList<SimulationFile> files) {
        for (SimulationFile file : files) {
            System.out.println("Item: " + file.getName());
            TreeItem<SimulationFile> newItem = new TreeItem<SimulationFile>(file);
            if (file.isDirectory()) {
                if (file.getFiles() != null) {
                    traverseTree(newItem, file.getFiles());
                }
                treeItem.getChildren().add(newItem);
            } else {
                treeItem.getChildren().add(newItem);
            }
        }
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
