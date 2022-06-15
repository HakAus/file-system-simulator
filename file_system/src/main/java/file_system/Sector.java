package file_system;

public class Sector {

    // Atributes
    private int position, next;

    // Constructors
    Sector() {
        position = 0;
        next = 0;
    }

    Sector(int pPosition, int pNext) {
        position = pPosition;
        next = pNext;
    }

    // Methods
    public int getPosition() {
        return position;
    }

    public void setPosition(int value) {
        position = value;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int value) {
        next = value;
    }

}
