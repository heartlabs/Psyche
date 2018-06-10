package mindObjects;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by e1027424 on 07.01.16.
 */
public class Global {
    public static final MindObject whatever = new AbstractTemplate("'Whatever'");
    public static final MindObject something = new AbstractTemplate("'Something'");
    public static final MindObject someone = new AbstractTemplate("'Someone'");

    private static Feeling desire;
    private static Feeling fear;

    private static Map<String, MindObject> nameMap = new HashMap<>();

    private static void initFeelings(){
        HashMap<Double, String> desireEx = new HashMap<>();
        HashMap<Double, String> fearEx = new HashMap<>();

        desireEx.put(0.2, "looks interested");
        desireEx.put(0.5, "wants to know more");
        desireEx.put(0.7, "has glimmering eyes");
        desireEx.put(1.0, "breathes heavily");
        desireEx.put(1.5, "expresses strong desire");
        desireEx.put(2.0, "undresses thinking about");

        fearEx.put(0.2, "looks tense");
        fearEx.put(0.5, "looks scared");
        fearEx.put(0.7, "takes a step backwards");
        fearEx.put(1.0, "shivers");
        fearEx.put(1.5, "hides anxiously");
        fearEx.put(2.0, "runs away crying in fear");

        desire  = new Feeling("desire", desireEx);
        fear = new Feeling("fear", fearEx);
    }

    private static void init(){


        initFeelings();

        nameMap.put("Something", something);
        nameMap.put("Someone", someone);
        nameMap.put("_", whatever);
        nameMap.put("desire", desire);
        nameMap.put("fear", fear);
    }

    public static MindObject get(String name){
        if (nameMap.isEmpty())
            init();

       return nameMap.get(name);
    }
}
