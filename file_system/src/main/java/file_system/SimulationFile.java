package file_system;

import java.util.ArrayList;
import java.util.Date;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeCell;

public class SimulationFile extends TreeCell<String> {

    // Atributes
    private long start, end, size;
    private BooleanProperty isDirectory;
    private StringProperty name;
    private Date creationDate;
    private Date lastModified;
    private ArrayList<SimulationFile> files;
    private ArrayList<Long> sectors;
    private SimulationFile parent;

    // Constructors
    SimulationFile() {
        start = end = size = 0;
        isDirectory = new SimpleBooleanProperty();
        name = new SimpleStringProperty();
        lastModified = new Date();
        sectors = new ArrayList<Long>();
    }

    SimulationFile(long pStart, long pEnd, long pSize, String pName,
            Date pLastModified, ArrayList<Long> pSectors) {
        start = pStart;
        end = pEnd;
        size = pSize;
        isDirectory = new SimpleBooleanProperty(false);
        name = new SimpleStringProperty(pName);
        creationDate = new Date();
        lastModified = pLastModified;
        sectors = pSectors;
    }

    SimulationFile(String pName, Date pLastModified) {
        start = end = -1;
        isDirectory = new SimpleBooleanProperty(true);
        name = new SimpleStringProperty(pName);
        lastModified = pLastModified;
        sectors = null;
    }

    // Methods
    @Override
    public void updateItem(String simFile, boolean empty) {
        super.updateItem(simFile, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(simFile);
            setGraphic(getTreeItem().getGraphic());
        }
    }

    public void addFile(SimulationFile file) {
        file.setIsDirectory(false);
        files.add(file);
    }

    public void addDirectory(SimulationFile directory) {
        directory.setIsDirectory(true);
        files.add(directory);
    }

    public long getStart() {
        return start;
    }

    public void setStart(int value) {
        start = value;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(int value) {
        end = value;
    }

    public long getSize() {
        return size;
    }

    public void setSize(int value) {
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

    public String getFullname(){
        return nameProperty().getName();
    }

    public String getExtension() {
        return nameProperty().get().substring(nameProperty().get().lastIndexOf(".") + 1);
    }

    public String getCreationDate(){
        return creationDate.toString();
    }

    public String getModificationDate(){
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
