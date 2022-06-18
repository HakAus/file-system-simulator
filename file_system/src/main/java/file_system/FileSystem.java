package file_system;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;

import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

public class FileSystem {

    // private static TreeItem<SimulationFile> currentDirectory;
    private ArrayList<SimulationFile> tree;
    private RandomAccessFile disk;
    public static int freeSpace;
    public static int sectorSize;
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
        System.out.println("Content length: " + content.length() + " freeSpace: " + freeSpace);
        if (content.length() < freeSpace) {
            try {
                ArrayList<Long> writtenSectors = writeToSectors(fileName);
                start = writtenSectors.get(0).longValue();
                size = (long) content.length();
                end = writtenSectors.get(writtenSectors.size() - 1).longValue();
                file = new SimulationFile(start, end, size, fileName, new Date(), writtenSectors);
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

    public ArrayList<Long> writeToSectors(String content) throws IOException {
        int writtenChars = 0;
        int currentChar = 0;
        ArrayList<Long> sectors = new ArrayList<>();
        while (writtenChars < content.length() && (disk.getFilePointer() + 1) < freeSpace) {
            // Read char
            long position = disk.getFilePointer();
            char c = (char) disk.read();
            if (c == Constants.FREE_SPACE) {
                // fill sector
                disk.seek(position);
                sectors.add(position);
                for (int i = 0; i < sectorSize && currentChar < content.length(); i++) {
                    disk.writeByte(content.charAt(currentChar));
                    currentChar++;
                    writtenChars++;
                }
            } else {
                // Go to next sector
                disk.seek(disk.getFilePointer() - 1 + sectorSize);
            }
        }
        disk.seek(0);
        return sectors;
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
