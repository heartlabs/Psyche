package mindObjects.concepts;

import mindObjects.MindObject;

/**
 * Created by e1027424 on 11.01.16.
 */
public class Asking extends ConceptOfExpression{
    public Asking(MindObject subject, MindObject target, MindObject object) {
        super(subject, target, object);
    }

    @Override
    public String express() {
        return subject.express() + " asks " +target.express()+" about \"" + object.express() + "?\"";
    }
}
