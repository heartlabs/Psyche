package mindObjects;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aaron on 21.12.2015.
 */
public class Feeling extends MindObject {
    private final String name;
    private final HashMap<Double, String> expressMap;

    public Feeling(String name, HashMap<Double, String> expressMap) {
        this.expressMap = expressMap;
        this.name = name;
    }

    public String express(double intensity){
        String expression = "shows some other indefinite reaction";
        double expressionKey = 0;

        for (Map.Entry<Double, String> entry: expressMap.entrySet()){
            if (entry.getKey() > expressionKey && entry.getKey() <= intensity) {
                expression = entry.getValue();
                expressionKey = entry.getKey();
            }
        }

        return expression;
    }

    @Override
    public String express() {
        return name;
    }

}
