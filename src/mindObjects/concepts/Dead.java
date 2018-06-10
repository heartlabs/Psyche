package mindObjects.concepts;

import mindObjects.MindObject;

/**
 * Created by e1027424 on 11.01.16.
 */
public class Dead extends Concept {
    public Dead(MindObject subject) {
        super(subject);
    }

    @Override
    public String express() {
        return subject.express() + " is dead";
    }
}
