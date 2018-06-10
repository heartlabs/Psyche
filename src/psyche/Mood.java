package psyche;

import mindObjects.Feeling;
import mindObjects.MindObject;
import mindObjects.Someone;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by e1027424 on 08.01.16.
 */
public class Mood {
    private final Map<Feeling, Double> feelings;
    private static double CALMING_FACTOR = 0.1d;

    public Mood (){
        this.feelings = new HashMap<>();
    }

    public Mood (Map<Feeling, Double> feelings){
        this.feelings = new HashMap<>(feelings);
    }

    private void calm(){
        Map<Feeling, Double> feelingsCopy = new HashMap<>(feelings);
        for (Map.Entry<Feeling, Double> entry: feelingsCopy.entrySet()){
            Feeling feeling = entry.getKey();
            Double intensity = entry.getValue()-CALMING_FACTOR;

            if (intensity > 0)
                feelings.put(feeling, intensity);
            else
                feelings.remove(feeling);
        }
    }

    public void transist(Mood other){
        calm();

        for (Map.Entry<Feeling, Double> entry: other.feelings.entrySet()){
            Feeling feeling = entry.getKey();
            Double intensity = entry.getValue();

            // If feeling was already present, transist slowly
            // Otherwise just take over the value by surprise
            if (feelings.containsKey(feeling))
                intensity += (feelings.get(feeling) - intensity)/2d;

            feelings.put(feeling, intensity);
        }
    }

    public void addUp(Map<Feeling, Double> otherFeelings){
        for (Map.Entry<Feeling, Double> entry: otherFeelings.entrySet()){
            Feeling feeling = entry.getKey();
            Double intensity = entry.getValue();

            if (feelings.containsKey(feeling))
                intensity += feelings.get(feeling);

            feelings.put(feeling, intensity);
        }
    }



    public Map<Feeling, Double> get(){
        return new HashMap<>(feelings);
    }

    public String express(){
        String result = " ";
        for (Map.Entry<Feeling, Double> entry: feelings.entrySet()) {
            Feeling feeling = entry.getKey();
            result += feeling.express() + " ";
        }

        return result;
    }

    public String express(Someone owner){
        String result = "";//owner.express() + ":\n";

        for (Map.Entry<Feeling, Double> entry: feelings.entrySet()) {
            Feeling feeling = entry.getKey();
            result += owner.express() + " " + feeling.express(entry.getValue()) + "\n";
        }

        return result;
    }

 /*   public void emotionalize(AssociationGraph mindset, MindObject subject){
        for (Map.Entry<Feeling, Double> entry: feelings.entrySet()){
            mindset.addAssociation(subject, entry.getKey(), entry.getValue());
        }
    }*/
}
