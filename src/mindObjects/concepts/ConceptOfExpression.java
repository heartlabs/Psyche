package mindObjects.concepts;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import mindObjects.MindObject;

import java.util.Set;

/**
 * Created by e1027424 on 11.01.16.
 */
public abstract class ConceptOfExpression extends ConceptWithObject {
    protected final Referable target;

    protected ConceptOfExpression(Referable subject, Referable target, Referable object) {
        super(subject, object);
        this.target = target;
    }

    @Override
    public Set<MindObject> getDirectSubComponents(){
        Set<MindObject> components= super.getDirectSubComponents();
        
    	components.add(target.getReference());
        
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
        ConceptOfExpression rhs = (ConceptOfExpression) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(target, rhs.target)
                .isEquals();
    }

    @Override
	public int hashCode() {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(127, 67).
                appendSuper(super.hashCode()).
                append(target).
                toHashCode();
    }
}