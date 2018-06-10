package mindObjects.concepts;

import mindObjects.MindObject;

/**
 * Created by e1027424 on 11.01.16.
 */
public class Loving extends ConceptWithObject {
    public Loving(MindObject subject, MindObject object) {
        super(subject, object);
    }

    @Override
    public String express() {
        return subject.express() + " loves <3 " + object.express();
    }
}
