package entities;

//basic class for Place or Transition
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
