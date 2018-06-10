package mindObjects;

import java.util.HashSet;
import java.util.Set;

public abstract class SimpleMindObject extends MindObject {
	@Override
	public boolean match(MindObject other) {
		return equals(other);
	}

	// only direct subcomponents excluding this
    @Override
	public Set<MindObject> getDirectSubComponents(){
        return new HashSet<>();
    }
}
