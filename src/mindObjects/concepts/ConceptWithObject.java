package mindObjects.concepts;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import mindObjects.MindObject;

/**
 * Created by e1027424 on 28.12.15.
 */
public abstract class ConceptWithObject extends Concept {
    protected final Referable object;

    protected ConceptWithObject(Referable subject, Referable object){
        super(subject);
        
        this.object = object;
    }

    @Override
    public Set<MindObject> getDirectSubComponents(){
        Set<MindObject> components= super.getDirectSubComponents();
        
    	components.add(object.getReference());
        
        return components;
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

    @Override
	public int hashCode() {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(37, 937).
                appendSuper(super.hashCode()).
                append(object).
                toHashCode();
    }

}
