package file_system;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class FileExplorer {
    private TreeView<String> view;
    private TreeItem<String> root = new TreeItem<String>();

    public TreeItem<String> getRoot() {
        return root;
    }

    FileExplorer() {
        view = new TreeView<String>();
    }

    private void CreateTreeView(SimulationFile[] files) {
        TreeItem<String> root = new TreeItem<String>();

        for (SimulationFile file : files) {
            if (file.isDirectory()) {

            }
        }

        view.setRoot(root);
    }

    public void createFile() {
        TreeItem item = new TreeItem<>();

    }
}
