package entities;

import exceptions.MissingIDException;
import exceptions.NegativeMarkingException;
import exceptions.NotAnArcException;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;


public class PetriNet {
    private Set<Place> places = new HashSet<>();
    private Set<Transition> transitions = new HashSet<>();
    private Set<Arc> arcs = new HashSet<>();

    public PetriNet() {
    }

// methods for setting Petri net
    private void createClassicArc(Entity fromEntity, Entity toEntity) throws NotAnArcException {
        addArc(new ClassicArc(fromEntity, toEntity));
        updateLists(fromEntity, toEntity);

    }
    private void createResetArc(Entity fromEntity, Entity toEntity) throws NotAnArcException {
        addArc(new ResetArc(fromEntity, toEntity));
        updateLists(fromEntity, toEntity);
    }
    private void createResetArc(Entity fromEntity, Entity toEntity, int mark) {
        throw new InvalidParameterException("Cannot create reset arc with marking.");
    }

    private void createClassicArc(Entity fromEntity, Entity toEntity, int mark) throws NotAnArcException, NegativeMarkingException {
        addArc(new ClassicArc(fromEntity,toEntity,mark));
        updateLists(fromEntity, toEntity);

    }
// called after every added Arc, to update lists of places and transitions
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

// if classic arc is duplicated, its mark is raised
// if classic arc is later created as reset, it changes to reset
// if reset arc is later created as classic, it stays as reset
    private void addArc(Arc newArc) {
        if (newArc instanceof ClassicArc) {
            for (Arc a : arcs) {
                if (a.getToEntity() == newArc.getToEntity() && a.getFromEntity() == newArc.getFromEntity()) {
                    if (a instanceof ClassicArc)
                        ((ClassicArc) newArc).addMark(1);
                    return;
                }
            }
        } else {
            for (Arc a : arcs) {
                if (a.getToEntity() == newArc.getToEntity() && a.getFromEntity() == newArc.getFromEntity()) {
                    arcs.remove(a);
                }
            }
        }
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
        if (transitions.isEmpty())
            throw new NullPointerException("List of transitions is empty.");
        System.out.println("Transitions ready to be fired: ");
        boolean atleastOne = false;
        for (Transition t: transitions){
            if (t.isFireable()){
                System.out.print(t.getLabel() + "(" + t.getID() + ") ,");
                atleastOne = true;
            }
        }
        if (!atleastOne)
            System.out.println("Net is in deadlock.");
        System.out.println();
    }

// printing either label or ID, marks only if its greater than 0
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
// printing label or ID of in/out entities, printing multiplicity if it's greater than 1
// printing (R) if it's reset arc
        System.out.println("Arcs between: ");
        for (Arc a : arcs){
            // Bad looking code just for good looking output
            if (a instanceof ResetArc){
                System.out.println((a.getFromEntity().getLabel() != null ? a.getFromEntity().getLabel() : a.getFromEntity().getID()) +
                        " and " + (a.getToEntity().getLabel()!= null ? a.getToEntity().getLabel() : a.getToEntity().getID()) + "(R)");
            } else {
                System.out.println((a.getFromEntity().getLabel() != null ? a.getFromEntity().getLabel() : a.getFromEntity().getID()) +
                        " and " + (a.getToEntity().getLabel() != null ? a.getToEntity().getLabel() : a.getToEntity().getID()) + (a.getMark() != 1 ? ("(" + a.getMark() + "), ") : ", "));
            }
        }
    }
    public void setExampleNet() throws NegativeMarkingException, NotAnArcException {       //no way to code this in easier way

        this.addTransition(new Transition("t1"));
        this.createClassicArc(new Transition("t2"), new Place("p1"), 5);
        Transition t3 = new Transition("t3");
        this.createClassicArc(new Place("p2"), t3);
        Place p3 = new Place("p3", 1);
        this.addPlace(p3);
        Transition t4 = new Transition("t4");
        this.addTransition(t4);
        this.createClassicArc(p3, t4);
        this.createClassicArc(t4, p3, 2);
        Place p4 = new Place("p4", 1);
        Place p5 = new Place("p5", 5);
        Place p6 = new Place("p6");
        Place p7 = new Place("p7");
        this.addPlace(p4);
        this.addPlace(p5);
        this.addPlace(p6);
        this.addPlace(p7);
        Transition t5 = new Transition("t5");
        this.addTransition(t5);
        this.createResetArc(p5, t5);
        this.createClassicArc(p4, t5);
        this.createClassicArc(t5, p6);
        this.createClassicArc(t5, p7);
    }

// just for testing
    public void setExampleNet2() throws NotAnArcException, NegativeMarkingException {
        Place p1 = new Place("p1",5);
        Transition t1 = new Transition("t1");
        this.createClassicArc(p1,t1);
        this.createResetArc(p1,t1);
    }
}
