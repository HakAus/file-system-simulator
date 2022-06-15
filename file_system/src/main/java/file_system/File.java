package file_system;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class File {

    // Atributes
    private int start, end;
    private BooleanProperty isDirectory;
    private StringProperty name;
    private LongProperty lastModified;

    // Constructors
    File() {
        start = 0;
        end = 0;
        isDirectory = new SimpleBooleanProperty();
        name = new SimpleStringProperty();
        lastModified = new SimpleLongProperty();
    }

    File(int pStart, int pEnd, boolean pIsDirectory, String pName, long pLastModified) {
        start = pStart;
        end = pEnd;
        isDirectory = new SimpleBooleanProperty(pIsDirectory);
        name = new SimpleStringProperty(pName);
        lastModified = new SimpleLongProperty(pLastModified);
    }

    // Methods
    public int getStart() {
        return start;
    }

    public void setStart(int value) {
        start = value;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int value) {
        end = value;
    }

    public void setIsDrectory(boolean value) {
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

    public StringProperty nameProperty() {
        if (name == null)
            name = new SimpleStringProperty(this, "name");
        return name;
    }

    public void setLastModified(long value) {
        lastModifiedProperty().set(value);
    }

    public long getLastModified() {
        return lastModifiedProperty().get();
    }

    public LongProperty lastModifiedProperty() {
        if (lastModified == null)
            lastModified = new SimpleLongProperty(this, "lastModified");
        return lastModified;
    }

}
