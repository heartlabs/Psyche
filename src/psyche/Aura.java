package psyche;

import mindObjects.Someone;

/**
 * Created by e1027424 on 08.01.16.
 */
public class Aura {
    private final Mood effect;


    public Aura (Mood effect){
        this.effect = effect;
    }

    public void affect(Mood other){
        other.transist(effect);
    }

    public String express(Someone owner) {
        return effect.express(owner);
    }
}
