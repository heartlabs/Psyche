package mindObjects;

import psyche.AssociationGraph;
import psyche.Mood;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by e1027424 on 28.12.15.
 */
public class Experience extends ComposedMindObject {
    private final AssociationGraph content;
    private final Mood connectedEmotions;
    private final int time = 0;
    private double intensity;

    public Experience(MindObject content, double intensity, Mood connectedEmotions){
        this(new AssociationGraph(content), intensity, connectedEmotions);
    }

    public Experience(AssociationGraph content, double intensity, Mood connectedEmotions) {
        super();
        this.content = content;
        this.intensity = intensity;
        this.connectedEmotions = connectedEmotions;
    }

    @Override
    public String express() {
        return "the eperience of: " + content.expressGraph() + " (" + connectedEmotions.express() + ")";
    }

    public void showContentGraph(){
        content.show("Experience Content");
    }

    @Override
    public Set<MindObject> getDirectSubComponents(){
        Set<MindObject> contentObjects = new HashSet<>(content.vertexSet());
        contentObjects.addAll(connectedEmotions.get().keySet());

        return contentObjects;
    }

    public void applyToMindset(AssociationGraph mindset){
        mindset.addVertex(this);

        Set<MindObject> contentObjects = getDirectSubComponents();

        for (MindObject source: contentObjects){

            for (MindObject o: source.getComponents()){
                if (mindset.containsVertex(o))
                    mindset.decreaseIntensities(o);

                // TODO better
                if (!(o instanceof AbstractTemplate))
                    mindset.addAssociation(o, this, intensity);
            }

        }

    }
}
