package Tests;

import org.junit.Test;
import mindObjects.MindObject;
import mindObjects.Someone;
import mindObjects.Something;
import util.Bla;

import java.util.*;

/**
 * Created by e1027424 on 29.12.15.
 */
import static org.junit.Assert.*;

public class SomeoneTest extends  MindObjectTest {

    @Test
    public void testEquals() throws Exception {
        super.testEquals(new Someone("a"), new Someone("a"));
        super.testEquals(new Someone("v"), new Someone("c"));
    }

    Someone a = new Someone("A");
    Someone b = new Someone("B");
    Something c = new Something("C");

    private void init() {
        a.resetPsyche();
        b.resetPsyche();

        b.getPsyche().__getAG().addVertex(a);
        b.getPsyche().__getAG().addVertex(b);
        b.getPsyche().__getAG().addVertex(c);

        List<String> parameters = new ArrayList<>();
        parameters.add("§H A C");

        a.react(b, "tell", parameters);
        a.react(b, "tell", parameters);
    }

    // necessary because of Memories are never equal, but their Strings are
    private Map<String,Double> stringMap (Map<MindObject, Double> associations){
        Map<String,Double> stringmap = new HashMap<>();

        for (MindObject key: associations.keySet()){
            String exp = key.express();
            Double v = associations.get(key);

            if (stringmap.containsKey(key))
                v += stringmap.get(key);

            stringmap.put(exp, v);
        }

        return stringmap;
    }

    @Test
    public void testGASS(){
        init();

        Map<String, Double> amap = stringMap(a.getPsyche().getAssociations(a));

        for (int i=0; i<100; i++){
            init();
            Map<String, Double> bmap = stringMap(a.getPsyche().getAssociations(a));

            Set<Map.Entry<String,Double>> diff = new HashSet<>(amap.entrySet());
            diff.removeAll(bmap.entrySet());

            assertTrue(Bla.setEquals(amap.keySet(), bmap.keySet()));

            assertTrue(
                    Bla.setEquals(amap.entrySet(), bmap.entrySet())
            );
        }


    }

 /*   @Test
    public void testALSP() {
        init();
        AssociationGraph ag = a.getPsyche().__getAG().unfold();

        AllLongestSimplePaths<MindObject, AssociationGraph.AssociationEdge> alsp = new AllLongestSimplePaths<>(ag, a, null);

        for (int i=0; i<100; i++){
            init();
            ag = a.getPsyche().__getAG().unfold();
            AllLongestSimplePaths<MindObject, AssociationGraph.AssociationEdge> alsp2 = new AllLongestSimplePaths<>(ag, a, null);
            assertTrue(alsp.samePaths(alsp2));
        }
    }*/

 /*   @Test
    public void testTCS() {
        init();
        AssociationGraph ag = a.getPsyche().__getAG().unfold();

        AssociationGraph tcs = ag.transitiveClosureStar(a, null);

        for (int i=0; i<100; i++){
            init();

            ag = a.getPsyche().__getAG().unfold();
            AssociationGraph tcs2 = ag.transitiveClosureStar(a, null);
            if (!tcs.contentEquals(tcs2))
                Log.debug(tcs + "\n" + tcs2);
            assertTrue(tcs.contentEquals(tcs2));
        }
    }*/
}