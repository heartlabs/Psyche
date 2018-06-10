package mindObjects.concepts;

import mindObjects.Global;
import mindObjects.MindObject;

/**
 * Created by e1027424 on 11.01.16.
 */
public class Killing extends ConceptWithObject {
    public Killing(MindObject subject, MindObject object) {
        super(subject, object, Global.someone, Global.someone);
    }

    @Override
    public String express() {
        return subject.express() + " kills " + object.express();
    }
}
