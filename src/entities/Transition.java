package entities;

import javafx.util.Pair;

import java.util.*;

public class Transition extends Entity {

    public Transition(String name) {
        this.label = name;
        this.ID = createID();
    }

    public Transition() {
        this.ID = createID();
    }
    private HashMap<Place, Arc> inputPlaces = new HashMap<>();
    private HashMap<Place, Integer> outputPlaces = new HashMap<>();



    public void fire(){
        if (isFireable()) {
            consumeMarks();
            addMarks();
            System.out.println((label != null ? label : ID) + " fired successfully.");
        }
        else
            throw new IllegalStateException((label != null ? label : ID) + " cannot be fired.");
    }
    //TODO implement
    public boolean isFireable() {
        for (Map.Entry<Place, Arc> entry : inputPlaces.entrySet()) {
            if (!entry.getValue().isReady())
                return false;
        }
        return true;
    }

    private void consumeMarks(){
        for (Map.Entry<Place, Arc> inP : inputPlaces.entrySet()){
            if (inP.getValue() instanceof ResetArc)
                inP.getKey().consumeMark();
            else
                inP.getKey().consumeMark(inP.getValue().mark);
        }
    }
    private void addMarks(){
        for (Map.Entry<Place, Integer> outP: outputPlaces.entrySet()) {
            outP.getKey().addMark(outP.getValue());
        }
    }
    public void addInputPlace(Place newPlace, Arc arc){
        inputPlaces.put(newPlace, arc);
    }
    public void addOutputPlace(Place newPlace, int mark){
        outputPlaces.put(newPlace, mark);
    }
    public void printInputPlaces(){
        Iterator itr = inputPlaces.entrySet().iterator();
        System.out.println("Iterating over transition (" + (label != null ? label : ID) +") input places:");
        printMap(itr);
    }
    //System.out.println(Collections.singletonList(outputPlaces));
    public void printOutputPlaces(){
        Iterator itr = outputPlaces.entrySet().iterator();
        System.out.println("Iterating over transition (" + (label != null ? label : ID) +") output places:");
        printMap(itr);

    }
    private void printMap(Iterator itr) {
        while (itr.hasNext()){
            Map.Entry pair = (Map.Entry)itr.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            itr.remove();
        }
        System.out.println();
    }
}
