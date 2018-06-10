package mindObjects;

/**
 * Created by e1027424 on 28.12.15.
 */
public class AbstractTemplate extends MindObject {
    private final String description;

    public AbstractTemplate(String description){
        super();
        this.description = description;
    }

    public AbstractTemplate(MindObject supertype, String description){
        this(description);
        addSupertype(supertype);

    }

    @Override
    public String express() {
        return description;
    }


}
