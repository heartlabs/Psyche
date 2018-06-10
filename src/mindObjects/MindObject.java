package mindObjects;

import psyche.AssociationGraph;

import java.util.*;

/**
 * Created by Aaron on 20.12.2015.
 */
public abstract class MindObject {

    private final Set<MindObject> supertypes = new HashSet<>();

    public abstract String express();

    public MindObject(){
        // No call of this constructor from AbstractTemplate derivates
        addSupertype(Global.whatever);
    }

    @Override
    public String toString(){
    /*    String output = "";
        String description = express();

        final int MAX_LENGTH = 100;

        for (String line: description.split("\n")) {
            output += StringUtils.rightPad(line, MAX_LENGTH-line.length(), " ") + "\n";
        }

        return output;*/
        return  express();
    }

    // only direct subcomponents excluding this
    public Set<MindObject> getDirectSubComponents(){
        return new HashSet<>();
    }

    // all Components and subcomponents including this
    // NOTE: Might be better if this is not included
    public final Set<MindObject> getComponents(){
        Set<MindObject> components = new HashSet<>();

        components.add(this);

        for (MindObject subComponent: getDirectSubComponents()){
            components.add(subComponent);
            components.addAll(subComponent.getDirectSubComponents());
        }

        return components;
    }

    protected double unfoldedEdgesIntensity(){
        return 1.0d;
    }

    public void unfoldInto(AssociationGraph mindset){
        if (!mindset.containsVertex(this)){
            for (MindObject component: getDirectSubComponents()){
                component.unfoldInto(mindset);

//                int numberSubcomponents = component.getDirectSubComponents().size();
                double intensity = unfoldedEdgesIntensity();  //1d/(Math.max(numberSubcomponents, 1));   // to make sure no total Intensity>1

                // TODO better
                if (!(component instanceof AbstractTemplate))
                    mindset.addAssociation(this, component, intensity);
            }
        }

    }

    // matches a sub-Object or identical
    // i.e. Something matches Someone matches John and vice versa
    public boolean match(MindObject other){
        return other.isSubtypeOf(this) || isSubtypeOf(other);
    }

    public boolean isSubtypeOf(MindObject other){
        return this.equals(other) || recursiveSupertypeScan(other);
    }

    private boolean recursiveSupertypeScan(MindObject other){
        if (supertypes.contains(other))
            return true;

        for (MindObject m: supertypes){
            if (m==null)
                throw new RuntimeException("Supertype is null: " + express());
            if (m.recursiveSupertypeScan(other))
                return true;
        }

        return false;
    }

    protected void addSupertype(MindObject supertype){
        if (supertype != this && supertype != null)  // 'this' is implicitly always supertype, null NEVER
            supertypes.add(supertype);
    }

}
