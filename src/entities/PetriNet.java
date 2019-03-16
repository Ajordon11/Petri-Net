package entities;

import exceptions.MissingIDException;
import exceptions.NegativeMarkingException;
import exceptions.NotAnArcException;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;


public class PetriNet {
    private List<Place> places = new ArrayList<>();
    private List<Transition> transitions = new ArrayList<>();
    private List<Arc> arcs = new ArrayList<>();

    public PetriNet() {
    }
    private void createClassicArc(Entity fromEntity, Entity toEntity){
        try {
            addArc(new ClassicArc(fromEntity,toEntity));
        }catch (NotAnArcException exception){
            System.out.println("Cannot create arc between " + fromEntity.getLabel() + " and " + toEntity.getLabel());
        }
        updateLists(fromEntity, toEntity);
    }
    private void createResetArc(Entity fromEntity, Entity toEntity){
        try {
            addArc(new ResetArc(fromEntity,toEntity));
        }catch (NotAnArcException exception){
            System.out.println("Cannot create arc between " + fromEntity.getLabel() + " and " + toEntity.getLabel());
        }
        updateLists(fromEntity, toEntity);
    }
    private void createResetArc(Entity fromEntity, Entity toEntity, int mark) {
        throw new InvalidParameterException("Cannot create reset arc with marking.");
    }

    private void createClassicArc(Entity fromEntity, Entity toEntity, int mark){
        try {
            addArc(new ClassicArc(fromEntity,toEntity,mark));
        }catch (NotAnArcException exception){
            System.out.println("Cannot create arc between " + fromEntity.getLabel() + " and " + toEntity.getLabel());
        }catch (NegativeMarkingException e){
            System.out.println(e.getMessage());
        }
        updateLists(fromEntity, toEntity);

    }

    private void updateLists(Entity fromEntity, Entity toEntity) {
        if (fromEntity instanceof Place){
            if (!(places.contains(fromEntity)))
                addPlace((Place)fromEntity);
        } else
        if (!(transitions.contains(fromEntity)))
            addTransition((Transition)fromEntity);
        if (toEntity instanceof Place){
            if (!(places.contains(toEntity)))
                addPlace((Place)toEntity);
        } else
        if (!(transitions.contains(toEntity)))
            addTransition((Transition)toEntity);
    }

    public Place findPlace(long ID) throws MissingIDException {
        if (places.isEmpty())
            throw new NullPointerException("List of places is empty.");
        for (Place p: places) {
            if (p.getID() == ID)
                return p;
        }
        throw new MissingIDException("Place "+ID+" doesnt exist.");
    }
    public Transition findTransition(long ID) throws MissingIDException {
        if (transitions.isEmpty())
            throw new NullPointerException("List of transitions is empty.");
        for (Transition t: transitions) {
            if (t.getID() == ID)
                return t;
        }
        throw new MissingIDException("Transition "+ID+" doesnt exist.");
    }
    private void addTransition(Transition newTransition){
        transitions.add(newTransition);
    }
    private void addPlace(Place newPlace){
        places.add(newPlace);
    }
    private void addArc(Arc newArc){
        arcs.add(newArc);
    }


    public void fireTransition(long ID) {
        try {
            Transition toFire = findTransition(ID);
            toFire.fire();
        } catch (MissingIDException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
    public void showFireableTransitions(){
        System.out.println("Transitions ready to be fired: ");
        for (Transition t: transitions){
            if (t.isFireable()){
                System.out.print(t.getLabel() + "(" + t.getID() + ") ,");
            }
        }
        System.out.println();
    }
    public void printNet(){
        System.out.print("Places: ");
        for (Place p : places) {
            System.out.print((p.getLabel()!= null ? p.getLabel() : p.getID()) + (p.getMark() != 0 ? ("("+ p.getMark() +"), ") : ", "));
        }
        System.out.println();
        System.out.print("Transitions: ");
        for (Transition t:transitions){
            System.out.print((t.getLabel()!= null ? t.getLabel() : t.getID()) + ", ");
        }
        System.out.println();
        System.out.println("Arcs between: ");
        for (Arc a : arcs){
            System.out.println((a.getFromEntity().getLabel() != null ? a.getFromEntity().getLabel() : a.getFromEntity().getID()) +
                    " and " + (a.getToEntity().getLabel()!= null ? a.getToEntity().getLabel() : a.getToEntity().getID()));
        }
    }
    public PetriNet setExampleNet() throws NegativeMarkingException {       //no way to code this in easier way
        PetriNet net = new PetriNet();
        net.addTransition(new Transition("t1"));
        net.createClassicArc(new Transition("t2"), new Place("p1"), 5);
        Transition t3 = new Transition("t3");
        net.createClassicArc(new Place("p2"), t3);
        Place p3 = new Place("p3",1);
        net.addPlace(p3);
        Transition t4 = new Transition("t4");
        net.addTransition(t4);
        net.createClassicArc(p3, t4);
        net.createClassicArc(t4, p3, 2);
        Place p4 = new Place("p4",1);
        Place p5 = new Place("p5", 5);
        Place p6 = new Place("p6");
        Place p7 = new Place("p7");
        net.addPlace(p4); net.addPlace(p5); net.addPlace(p6); net.addPlace(p7);
        Transition t5 = new Transition("t5");
        net.addTransition(t5);
        net.createResetArc(p5, t5);
        net.createClassicArc(p4, t5);
        net.createClassicArc(t5, p6);
        net.createClassicArc(t5, p7);

        return net;
    }
}
