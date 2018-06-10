package mindObjects.concepts;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import psyche.InvalidSubtypeException;
import mindObjects.MindObject;

import java.util.Set;

/**
 * Created by e1027424 on 28.12.15.
 */
public abstract class ConceptWithObject extends Concept {
    protected final MindObject object;
    private MindObject objectSupertype;

    // use when validObject() is overridden
    protected ConceptWithObject(MindObject subject, MindObject object){
        this(subject, object, null, null);
    }

    protected ConceptWithObject(MindObject subject, MindObject object, MindObject subjectSupertype, MindObject objectSupertype) {
        super(subject, subjectSupertype);
        this.object = object;
        this.objectSupertype = objectSupertype;

        if (!validObject(object))
            throw new InvalidSubtypeException("Object has invalid type: " + object.express());
    }

    // meant to override
    protected boolean validObject(MindObject object){
        return object.isSubtypeOf(objectSupertype);
    }

    @Override
    public Set<MindObject> getDirectSubComponents(){
        Set<MindObject> components= super.getDirectSubComponents();
        components.add(object);
        return components;
    }


    @Override
    public boolean isSubtypeOf(MindObject other){
        if (other instanceof ConceptWithObject) {
            ConceptWithObject c = (ConceptWithObject) other;
            if (!object.isSubtypeOf(c.object))
                return false;
        }

        return super.isSubtypeOf(other);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ConceptWithObject rhs = (ConceptWithObject) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(object, rhs.object)
                .isEquals();
    }

    public int hashCode() {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(37, 937).
                appendSuper(super.hashCode()).
                append(object).
                toHashCode();
    }

}
