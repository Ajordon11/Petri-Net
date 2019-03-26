import entities.*;
import exceptions.NegativeMarkingException;
import exceptions.NotAnArcException;

import java.security.InvalidParameterException;

public class Main{

    public static void main(String[] args) {
        PetriNet net = new PetriNet();
// 1. Po spusteni prechodu ktory nie je spustitelny sa vyhodi IllegalStateException
// 2. Pri pokuse o vytvorenie hrany medzi nevhodnymi typmi sa vyhodi NotAnArcException
// 3. Konstruktor hrany bez vstupneho a vystupneho bodu vyhodi NotAnArcExcepton
// 4. Prechod s hranou nasobnosti viac ako 1 pocita pocet znackovani
// 5. Hrana bez udania nasobnosti je automaticky 1, ak sa zada zaporne znackovanie (alebo 0) vyhodi sa NegativeMarkingException
// 6. Vytvorenie reset hrany so vstupnym bodom - prechodom vyhodi NotAnArcException

        try {
            net.setExampleNet();        // example net - podla zadania
            System.out.println("Example net set successfully.");

//            net.showFireableTransitions();
//            net.printNet();
//            net.fireTransition(1163157884);
//            net.printNet();
//            net.showFireableTransitions();

        } catch (NegativeMarkingException | InvalidParameterException | NullPointerException | NotAnArcException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }




    }
}