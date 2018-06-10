package mindObjects.concepts;

import mindObjects.MindObject;

public class MindObjectVariable implements Referable {
	private MindObject representing;
	
    @Override
    public String toString(){
        return  express();
    }

	@Override
	public String express() {
		return "MOV currently representing "+ representing.express();
	}

	@Override
	public boolean match(MindObject other) {
		return representing.match(other);
	}
	
	@Override
	public MindObject getReference() {
		return representing;
	}
}
