package file_system;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    private TextField txtCurrentDirectory, txtExtension, txtFilename, txtSearchBox, txtPath;

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
        SimulationFile directory = FileSystem.currentDirectory;
        fileSystem.createFile(directory, "arch_Austin", "austin");
        System.out.println("---");
        fileSystem.createFile(directory, "arch_Ulises", "ulises");
        toggleFileEditor();
        btnAddFile.setDisable(true);
        btnAddDirectory.setDisable(true);

        // MIENTRAS
        // getProperties(event);
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
        String search = txtSearchBox.getText();
        if (search.contains("*")) {
            search = search.replace("*", ""); // TEMPORAL Y SI FUNCIONA PUEDE QUE LO DEJE ASI xD
        }

        // Search in the tree
        ArrayList<String> results = new ArrayList<String>();
        SimulationFile root = fileSystem.currentDirectory; // get the root directory PENDIENTE
        String currentPath = root.getName() + "/";

        searching(root, search, currentPath, results);
    }

    // @FXML
    void getProperties(ActionEvent event) {
        SimulationFile file = FileSystem.currentFile;
        String properties = "";
        properties += "Name: " + file.getName() + "\n";
        properties += "Extension: " + file.getExtension() + "\n";
        properties += "Size: " + file.getSize() + "\n";
        properties += "Creation Date: " + file.getCreationDate() + "\n";
        properties += "Modification Date: " + file.getModificationDate() + "\n";

        System.out.println(properties);
    }

    // @FXML
    void move() {
        System.out.println("Move");
        SimulationFile file = FileSystem.currentDirectory;

        SimulationFile parent = file.getParentDirectory();
        parent.getFiles().remove(file);

        SimulationFile newParent = fileSystem.currentDirectory; // OBTENER EL SELECCIONADO COMO DESTINO
        newParent.getFiles().add(file);
    }

    // @FXML
    void copy1() throws IOException { // Ruta real a virtual // TAMBIEN REVISAR PORQUE TENIA MUCHO SUE:O
        System.out.println("Copy");
        String path = txtPath.getText();
        ReadFile readFile = new ReadFile();
        SimulationFile destiny = fileSystem.currentDirectory; // OBTENER EL SELECCIONADO COMO DESTINO
        File file = new File(path);
        String content = readFile.read(path);
        fileSystem.createFile(destiny, file.getName(), content);
    }

    // @FXML
    void copy2() { // Ruta virtual a real
        System.out.println("Copy");
        SimulationFile file = fileSystem.currentFile;
        String content = file.getName(); // getContent(); // AQUI SE USARA LA FUNCION DE VER FILE
        String pathDestiny = txtPath.getText();
        WriteFile writeFile = new WriteFile();
        writeFile.write(pathDestiny, content);

    }

    // @FXML
    void copy3() { // Ruta real a real
        System.out.println("Copy");

        SimulationFile file = fileSystem.currentDirectory;

        SimulationFile destiny = fileSystem.currentDirectory; // OBTENER EL SELECCIONADO COMO DESTINO
        destiny.getFiles().add(file);
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

    private boolean isFile(String search) {
        if (search.contains("."))
            return true;
        return false;
    }

    private void searching(SimulationFile directory, String search, String currentPath, ArrayList<String> results) {

        ArrayList<SimulationFile> files = directory.getFiles();
        for (SimulationFile file : files) {
            // if (isFile(search)){
            if (isFile(file.getFullname())) {
                if (file.getFullname().contains(search)) { // HACER EXPRESION REGULAR PARA LOS CASOS DE *.???
                    results.add(currentPath + file.getFullname());
                }
            } else {
                if (file.getName().contains(search)) {
                    results.add(currentPath + file.getName());
                }
                searching(file, search, currentPath + file.getName() + "/", results);
            }
            // } else {
            // if (file.getName().contains(search)) {
            // results.add(currentPath + file.getName());
            // } else {
            // searching(file, search, currentPath + file.getName() + "/", results);
            // }
            // }
        }

        System.out.println("Resultados: " + results);

    }
}
