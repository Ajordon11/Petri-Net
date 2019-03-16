import entities.*;
import exceptions.NegativeMarkingException;


import java.security.InvalidParameterException;


public class Main{

    public static void main(String[] args) {
        PetriNet net = new PetriNet();
        try {
            net = net.setExampleNet();
        } catch (NegativeMarkingException | InvalidParameterException  e) {
            System.out.println(e.getMessage());
        }
        net.showFireableTransitions();
    }
}