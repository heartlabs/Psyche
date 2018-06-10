package mindObjects;

import mindObjects.concepts.Concept;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Aaron on 21.12.2015.
 */
public class Something extends SimpleMindObject {

    private static int lastId = Integer.MIN_VALUE;

    private final String name;
    private final int id = ++lastId;

    private final Set<Concept> publicProperties = new HashSet<>();

    public Something(String name) {
        super();

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId(){
        return id;
    }

    public Set<Concept> getPublicProperties(){
        return publicProperties;
    }

    public String describeAura(){
        return "The " + express() + " doesn't seem to give any reaction ";
    }

    public String react(Someone talker, String command, List<String> parameters){
        return "The " + express() + " doesn't seem to give any reaction ";
    }

    @Override
    public String express() {
        return name;
    }

    public void affectEmotionsOf(Someone other){
        return; // Things can't currently have an Aura
    }

/*    public void showPsycheGraph(MindObject subject) {
        Log.out("Inanimate " + express() + " doesn't have any psyche to show");
    }*/
}
