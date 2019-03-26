package entities;

import exceptions.NotAnArcException;

public class ResetArc extends Arc {
    ResetArc(Entity fromEntity, Entity toEntity) throws NotAnArcException {
        this.fromEntity = fromEntity;
        this.toEntity = toEntity;
        if (!arcPathCheck(fromEntity,toEntity)) {
            if (fromEntity.getLabel() == null || toEntity.getLabel() == null)
                throw new NotAnArcException(true);
            else
                throw new NotAnArcException(fromEntity, toEntity, true);
        }
        this.mark = 1;
        updateTransition(fromEntity,toEntity);
    }
    public ResetArc() throws NotAnArcException {
    }

    @Override
    public void updateTransition(Entity fromEntity, Entity toEntity) {
        if (fromEntity instanceof Transition){
            ((Transition) fromEntity).addOutputPlace((Place) toEntity, mark);
        } else
            ((Transition) toEntity).addInputPlace((Place) fromEntity, this);

    }


    @Override
    public <F, T> boolean arcPathCheck(F fromEntity, T toEntity) {
        if (fromEntity instanceof Transition)
            return false;
        if (fromEntity instanceof Place) {
            return (toEntity instanceof Transition);
        }
        return false;
    }
}