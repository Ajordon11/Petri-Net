package entities;

import exceptions.NegativeMarkingException;
import exceptions.NotAnArcException;

public class ClassicArc extends Arc {


    // If new arc have valid entry and exit point but no mark, it is automatically set to 1
    ClassicArc(Entity fromEntity, Entity toEntity) throws NotAnArcException {

        this.fromEntity = fromEntity;
        this.toEntity = toEntity;
        if (!arcPathCheck(fromEntity,toEntity)) {
            if (fromEntity.getLabel() == null || toEntity.getLabel() == null)
                throw new NotAnArcException();
            else
                throw new NotAnArcException(fromEntity, toEntity);
        }
        this.mark = 1;
        updateTransition(fromEntity,toEntity);
    }
    // ideal constructor, with entry and exit point and marking
    ClassicArc(Entity fromEntity, Entity toEntity, int mark) throws NotAnArcException, NegativeMarkingException {

        this.fromEntity = fromEntity;
        this.toEntity = toEntity;
        if (!arcPathCheck(fromEntity,toEntity)) {
            if (fromEntity.getLabel() == null || toEntity.getLabel() == null)       //checking for names
                throw new NotAnArcException();
            else
                throw new NotAnArcException(fromEntity, toEntity);
        }
        if (mark <= 0){
            throw new NegativeMarkingException();
        }
        this.mark = mark;
        updateTransition(fromEntity,toEntity);
    }
    ClassicArc() throws NotAnArcException {
        throw new NotAnArcException("Arc must have entry and exit!");
    }


}
