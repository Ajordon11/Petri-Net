package exceptions;

import entities.Entity;

public class NotAnArcException extends Exception {
    public NotAnArcException(Entity fromEntity, Entity toEntity) {
        System.out.println(fromEntity.getLabel() + " and " + toEntity.getLabel() + " cant make a valid arc!");
    }

    public NotAnArcException() {
    }
    public NotAnArcException(Entity fromEntity, Entity toEntity, boolean isReset) {
        System.out.println(fromEntity.getLabel() + " and " + toEntity.getLabel() + " cant make a valid reset arc!");
    }

    public NotAnArcException(boolean isReset) {
        System.out.println("Inserted entities cannot create reset arc.");
    }
    public NotAnArcException(String message){
        System.out.println(message);
    }

    @Override
    public String getMessage() {
        return "That's not a valid arc!";
    }
}
