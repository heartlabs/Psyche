package mindObjects.concepts;

import mindObjects.Global;
import mindObjects.MindObject;

/**
 * Created by e1027424 on 28.12.15.
 */
public class Having extends ConceptWithObject {

    public Having(MindObject subject, MindObject posessedObject) {
        super(subject, posessedObject, Global.something, Global.something);
    }



    @Override
    public String express() {
        return subject.express() + " has a " + object.express();
    }


}
