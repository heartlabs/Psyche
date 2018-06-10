package psyche;

import org.apache.commons.lang3.StringUtils;
import mindObjects.MindObject;

import javax.swing.*;
import java.util.*;

/**
 * Created by e1027424 on 09.01.16.
 */
public class CenteredAssociationGraph extends AssociationGraph { //SimpleDirectedWeightedGraph<MindObject, AssociationEdge> implements ExpressableGraph {
    private final Set<AssociationEdge> expressedEdges = new HashSet<>();
    private final Set<MindObject> visited = new HashSet<>();   // visited on current iteration
    private final AssociationGraph parentGraph;
    private List<AssociationEdge> currentPath = new ArrayList<>();
    private Set<List<AssociationEdge>> allPaths = new HashSet<>();

    private final Map<MindObject, Double> intensityMap = new HashMap<>();;

    private final MindObject startVertex, targetVertex;

    // NULL OBJECT
    private CenteredAssociationGraph(MindObject startVertex){
        this.startVertex = startVertex;
        this.targetVertex = null;
        this.parentGraph = null;

        addVertex( startVertex);
    }

    // Finds paths for all possible targets
    public CenteredAssociationGraph(AssociationGraph parentGraph, MindObject start){
        this(parentGraph, start, null);
    }

    // finds paths to targetVertex only
    public CenteredAssociationGraph(AssociationGraph parentGraph, MindObject start, MindObject targetVertex){
        this.parentGraph = parentGraph;
        this.startVertex = start;
        this.targetVertex = targetVertex;



        visited.add(start);

        addVertex(startVertex);

        if (parentGraph.containsVertex(start))
            visitVertex(start, 1d);

    //    addIntensity(startVertex, 1d);

    }

    private boolean endOfPath(MindObject vertex){
        if (vertex == targetVertex)
            return true;

        for (AssociationEdge edge: parentGraph.outgoingEdgesOf(vertex)){
            if (!visited.contains(parentGraph.getEdgeTarget(edge)))
                return false;
        }

        return true;
    }

    private void visitVertex(final MindObject vertex, final double cumulatedIntensity){


        addIntensity(vertex, cumulatedIntensity);
        //    Log.debug("Visiting vertex " + vertex);

        if (endOfPath(vertex)){
            if (targetVertex == null || vertex == targetVertex) {
                allPaths.add(new ArrayList<>(currentPath));
                for (AssociationEdge edge: currentPath) {
                    MindObject source = parentGraph.getEdgeSource(edge);
                    MindObject target = parentGraph.getEdgeTarget(edge);
                    AssociationEdge supergraphEdge = parentGraph.getEdge(source, target);

                    addVertex(source);
                    addVertex(target);
                    addEdge(edge.getStimulant(), edge.getTarget(), supergraphEdge);
                    setEdgeWeight(supergraphEdge, parentGraph.getEdgeWeight(supergraphEdge));
                }
            }
        }else {

            for (AssociationEdge edge : parentGraph.outgoingEdgesOf(vertex)) {
                MindObject target = parentGraph.getEdgeTarget(edge);

                //            Log.debug("outgoing: " + target);

                if (visited.add(target)) {
                    currentPath.add(edge);
                    visitVertex(target, cumulatedIntensity*edge.getIntensity());
                    visited.remove(target);
                    currentPath.remove(edge);
                }
            }
        }

    }

    private Double getIntensity(MindObject vertex) {
        Double result = 0d;

        if (intensityMap.containsKey(vertex))
            result = intensityMap.get(vertex);

        return result;
    }

    private void addIntensity(MindObject vertex, double intensity){
        double newIntensity = getIntensity(vertex) + intensity;
        intensityMap.put(vertex, newIntensity);
    }

    public Map<MindObject, Double> getIntensityMap(){
        return intensityMap;
    }

    public String expressGraph(final MindObject next, final int indents){
        String result = next.express();
        String indentChar = "...";
        String indentString = StringUtils.repeat(indentChar, indents);
        int counter = 0;

        for (AssociationEdge association: outgoingEdgesOf(next)){
            if (counter == 0) {
                if (indents > 0)
                    result += ", which";

                result += " reminds me on";

            }else {
                result += "\n" + indentString + "and also reminds me on";
            }

            if (expressedEdges.add(association)) {
                result += "\n" + indentString + indentChar;
                result += expressGraph(association.getTarget(), indents+1);
                expressedEdges.remove(association);
            }

            counter++;
        }

        return result;

    }

    @Override
    public JFrame show() {
        return null;
    }

    @Override
    public String expressGraph() {
        return expressGraph(startVertex);
    }

    protected String expressGraph(MindObject next) {
        return expressGraph(next, 0);
    }

    @Override
    public Set<MindObject> getSources(){
        Set<MindObject> source = new HashSet<>();
        source.add(startVertex);
        return source;
    }


    public static CenteredAssociationGraph nullObject(MindObject startVertex){
        return new CenteredAssociationGraph(startVertex);
    }
}

