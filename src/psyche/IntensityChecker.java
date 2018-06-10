package psyche;

import java.util.*;

import org.jgrapht.DirectedGraph;

import mindObjects.MindObject;
import psyche.AssociationGraph.AssociationEdge;

public class IntensityChecker {


	private static Set<AssociationEdge> visitedEdges;
	private static DirectedGraph<MindObject, AssociationEdge> graph;

	private static void visitVertex(MindObject currentVertex, Double cumulatedIntensity){
		Double currentIntensity = getIntensity(currentVertex);

		updateIntensity(currentVertex, cumulatedIntensity);

		for (AssociationEdge edge: graph.outgoingEdgesOf(currentVertex)){
			if (visitedEdges.add(edge)){
				Double newIntensity = currentIntensity + getCumulatedIntensity(edge);
				visitVertex(edge.getTarget(), newIntensity);
			}
		}
	}


	private static Map<MindObject, Double> intensityMap;

	public static Map<MindObject, Double> getIntensityMap(AssociationGraph associations, MindObject startVertex) {
		if (!associations.containsVertex(startVertex))
			return new HashMap<>();

		graph = associations;
		visitedEdges = new HashSet<>();

		intensityMap = new HashMap<>();

//		updateIntensity(startVertex, 1.0d);

		visitVertex(startVertex, 1.0d);

		return getIntensityMap();
	}


	private static double getCumulatedIntensity(AssociationEdge edge) {
		Double sourceIntensity = getIntensity(edge.getStimulant());

		return sourceIntensity * edge.getIntensity();
	}


	private static void updateIntensity(MindObject vertex, Double intensity) {
		intensityMap.put(vertex, intensity);
	}

	private static Double getIntensity(MindObject vertex) {
		Double result = 0d;

		if (intensityMap.containsKey(vertex))
			result = intensityMap.get(vertex);

		return result;
	}

	public static Map<MindObject, Double> getIntensityMap() {
		return intensityMap;
	}
}