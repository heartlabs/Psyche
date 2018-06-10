package Tests;

import org.junit.Test;
import psyche.AssociationGraph;
import mindObjects.Global;
import mindObjects.concepts.Having;
import mindObjects.Someone;
import mindObjects.Something;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by e1027424 on 29.12.15.
 */
public class AssociationGraphTest {
    private final Someone a = new Someone("a");
    private final Someone b = new Someone("b");
    private final Something c = new Something("c");
    private final Something d = new Something("d");

    private final Having hac = new Having(a,c);
    private final Having someoneHasSomething = new Having(Global.someone, Global.something);



    @Test
    public void testMatching(){
        AssociationGraph graph = new AssociationGraph();

        graph.addAssociation(a,b,1d);

        hac.unfoldInto(graph);

        assertTrue(graph.find(someoneHasSomething).contains(hac));
        assertFalse(graph.find(hac).contains(someoneHasSomething));
        assertFalse(graph.find(someoneHasSomething).contains(a));



    }

    @Test
    public void testIntersect(){
        AssociationGraph graph1 = new AssociationGraph();
        AssociationGraph graph2 = new AssociationGraph();
        AssociationGraph graph3 = new AssociationGraph();

        graph1.addAssociation(a,b,1d);
        graph1.addAssociation(c,d,1d);

        graph2.addAssociation(a,d,1d);
        graph2.addAssociation(b,c,1d);

        graph3.addAssociation(a,b,0d);
        graph3.addAssociation(c,b,0d);
        graph3.addAssociation(a,d,0d);

        AssociationGraph i12 = graph1.intersect(graph2);
        AssociationGraph i13 = graph1.intersect(graph3);
        AssociationGraph i11 = graph1.intersect(graph1);

        AssociationGraph i21 = graph2.intersect(graph1);
        AssociationGraph i23 = graph2.intersect(graph3);

        assertTrue(i12.vertexSet().isEmpty());
        assertTrue(i11.edgeSet().size() == graph1.edgeSet().size());
        assertTrue(i12.edgeSet().size() == i21.edgeSet().size());

        assertTrue(i13.containsEdge(a,b));
        assertTrue(i13.edgeSet().size() == 1);

        assertTrue(i23.containsEdge(a,d));
        assertFalse(i23.containsEdge(c,b));
        assertTrue(i23.edgeSet().size() == 1);
    }
}
