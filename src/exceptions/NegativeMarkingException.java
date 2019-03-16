package exceptions;

public class NegativeMarkingException extends Exception {
    public NegativeMarkingException() {

    }
    public NegativeMarkingException(long ID, String name){
        System.out.print("Negative marking at "+ID + " (" + name + "). ");
    }
    @Override
    public String getMessage() {
        return "Inserted marking is invalid!";
    }
}
