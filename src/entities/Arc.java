package entities;

public abstract class Arc {

    int mark;
    Entity fromEntity;
    Entity toEntity;


    Entity getFromEntity() {
        return fromEntity;
    }

    Entity getToEntity() {
        return toEntity;
    }

    public <F, T> boolean arcPathCheck(F fromEntity, T toEntity) {
        if (fromEntity instanceof Place) {
            return (toEntity instanceof Transition);
        } else if (fromEntity instanceof Transition) {
            return (toEntity instanceof Place);
        } else
            return false;
    }

    public void updateTransition(Entity fromEntity, Entity toEntity) {
        if (fromEntity instanceof Transition) {
            ((Transition) fromEntity).addOutputPlace((Place) toEntity, mark);
        } else
            ((Transition) toEntity).addInputPlace((Place) fromEntity, this );
    }

    public int getMark() {
        return mark;
    }

    public boolean isReady() {
        if (fromEntity instanceof Place)
            return (((Place) fromEntity).getMark() >= mark);
        else
            return false;
    }

}


