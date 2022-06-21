package file_system;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class StartupController {

    @FXML
    private Button btnCreateDisc;

    @FXML
    private TextField txtSectorAmount;

    @FXML
    private TextField txtSectorSize;

    @FXML
    void createDisc(ActionEvent event) {

        // Create file for disc
        try {
            File discFile = new File(Constants.DISC_PATH);
            if (discFile.createNewFile()) {
                System.out.println("File created: " + discFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Write initial data for disc
        FileSystem.sectorAmount = Integer.parseInt(txtSectorAmount.getText());
        int sectorSize = Integer.parseInt(txtSectorSize.getText());
        int pointerSize = Integer.toHexString(FileSystem.sectorAmount * sectorSize).length();
        System.out.println("Pointer size: " + pointerSize);
        FileSystem.sectorSize = sectorSize + pointerSize;
        FileSystem.pointerSize = pointerSize;
        FileSystem.freeSpace.set((FileSystem.sectorSize - pointerSize) * FileSystem.sectorAmount);

        try {
            FileWriter myWriter = new FileWriter(Constants.DISC_PATH);
            char[] cbuf = new char[FileSystem.sectorSize * FileSystem.sectorAmount];
            for (int i = 0; i < FileSystem.sectorSize * FileSystem.sectorAmount; i++) {
                cbuf[i] = '0';
            }
            myWriter.write(cbuf);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        App.switchScene("window");

    }

}
