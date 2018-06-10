package mindObjects.concepts;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import psyche.InvalidSubtypeException;
import mindObjects.MindObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by e1027424 on 28.12.15.
 */
public abstract class Concept extends MindObject {
    protected final MindObject subject;
    private final MindObject subjectSupertype;

    Concept(MindObject subject){
        this(subject, null);
    }

    Concept(MindObject subject, MindObject subjectSupertype){
        super();

        this.subjectSupertype = subjectSupertype;

        if (!validSubject(subject))
            throw new InvalidSubtypeException("Subject has no valid type: " + subject.express());

        this.subject = subject;
    }

    public MindObject getSubject() {
        return subject;
    }

    @Override
    public Set<MindObject> getDirectSubComponents(){
        Set<MindObject> components= new HashSet<>();
        components.add(subject);

        return components;
    }

 //   public abstract void applyToMindset(AssociationGraph mindset, Double intensity);

    protected boolean validSubject(MindObject subject){
        return subject.isSubtypeOf(subjectSupertype);
    }

    @Override
    public boolean isSubtypeOf(MindObject other){
        if (other == null)
            return false;

        if (super.isSubtypeOf(other))
            return true;

        if (this.getClass() == other.getClass()) {
            Concept c = (Concept) other;

            return subject.isSubtypeOf(c.subject);
        }

        return false;
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

    public int hashCode() {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(17, 37).
                append(subject).
                toHashCode();
    }
}
