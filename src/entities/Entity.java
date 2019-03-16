package entities;

public class Entity {
    long ID;
    String label;

    long createID(){
        return hashCode();
    }

    long getID() {
        return ID;
    }

    public String getLabel() {
        return label;
    }
}
