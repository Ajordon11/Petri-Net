package exceptions;

import entities.Entity;

public class NotAnArcException extends Exception {
    public NotAnArcException(Entity fromEntity, Entity toEntity) {
        System.out.println(fromEntity.getLabel() + " and " + toEntity.getLabel() + " cant make a valid arc!");
    }

    public NotAnArcException() {
    }
    public NotAnArcException(String message){
        System.out.println(message);
    }

    @Override
    public String getMessage() {
        return "That's not a valid arc!";
    }
}
