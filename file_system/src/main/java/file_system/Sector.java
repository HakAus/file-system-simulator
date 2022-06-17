package file_system;

public class Sector {

    // Atributes
    private long position;
    private Sector next;

    // Constructors
    Sector() {
        position = 0;
        next = null;
    }

    Sector(long pPosition, Sector pNext) {
        position = pPosition;
        next = pNext;
    }

    // Methods
    public long getPosition() {
        return position;
    }

    public void setPosition(long value) {
        position = value;
    }

    public Sector getNext() {
        return next;
    }

    public void setNext(Sector value) {
        next = value;
    }

}
