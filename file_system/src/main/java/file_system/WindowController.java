package file_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

public class WindowController {

    @FXML
    private Button btnAddDirectory;

    @FXML
    private Button btnAddFile;

    @FXML
    private Button btnSearch;

    @FXML
    private TextArea editor;

    @FXML
    private TreeView<?> explorer;

    @FXML
    private TextField txtCurrentDirectory;

    @FXML
    private TextField txtSearchBox;

    @FXML
    private GridPane window;

    @FXML
    void addDirectory(ActionEvent event) {

    }

    @FXML
    void addFile(ActionEvent event) {

    }

    @FXML
    void search(ActionEvent event) {

    }

}
