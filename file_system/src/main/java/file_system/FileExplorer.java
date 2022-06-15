package file_system;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class FileExplorer {
    private TreeView<String> view;

    FileExplorer() {
        view = new TreeView<String>();
    }

    private void CreateTreeView(File[] files) {
        TreeItem<String> root = new TreeItem<String>();
        for (File file : files) {

        }
    }

    // private TreeItem<String> AddItem(File file) {
    // if (file.isDirectory()) {

    // }
    // root.getChildren().add(new TreeItem<String>(file.getName()));
    // }

    // public TreeView<TreeView<String>> getView() {
    // return view;
    // }

    public void addItem(TreeItem<String> item) {
        view.setRoot(item);
    }
}
