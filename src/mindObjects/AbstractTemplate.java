package mindObjects;

/**
 * Created by e1027424 on 28.12.15.
 */
public class AbstractTemplate extends SimpleMindObject {
    private final String description;

    public AbstractTemplate(String description){
        super();
        this.description = description;
    }

    @Override
    public String express() {
        return description;
    }


}
