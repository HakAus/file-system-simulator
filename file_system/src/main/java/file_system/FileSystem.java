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
                String result;
                System.out.println("File size: " + file.getSize());
                result = readFile(file);
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

    public String readFile(SimulationFile file) {
        try {
            return readSector(file.getStart(), file.getSize());
        } catch (IOException e) {
            return "";
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

    public String readSector(long offset, long blocksLeft)
            throws IOException, NumberFormatException {
        // Lookup sector
        disk.seek(offset);

        // Read sector
        int contentSize = sectorSize - pointerSize;
        String pointer = "";
        String content = "";
        for (int i = 0; i < sectorSize; i++) {

            if (i >= contentSize) {
                pointer += (char) disk.read();
            } else {
                content += (char) disk.read();
                blocksLeft--;

                if (blocksLeft == 0) {
                    int left = sectorSize - i - 1;
                    for (int j = 0; j < left; j++) {
                        disk.read();
                    }
                    return content;
                }
            }
        }

        // hexadecimal to decimal
        long pointerDec = Long.parseLong(pointer, 16);

        return content + readSector(pointerDec, blocksLeft);

    }

    public ArrayList<Long> writeToSectors(String content) throws IOException {

        ArrayList<Long> result = new ArrayList<>();
        if (freeSpace > content.length()) {
            long end = 0;
            long start = writeSector(content, disk.getFilePointer(), end);
            result.add(start);
            result.add(end);
            freeSpace -= content.length();
        }

        // Reset disc file pointer
        disk.seek(0);

        return result;
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
