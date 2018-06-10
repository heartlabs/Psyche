package psyche;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by e1027424 on 24.12.15.
 */
public class BetterWeightedDirectedGraph<V,E> extends SimpleDirectedGraph<V, E> implements WeightedGraph<V,E> {
    private Map<E, Double> weightMap = new HashMap<>();

    public BetterWeightedDirectedGraph(Class<? extends E> edgeClass) {
        super(edgeClass);
    }

    public void setEdgeWeight(E edge, double weight){
        if (containsEdge(edge))
            weightMap.put(edge, weight);
        else
            throw new IllegalArgumentException("Cannot set edge weight: Edge was not contained");
    }

    @Override
    public double getEdgeWeight(E edge){
        if (weightMap.containsKey(edge))
            return weightMap.get(edge);

        if (containsEdge(edge))
            return WeightedGraph.DEFAULT_EDGE_WEIGHT;

        throw new IllegalArgumentException("Cannot get edge weight: Edge was not contained");
    }
}
