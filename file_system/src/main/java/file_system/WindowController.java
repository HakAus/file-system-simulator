package file_system;

import java.io.File;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class WindowController {

    @FXML
    private Button btnAddDirectory, btnAddFile, btnCancel, btnSaveFile, btnSearch;

    @FXML
    private TreeView<SimulationFile> treeView;

    @FXML
    private GridPane window, fileEditor;

    @FXML
    private TextField txtCurrentDirectory, txtExtension, txtFilename, txtSearchBox;

    @FXML
    private TextArea txtFileContent;

    private TreeItem<SimulationFile> root = new TreeItem<SimulationFile>();
    private FileSystem fileSystem;

    @FXML
    void selectItem() {
        TreeItem<SimulationFile> selected = treeView.getSelectionModel().getSelectedItem();
        if (selected != null)
            System.out.println(selected.getValue().getName());
    }

    @FXML
    void selectItemOptions() {
        TreeItem<SimulationFile> selected = treeView.getSelectionModel().getSelectedItem();
        if (selected != null)
            System.out.println(selected.getValue().getName() + "options");
    }

    @FXML
    void addDirectory(ActionEvent event) {
    }

    @FXML
    void addFile(ActionEvent event) {
        System.out.println("Add file");
        fileSystem.createFile("prueba", "letras");
        toggleFileEditor();
        btnAddFile.setDisable(true);
        btnAddDirectory.setDisable(true);

    }

    @FXML
    void cancelSaveFile(ActionEvent event) {
        // Bottons are enabled again
        btnAddFile.setDisable(false);
        btnAddDirectory.setDisable(false);
        toggleFileEditor();
    }

    @FXML
    void saveFile(ActionEvent event) {

        // Get data
        // TreeItem<SimulationFile> currentDirectory = FileSystem.getCurrentDirectory();
        // System.out.println("directorio actual: " + currentDirectory.getValue());
        String fileName = txtFilename.getText();
        String fileExtension = txtExtension.getText();
        String content = txtFileContent.getText();

        // GUI handling
        btnAddFile.setDisable(false);
        btnAddDirectory.setDisable(false);
        PopUp.FileCreatedSuccesfullyPopUp();
        toggleFileEditor();
    }

    @FXML
    void search(ActionEvent event) {

    }

    public void initialize() {
        fileSystem = new FileSystem();
        // File folder = new File(getClass().getResource("imgs/folder.png").getFile());
        // File file = new File(getClass().getResource("imgs/file.png").getFile());
        // TreeItem<String> root = new TreeItem<SimulationFile>(new
        // SimulationFile("root", new Date()),
        // new ImageView(new Image("folder.png", 20, 20, false, false)));
        // root.getChildren()
        // .add(new TreeItem<SimulationFile>(new SimulationFile(0, 0, "file1", new
        // Date(), new FileSectorList()),
        // new ImageView(new Image("file.png", 20, 20, false, false))));
        // treeView.setRoot(root);

        // treeView.setCellFactory(new Callback<TreeView<SimulationFile>,
        // TreeCell<String>>() {
        // @Override
        // public TreeCell<SimulationFile> call(TreeView<SimulationFile> param) {
        // return new TreeCell<SimulationFile>() {
        // @Override
        // protected void updateItem(SimulationFile item, boolean empty) {

        // if (item == null || empty) {
        // setText(null);
        // } else {
        // System.out.println("El item no es null");
        // setText(item.getName());
        // selectItem();

        // // EventDispatcher originalDispatcher = getEventDispatcher();
        // // setEventDispatcher(
        // // new TreeMouseEventDispatcher(originalDispatcher, this, currentDirectory));
        // }
        // if (getTreeItem() != null) {
        // // // update disclosureNode depending on expanded state
        // // setDisclosureNode(getTreeItem().isExpanded() ? expanded : collapsed);
        // }
        // }
        // };
        // }
        // });
    }

    private void toggleFileEditor() {
        if (fileEditor.isVisible()) {
            fileEditor.setVisible(false);
        } else {
            fileEditor.setVisible(true);
        }
    }
}
