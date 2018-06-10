package mindObjects.concepts;

import mindObjects.MindObject;

public interface Referable {
    public String express();

    public boolean match(MindObject other);
    
    public MindObject getReference();

}
