package file_system;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class WriteFile {

    public void write(String fullpath, String content){
        try {
            
            File file = new File(fullpath);

            // CUANDOS E REPITE
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}