package file_system;

import java.util.ArrayList;
import java.util.Date;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimulationFile {

    // Atributes
    private long start, end, size;
    private BooleanProperty isDirectory;
    private StringProperty name, path, extension;
    private Date creationDate, lastModified;
    private ArrayList<SimulationFile> files;
    private SimulationFile parent;

    // Constructors

    // Default
    SimulationFile() {
        start = end = size = 0;
        isDirectory = new SimpleBooleanProperty();
        parent = null;
        name = new SimpleStringProperty();
        path = new SimpleStringProperty();
        extension = new SimpleStringProperty();
        lastModified = new Date();
        files = null;
    }

    // For files
    SimulationFile(SimulationFile pParent, String pPath, long pStart, long pEnd, long pSize,
            String pName, String pExtension, Date pLastModified) {
        start = pStart;
        end = pEnd;
        size = pSize;
        isDirectory = new SimpleBooleanProperty(false);
        parent = pParent;
        name = new SimpleStringProperty(pName);
        path = new SimpleStringProperty(pPath);
        extension = new SimpleStringProperty(pExtension);
        creationDate = new Date();
        lastModified = pLastModified;
        files = null;
    }

    // For directories
    SimulationFile(SimulationFile pParent, String pPath, String pName, Date pLastModified) {
        start = end = -1;
        isDirectory = new SimpleBooleanProperty(true);
        parent = pParent;
        name = new SimpleStringProperty(pName);
        path = new SimpleStringProperty(pPath);
        extension = new SimpleStringProperty();
        lastModified = pLastModified;
        files = new ArrayList<SimulationFile>();
    }

    public StringProperty getExtensionProperty() {
        return extension;
    }

    public String getExtension() {
        return extension.get();
    }

    public void setExtension(String pExtension) {
        extension.set(pExtension);
    }

    public StringProperty getPathProperty() {
        return path;
    }

    public String getPath() {
        return path.get();
    }

    public void setPath(String pPath) {
        path.set(pPath);
    }

    public void upsertFile(SimulationFile pFile) {

        pFile.setIsDirectory(false);

        // Check if file already exists
        ArrayList<String> foundFiles = new ArrayList<>();
        for (SimulationFile file : files) {
            if (!file.isDirectory()) {
                if (file.getFullname().contains(pFile.getFullname())) {
                    // If found, update de file
                    file = pFile;
                    foundFiles.add(file.getFullname());
                }
            }
        }

        // Add new file only if no other files with the same name exist
        if (foundFiles.isEmpty())
            files.add(pFile);
    }

    public void addDirectory(SimulationFile directory) {
        directory.setIsDirectory(true);
        files.add(directory);
    }

    public long getStart() {
        return start;
    }

    public void setStart(long value) {
        start = value;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long value) {
        end = value;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long value) {
        size = value;
    }

    public void setIsDirectory(boolean value) {
        isDirectoryProperty().set(value);
    }

    public boolean isDirectory() {
        return isDirectoryProperty().get();
    }

    private BooleanProperty isDirectoryProperty() {
        if (isDirectory == null)
            isDirectory = new SimpleBooleanProperty();
        return isDirectory;
    }

    public void setName(String value) {
        nameProperty().set(value);
    }

    public String getName() {
        return nameProperty().get();
    }

    public String getFullname() {
        return getName() + "." + getExtension();
    }

    // public String getExtension() {
    // return nameProperty().get().substring(nameProperty().get().lastIndexOf(".") +
    // 1);
    // }

    public String getCreationDate() {
        return creationDate.toString();
    }

    public String getModificationDate() {
        return lastModified.toString();
    }

    public ArrayList<SimulationFile> getFiles() {
        return files;
    }

    public SimulationFile getParentDirectory() {
        return parent;
    }

    public StringProperty nameProperty() {
        if (name == null)
            name = new SimpleStringProperty(this, "name");
        return name;
    }

    public void setLastModified(Date date) {
        lastModified = date;
    }

    public Date getLastModified() {
        return lastModified;
    }

}
