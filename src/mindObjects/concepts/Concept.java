package mindObjects.concepts;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import mindObjects.ComposedMindObject;
import mindObjects.MindObject;

/**
 * Created by e1027424 on 28.12.15.
 */
public abstract class Concept extends ComposedMindObject {
    protected final Referable subject;

    Concept(Referable subject){
    	super();

        this.subject = subject;
    }

    public Referable getSubject() {
        return subject;
    }

    @Override
    public Set<MindObject> getDirectSubComponents(){
        Set<MindObject> components= new HashSet<>();
        
    	components.add(subject.getReference());

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
        Concept rhs = (Concept) obj;
        return new EqualsBuilder()
                //           .appendSuper(super.equals(obj))
                .append(subject, rhs.subject)
                .isEquals();
    }

    @Override
	public int hashCode() {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(17, 37).
                append(subject).
                toHashCode();
    }
}
