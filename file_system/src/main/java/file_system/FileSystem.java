package file_system;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;

import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;

public class FileSystem {

    // private static TreeItem<SimulationFile> currentDirectory;
    private ArrayList<SimulationFile> tree;
    private RandomAccessFile disk;
    public static int freeSpace;
    public static int sectorSize;
    public static int pointerSize;
    public static int sectorAmount;
    public static SimulationFile currentDirectory;
    public static SimulationFile currentFile;

    FileSystem() {
        try {
            disk = new RandomAccessFile(Constants.DISC_PATH, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        freeSpace = sectorAmount * sectorSize;
    }

    public void createFile(SimulationFile directory, String fileName, String content) {
        // Check for available space
        SimulationFile file;
        long start, end, size;
        start = end = size = 0;

        if (content.length() < freeSpace) {
            try {
                // File is written to disc
                ArrayList<Long> fileSectors = writeToSectors(content);

                // File system file creation
                start = fileSectors.get(0).longValue();
                size = (long) content.length();
                end = fileSectors.get(1).longValue();
                file = new SimulationFile(start, end, size, fileName, new Date());

                // PRUEBA LECTURA
                String text = "";
                String result;
                result = readSector(text, file.getStart(), file.getEnd(), size);
                System.out.println("Lectura: " + result);

                // File is added to the directory
                currentFile = file;
                // directory.addFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            System.out.println("No hay espacio");
        }
    }

    public void createdDirectory(SimulationFile parent, String name) {
        parent.addDirectory(new SimulationFile(name, new Date()));
    }

    public long writeSector(String content, long position, long end) throws IOException {

        // Lookup sector
        disk.seek(position);
        char block = (char) disk.read();
        disk.seek(position);

        if (block == Constants.FREE_SPACE) {
            // Write to sector
            for (int i = 0; i < sectorSize - pointerSize; i++) {
                disk.writeByte(content.charAt(0));
                content = content.length() > 1 ? content = content.substring(1, content.length()) : "";

                if (content.isEmpty()) {
                    end = position;
                    return end;
                }
            }

            // Get next sector index
            long sectorIdx = writeSector(content, disk.getFilePointer() + pointerSize, end);

            // Convert position to hex
            String pointer = Integer.toHexString((int) sectorIdx);

            // Adjust file pointer
            disk.seek(position + (sectorSize - pointerSize));

            // Write pointer
            int cont = 0;
            for (int i = 0; i < pointerSize; i++) {
                if (i < pointerSize - pointer.length())
                    disk.writeByte('0');
                else {
                    disk.writeByte(pointer.charAt(cont));
                    cont++;
                }

            }

            return position;

        } else {
            // Go to next sector
            return writeSector(content, disk.getFilePointer() + sectorSize, end);
        }
    }

    public String readSector(String content, long start, long end, long size) throws IOException{
        // Lookup sector
        disk.seek(start);

        // Read sector
        int cont = 0;
        int limit = sectorSize - pointerSize;
        String pointer = "";
        for (int i = 0; i < sectorSize; i++) {
            
            if (i >= limit){ //REVISAR SI ES MAYOR IGUAL O SOLO MAYOR
                pointer += (char) disk.read();
            }
            else {
                content += (char) disk.read();
                
                if (content.length() == size) {
                    return content;
                }
            }
            cont++;
        }

        //hexadecimal to decimal
        Integer pointerDec = Integer.parseInt(pointer, 16);
        

        System.out.println("content: " + content);
        System.out.println("pointer " + pointer);
        System.out.println("pointerDec " + pointerDec);
        System.out.println("end: " + end);
        System.out.println("start: " + start);

        return readSector(content, pointerDec, end, size);

    }

    public ArrayList<Long> writeToSectors(String content) throws IOException {

        ArrayList<Long> result = new ArrayList<>();
        long end = 0;
        if (freeSpace > content.length()) {
            long start = writeSector(content, disk.getFilePointer(), end);
            result.add(start);
            result.add(end);
            freeSpace -= content.length();
        }

        // Reset disc file pointer
        disk.seek(0);

        return result;

        // while (charIdx < content.length() && (disk.getFilePointer() + 1) < freeSpace)
        // {

        // // Read char
        // long position = disk.getFilePointer();
        // char block = (char) disk.read();
        // if (block == Constants.FREE_SPACE) {
        // // fill sector
        // disk.seek(position);
        // sectors.add(position);
        // int sectorBlocksWritten = 0;

        // for (int i = 0; i < sectorSize - pointerSize; i++) {
        // if (charIdx < content.length()) {
        // disk.writeByte(content.charAt(charIdx));
        // charIdx++;
        // sectorBlocksWritten++;
        // }
        // }

        // // Save position to patch the sector pointer
        // long spaceRemaining = sectorSize - (sectorBlocksWritten + pointerSize);
        // patchPosition = position + spaceRemaining;

        // // Adjust seek position in disc for next sector write
        // disk.seek(patchPosition + pointerSize);

        // } else {
        // // Go to next sector
        // disk.seek(disk.getFilePointer() - 1 + sectorSize);
        // }
        // }

    }

    public void writeFile(String content) {

    }

    // Creating the mouse event handler

    public static TreeItem<String> createDirectory(String name) {
        TreeItem<String> directory = new TreeItem<String>(name);

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.isSecondaryButtonDown()) {
                    System.out.println("Click derecho");
                } else {
                    System.out.println("Click izquierdo");
                }
            }
        };

        directory.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        return directory;
    }

}
