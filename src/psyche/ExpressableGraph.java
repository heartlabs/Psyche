package psyche;

import org.jgrapht.WeightedGraph;
import mindObjects.MindObject;

import javax.swing.*;

/**
 * Created by e1027424 on 09.01.16.
 */
public interface ExpressableGraph extends WeightedGraph<MindObject, AssociationGraph.AssociationEdge> {
    public JFrame show();
    public String expressGraph();
}
