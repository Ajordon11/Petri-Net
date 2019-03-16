package entities;

import exceptions.NegativeMarkingException;

public class Place extends Entity {
    Place(String name) {
        this.label = name;
        this.ID = createID();
        this.mark = 0;
    }
    Place(String name, int mark) throws NegativeMarkingException {
        this.label = name;
        this.ID = createID();
        if (mark < 0)
            throw new NegativeMarkingException(ID,name);
        this.mark = mark;
    }


    public Place() {
        this.mark = 0;
        this.ID = createID();
    }
    private int mark;

    int getMark() {
        return mark;
    }

    public void addMark(int mark) {
        this.mark += mark;
    }

    public void consumeMark(int mark) {
        this.mark -= mark;
    }
    public void consumeMark(){
        this.mark = 0;
    }

}
