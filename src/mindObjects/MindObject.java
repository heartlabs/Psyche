package mindObjects;

import psyche.AssociationGraph;

import java.util.*;

import mindObjects.concepts.Referable;

/**
 * Created by Aaron on 20.12.2015.
 */
public abstract class MindObject implements Referable {

    public MindObject(){
    }

    @Override
    public String toString(){
        return  express();
    }

    // only direct subcomponents excluding this
    public abstract Set<MindObject> getDirectSubComponents();

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

    protected final double unfoldedEdgesIntensity(){
        return 1.0d;
    }

    public void unfoldInto(AssociationGraph mindset){
        if (!mindset.containsVertex(this)){
            for (MindObject component: getDirectSubComponents()){
                component.unfoldInto(mindset);

                double intensity = unfoldedEdgesIntensity();

                mindset.addAssociation(this, component, intensity);
            }
        }
    }

   
    
    @Override
    public MindObject getReference() {
    	return this;
    }

}
