package file_system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
    public String read(String path) throws IOException {
        String cadena; 
        String text = "";
        FileReader f = new FileReader(path); 
        BufferedReader b = new BufferedReader(f); 
        while((cadena = b.readLine())!=null) { 
            text += cadena + "\n";
        	System.out.println(cadena); 
        } 
        b.close();
        return text; 
    }
}
