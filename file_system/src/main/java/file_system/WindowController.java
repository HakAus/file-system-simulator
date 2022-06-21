package file_system;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Button btnAddDirectory, btnAddFile, btnCancelSaveFile, btnSaveFile, btnSearch;

    @FXML
    private TreeView<SimulationFile> treeView;

    @FXML
    private GridPane window, fileEditor;

    @FXML
    private TextField txtExtension, txtFilename, txtSearchBox, txtPath;

    @FXML
    private TextArea txtFileContent;

    @FXML
    private Label lblAvailableSpace;

    @FXML
    private Button btnProperties;

    private FileSystem fileSystem;
    private SimulationFile selectedItem;

    @FXML
    void selectItem() {
        TreeItem<SimulationFile> selected = treeView.getSelectionModel().getSelectedItem();

        if (selected != null) {
            selectedItem = selected.getValue();
            if (selected.getValue().isDirectory()) {
                FileSystem.currentDirectory = selected.getValue();
            } else {
                FileSystem.currentFile = selected.getValue();

                // Read the file
                String fileContent = fileSystem.readFile(selected.getValue());
                txtFileContent.setText(fileContent);
                txtFilename.setText(selected.getValue().getName());
                txtExtension.setText(selected.getValue().getExtension());

                // Show the file
                toggleFileEditor();
            }
            // String path = getPath(selected.getValue());
            System.out.println("Got the path: " + selected.getValue().getPath());
            // selected.getValue().setPath(path);
            txtPath.setText(selected.getValue().getPath());
        }
    }

    @FXML
    void selectItemOptions() {
        TreeItem<SimulationFile> selected = treeView.getSelectionModel().getSelectedItem();
        if (selected != null)
            System.out.println(selected.getValue().getName() + "options");
    }

    @FXML
    void addDirectory(ActionEvent event) {
        // Create directory
        String name = txtPath.getText();
        String[] directories = name.split("/");
        if (directories.length > 1) {
            name = directories[directories.length - 1];

            ArrayList<String> results = searching(FileSystem.currentDirectory, name,
                    FileSystem.currentDirectory.getPath(),
                    new ArrayList<String>());

            if (results.isEmpty()) {
                SimulationFile newDirectory = new SimulationFile(FileSystem.currentDirectory,
                        txtPath.getText(), name, new Date());
                FileSystem.currentDirectory.addDirectory(newDirectory);

                // Refresh GUI tree
                createTree();
            }
        } else {
            PopUp.display("Enter a file or directory under root");
        }
    }

    @FXML
    void addFile(ActionEvent event) {
        System.out.println("Add file");
        if (!fileEditor.isVisible())
            toggleFileEditor();

        // MIENTRAS
        // getProperties(event);
    }

    @FXML
    void cancelSaveFile(ActionEvent event) {
        // Bottons are enabled again
        toggleFileEditor();
    }

    @FXML
    void saveFile(ActionEvent event) {

        ArrayList<String> results = searching(FileSystem.currentDirectory, txtFilename.getText(),
                FileSystem.currentDirectory.getPath(),
                new ArrayList<String>());

        if (results.isEmpty()) {
            // Create new file
            fileSystem.createFile(FileSystem.currentDirectory,
                    txtFilename.getText(),
                    txtExtension.getText(),
                    txtFileContent.getText());
        } else {
            // Update file
            fileSystem.updateFile(FileSystem.currentFile, txtFileContent.getText());
        }

        // Update tree
        createTree();

        // GUI handling
        PopUp.display(Constants.FILE_CREATED_SUCCESFULLY);
        toggleFileEditor();
    }

    @FXML
    void delete(ActionEvent event) {
        if (selectedItem.isDirectory()) {

        } else {
            try {
                // Delete file from disk
                fileSystem.deleteFile(selectedItem);

                // Delete file from file system

                // Update tree
                toggleFileEditor();
                createTree();

                // Show message
                PopUp.display(Constants.FILE_DELETED_SUCCESFULLY);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void search(ActionEvent event) {
        String search = txtSearchBox.getText();
        if (search.contains("*")) {
            search = search.replace("*", ""); // TEMPORAL Y SI FUNCIONA PUEDE QUE LO DEJE ASI xD
        }

        // Search in the tree
        ArrayList<String> results = new ArrayList<String>();
        SimulationFile root = FileSystem.currentDirectory; // get the root directory PENDIENTE
        String currentPath = root.getName() + "/";

        results = searching(root, search, currentPath, results);

        if (!results.isEmpty()) {
            String[] filteredPaths = new String[results.size()];
            filteredPaths = results.toArray(filteredPaths);
            createFilteredTree(filteredPaths);
        } else {
            createTree();
        }
    }

    @FXML
    void getProperties(ActionEvent event) {
        SimulationFile file = FileSystem.currentFile;
        String properties = "";
        properties += "Name: " + file.getName() + "\n";
        properties += "Extension: " + file.getExtension() + "\n";
        properties += "Size: " + file.getSize() + "\n";
        properties += "Creation Date: " + file.getCreationDate() + "\n";
        properties += "Modification Date: " + file.getModificationDate() + "\n";

        System.out.println(properties);
        PopUp.display(properties);
    }

    @FXML
    void move() {
        System.out.println("Move");
        SimulationFile file = FileSystem.currentDirectory;

        SimulationFile parent = file.getParentDirectory();
        parent.getFiles().remove(file);

        SimulationFile newParent = FileSystem.currentDirectory; // OBTENER EL SELECCIONADO COMO DESTINO
        newParent.getFiles().add(file);
    }

    // @FXML
    void copy1() throws IOException { // Ruta real a virtual // TAMBIEN REVISAR PORQUE TENIA MUCHO SUE:O
        System.out.println("Copy");
        String path = txtPath.getText();
        ReadFile readFile = new ReadFile();
        SimulationFile destiny = FileSystem.currentDirectory; // OBTENER EL SELECCIONADO COMO DESTINO
        File file = new File(path);
        String content = readFile.read(path);

        fileSystem.createFile(destiny, file.getName(), getExtensionToPath(path), content);
    }

    // @FXML
    void copy2() { // Ruta virtual a real
        System.out.println("Copy");
        SimulationFile file = FileSystem.currentFile;
        String content = file.getName(); // getContent(); // AQUI SE USARA LA FUNCION DE VER FILE
        String pathDestiny = txtPath.getText();
        WriteFile writeFile = new WriteFile();
        writeFile.write(pathDestiny, content);
    }

    // @FXML
    void copy3() { // Ruta real a real
        System.out.println("Copy");

        SimulationFile file = FileSystem.currentDirectory;

        SimulationFile destiny = FileSystem.currentDirectory; // OBTENER EL SELECCIONADO COMO DESTINO
        destiny.getFiles().add(file);
    }

    public String getExtensionToPath(String path) {
        String[] parts = path.split("\\.");
        String extension = parts[parts.length - 1];
        return extension;
    }

    private void createTree() {

        TreeItem<SimulationFile> root = new TreeItem<SimulationFile>(FileSystem.root);

        traverseTree(root, FileSystem.root.getFiles());

        treeView.setRoot(root);

        System.out.println("Root");

        txtPath.setText(FileSystem.currentDirectory.getPath());
    }

    private void createFilteredTree(String[] filterPaths) {

        System.out.println("Creando arbol ...");

        TreeItem<SimulationFile> root = new TreeItem<SimulationFile>(FileSystem.root);

        traverseFilteredTree(root, FileSystem.root.getFiles(), filterPaths);

        treeView.setRoot(root);

        System.out.println("Root");

        txtPath.setText(FileSystem.currentDirectory.getPath());
    }

    private void traverseTree(TreeItem<SimulationFile> treeItem, ArrayList<SimulationFile> files) {
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

    private void traverseFilteredTree(TreeItem<SimulationFile> treeItem, ArrayList<SimulationFile> files,
            String[] filteredPaths) {
        for (SimulationFile file : files) {
            System.out.println("file.getPath() -> " + file.getPath());
            for (String path : filteredPaths) {
                System.out.println("path -> " + path);

                if (file.getPath() == path) {
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
        }
    }

    public void initialize() {
        fileSystem = new FileSystem();

        createTree();

        // GUI bindings
        btnSearch.disableProperty().bind(
                Bindings.isEmpty(txtSearchBox.textProperty()));
        btnAddFile.disableProperty().bind(
                Bindings.isEmpty(txtPath.textProperty()));
        btnAddDirectory.disableProperty().bind(
                Bindings.isEmpty(txtPath.textProperty()));
        btnSaveFile.disableProperty().bind(
                txtFilename.textProperty().isEmpty().and(txtExtension.textProperty().isEmpty()));
        lblAvailableSpace.textProperty().bind(
                FileSystem.freeSpace.asString("%s characters left"));

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

    private ArrayList<String> searching(SimulationFile directory, String search, String currentPath,
            ArrayList<String> results) {

        ArrayList<SimulationFile> files = directory.getFiles();
        if (files != null) {
            for (SimulationFile file : files) {
                if (file.isDirectory()) {
                    if (file.getName().contains(search)) { // HACER EXPRESION REGULAR PARA LOS CASOS DE *.???
                        results.add(currentPath + file.getName());
                    }
                } else {
                    if (file.getFullname().contains(search)) {
                        results.add(currentPath + "/" + file.getFullname());
                    }
                    results = searching(file, search, currentPath + file.getName() + "/", results);
                }
            }
        } else {
            if (directory.getName().contains(search)) { // HACER EXPRESION REGULAR PARA LOS CASOS DE *.???
                results.add(currentPath + directory.getName());
            }
        }

        System.out.println("Resultados: " + results);
        return results;
    }
}
