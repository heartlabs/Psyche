package psyche;


import mindObjects.*;
import mindObjects.concepts.Concept;
import util.Bla;
import util.Log;

import javax.swing.*;
import java.util.*;

/**
 * Created by e1027424 on 28.12.15.
 */
public class Psyche {
    private AssociationGraph associationGraph = new AssociationGraph();
    private Mood mood = new Mood();

    public void addAssociation(MindObject stimulant, MindObject target, Double intensity){
        associationGraph.addAssociation(stimulant, target, intensity);
    }

    public void getAssociationIntensity(MindObject stimulant, MindObject target){
        associationGraph.getTotalIntensity(stimulant, target);
    }



    public Memory createMemory (Concept noticed, Double intensity){
        AssociationGraph memoryGraph = new AssociationGraph();
        memoryGraph.addVertex(noticed);

//        mood.emotionalize(memoryGraph, noticed);

        Memory memory = new Memory(memoryGraph, intensity, mood);

        return memory;
    }

    public void notice (Memory memory){
        memory.applyToMindset(associationGraph);
    }

    public void notice (Concept noticed, Double intensity){
        Memory memory = createMemory(noticed, intensity);
        notice(memory);
    }

    public void inject(Psyche other){
        associationGraph.inject(other.associationGraph);
    }
    public void inject(AssociationGraph other){
        associationGraph.inject(other);
    }

    public JFrame show(){
        return associationGraph.show();
    }

    public JFrame showAll() {
        return  associationGraph.unfold().show();
    }

    // Uses big/unfolded Graph
    public Map<MindObject, Double> getAssociations(MindObject stimulant){
        AssociationGraph graphPlusMatches = associationGraph.unfold();

        // Try to find more general matches if stimulant is not contained
        if (!graphPlusMatches.containsVertex(stimulant)) {
            for (MindObject match : graphPlusMatches.find(stimulant)) {
                if (match != stimulant)
                    graphPlusMatches.addAssociation(stimulant, match, 1d);
            }
        }

        CenteredAssociationGraph centered = new CenteredAssociationGraph(graphPlusMatches, stimulant, null);

        return Bla.sortByValue(centered.getIntensityMap());
    }

    // Uses small/folded Graph
    public ExpressableGraph whatDoYouKnowAbout(List<MindObject> objects){
        AssociationGraph knowledge = associationGraph;

        for (MindObject object: objects){
            knowledge = knowledge.intersect(whatDoYouKnowAbout(object));
        }

        return knowledge;
    }

    // Uses small/folded Graph
    public CenteredAssociationGraph whatDoYouKnowAbout(MindObject object){
        Set<MindObject> matches = associationGraph.find(object);
        AssociationGraph knowledge = new AssociationGraph();

        if (matches.isEmpty())
            return CenteredAssociationGraph.nullObject(object);

        for (MindObject match: matches){

            knowledge.inject(explain(match, null));

            if (!match.equals(object)) {
                knowledge.addAssociation(object, match, 1d);
            }
        }

        return new CenteredAssociationGraph(knowledge, object);
    }

    // target==null => Uses small/folded Graph
    // otherwise    => uses big/unfolded graph
    public CenteredAssociationGraph explain (MindObject stimulant, MindObject target){
        AssociationGraph associations = associationGraph;

        if (target != null)
            associations = associationGraph.unfold();

        return new CenteredAssociationGraph(associations, stimulant, target);
    }

    public Something getById(int id){
        for (MindObject vertex: associationGraph.vertexSet()){
            if (vertex instanceof Something){
                Something object = (Something) vertex;

                if (object.getId() == id)
                    return object;
            }
        }

        return null;
    }

    public Set<Something> getByName(String name){
        Set<Something> results = new HashSet<>();

        for (MindObject vertex: associationGraph.vertexSet()){
            if (vertex instanceof Something){
                Something object = (Something) vertex;

                if (object.getName().equals(name))
                    results.add(object);
            }
        }

        return results;
    }

    public Mood getFeelingsTowards(Collection<? extends MindObject> subjects){
        Mood feelings = new Mood();

        for (MindObject subject: subjects){
            feelings.addUp(getFeelingsTowardsSpecificObject(subject));
        }

        return feelings;
    }

    public Mood getFeelingsTowards(MindObject subject){
        return getFeelingsTowards(subject.getComponents());
    }

    private Map<Feeling, Double> getFeelingsTowardsSpecificObject(MindObject subject){
        Map<Feeling, Double> feelings = new HashMap<>();

        for (Map.Entry<MindObject, Double> entry: getAssociations(subject).entrySet()) {
            if (entry.getKey() instanceof Feeling) {
                feelings.put((Feeling) entry.getKey(), entry.getValue());
            }
        }

        return feelings;
    }

    public void reactEmotionalTo(Collection<? extends MindObject> subjects){
        Mood reaction = getFeelingsTowards(subjects);
        mood.transist(reaction);

        Log.debug("Reacting emotional towards " + subjects);
        Log.debug(reaction.express(new Someone("DEBUG")));
    }

    public void reactEmotionalTo(MindObject subject){
        Mood reaction = getFeelingsTowards(subject);
        mood.transist(reaction);

        Log.debug("Reacting emotional towards " + subject);
        Log.debug(reaction.express(new Someone("DEBUG")));
    }

    public void reactEmotionalTo(Aura other){
        other.affect(this.mood);
    }

    public Mood getMood(){
        return mood;
    }

    public AssociationGraph __getAG(){
        // WARNING: For Testing Only
        return  associationGraph;
    }

}
