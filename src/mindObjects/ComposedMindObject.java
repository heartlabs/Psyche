package mindObjects;

import java.util.HashSet;
import java.util.Set;

public abstract class ComposedMindObject extends MindObject {
	@Override
	public boolean match(MindObject other) {
		if (other.getClass() != getClass())
			return false;
		
		Set<MindObject> thisComponentsCopy = new HashSet<>(getDirectSubComponents());
		
		for (MindObject otherComponent : other.getDirectSubComponents()){
			MindObject found = null;
			
			for (MindObject thisComponent: thisComponentsCopy){
				if (otherComponent.match(thisComponent)){
					found = thisComponent;
					break;
				}
			}
			
			if (found == null)
				return false;
			
			thisComponentsCopy.remove(found);
			
		}
		
		return thisComponentsCopy.isEmpty();
	}

}
