package mindObjects.concepts;

import mindObjects.MindObject;

/**
 * Created by e1027424 on 04.01.16.
 */
public class Telling extends ConceptOfExpression {
    public Telling(MindObject subject, MindObject target, MindObject object) {
        super(subject, target, object);
    }

    @Override
    public String express() {
        return subject.express() + " tells "+ target.express() + ": \"" + object.express() + "\"";
    }

}
