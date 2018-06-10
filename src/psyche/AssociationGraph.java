package psyche;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jgrapht.WeightedGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import mindObjects.AbstractTemplate;
import mindObjects.MindObject;
import util.Bla;
import util.Log;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

public class AssociationGraph extends BetterWeightedDirectedGraph<MindObject, AssociationGraph.AssociationEdge> implements ExpressableGraph{


	private static final Double FORGETTING_FACTOR = 0.2d;

	public AssociationGraph(MindObject content) {
		this();
		addVertex(content);
	}

	public class AssociationEdge extends DefaultEdge {

		// Ensure, no double edges are created
		// TODO: Remove after finishing this class because slow
		private AssociationEdge(MindObject stimulant, MindObject target) {
			if (AssociationGraph.this.getEdge(stimulant, target) != null)
				throw new IllegalStateException("Edge already exists. I won't instantiate it again");
		}

		public MindObject getStimulant(){ return (MindObject) super.getSource(); }
		
		public MindObject getTarget(){ return (MindObject) super.getTarget(); }
		
		public double getIntensity(){ return AssociationGraph.this.getEdgeWeight(this);	}

		@Override
		public String toString(){
			return String.format("%.2f", getIntensity());
		}

		public String express() {
			return getStimulant().express() + "->" + getTarget().express();
		}
		
	    @Override
	    public boolean equals(Object obj) {
	        if (obj == null) {
	            return false;
	        }
	        if (obj == this) {
	            return true;
	        }
	        if (obj.getClass() != getClass()) {
	            return false;
	        }
	        AssociationEdge rhs = (AssociationEdge) obj;
	        return new EqualsBuilder()
	     //           .appendSuper(super.equals(obj))
	                .append(getTarget(), rhs.getTarget())
	                .append(getStimulant(), rhs.getStimulant())
	                .isEquals();
	    }

	    @Override
	    public int hashCode() {
	        // you pick a hard-coded, randomly chosen, non-zero, odd number
	        // ideally different for each class
	        return new HashCodeBuilder(17, 37).
	                append(getTarget()).
	                append(getStimulant()).
	                toHashCode();
	    }
	}

	public AssociationGraph(){
		super(AssociationEdge.class);	// Edge Factory is not working
	}

	private AssociationEdge edgeInstance(MindObject stimulator, MindObject target){
		if (containsEdge(stimulator, target))
			return getEdge(stimulator, target);

		return new AssociationEdge(stimulator, target);
	}

	// decrease intensity of all related associations to mimic "forgetting"
	public void decreaseIntensities(MindObject stimulant){
		Set<AssociationEdge> edgesToDelete = new HashSet<>();

		for (AssociationEdge edge: this.outgoingEdgesOf(stimulant)){
			Double currentWeight = getEdgeWeight(edge);
			setEdgeWeight(edge, currentWeight - FORGETTING_FACTOR);

			if (getEdgeWeight(edge) <= 0)
				edgesToDelete.add(edge);
		}

		for (AssociationEdge edge: edgesToDelete)
			removeEdge(edge);
	}

	private void addAssociation(AssociationEdge edge){
		addAssociation(edge.getStimulant(), edge.getTarget(), edge.getIntensity());
	}
	
	public void addAssociation(MindObject stimulant, MindObject target, Double weight){

		if (stimulant instanceof AbstractTemplate || stimulant instanceof AbstractTemplate)
			Log.debug("Adding AbstractTemplate Association");

		addVertex(stimulant);
		addVertex(target);

		AssociationEdge association = edgeInstance(stimulant, target);



		try {
			if (addEdge(stimulant, target, association)) {

				//		Log.debug("New Edge " + association.express());
				setEdgeWeight(association, weight);
				//		Log.debug("Set to " + getEdgeWeight(association));
			} else {

				//		Log.debug("Old Edge: " + stimulant + ":" + target + " - " + weight );

				setEdgeWeight(association, weight + getEdgeWeight(association));
			}
		}catch(Exception ex){
			// BREAKPOINT
			throw new RuntimeException("BREAKPOINT: " + ex.getMessage());
		}

	}

	public Double getTotalIntensity(MindObject from, MindObject to){
		/*AssociationGraph psy = transitiveClosureStar(from, to);

		if (! psy.containsEdge(from, to))
			return 0d;

		return psy.getEdgeWeight(psy.getEdge(from, to));*/

		return IntensityChecker.getIntensityMap(this, from).get(to);
	}

/*	// TODO: Dirty
	public AssociationGraph transitiveClosureStar(MindObject from, MindObject to){
		AssociationGraph unfolded = this.unfold();

		AllLongestSimplePaths<MindObject, AssociationEdge> asp = new AllLongestSimplePaths<>(unfolded, from, to);



		AssociationGraph psy = new AssociationGraph();
		Log.debug("########## TCS ##############");

		for (List<AssociationEdge> path: asp.getAllPaths()){
			Log.debug("\n* NEW PATH\n");
			Double intensity = 1d;

			for (AssociationEdge edge: path){
				intensity *= unfolded.getEdgeWeight(edge);

					Log.debug("Added edge " + edge.express() + "(" +  intensity+ ")");
					MindObject target = unfolded.getEdgeTarget(edge);


					psy.addAssociation(from, target, intensity);
			}
		}

		return  psy;
	}*/

	public void inject (WeightedGraph<MindObject, AssociationEdge> other){
		for (AssociationEdge edge: other.edgeSet()){
			MindObject stimulant = other.getEdgeSource(edge);
			MindObject target = other.getEdgeTarget(edge);
			Double intensity = other.getEdgeWeight(edge);

	//		Log.debug("injecting " + stimulant.express() + target.express() + intensity);

			addAssociation(stimulant, target, intensity);
		}
	}


	public JFrame show(){
		return show("AssociationGraph");
	}

	public JFrame show(String title){
		// Code taken from jgrapht page
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JGraphXAdapter<MindObject, AssociationEdge> graphAdapter =
				new JGraphXAdapter<>(this);

		mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
		layout.execute(graphAdapter.getDefaultParent());

		frame.add(new mxGraphComponent(graphAdapter));

		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);

		return frame;
	}

	private String surroundWithBrackets(String other){
		return "\n{\n" + other + "\n}\n";
	}

	public String expressGraph() {
		Set<MindObject> sources = getSources();
		String result = new String();

		if (sources.isEmpty())
			return "Nothing to talk about";

	/*	if (sources.size() > 1)
			result += "I cannot tell everything, but...\n";*/

		for (MindObject startPoint: sources) {
			AssociationGraph cag = new CenteredAssociationGraph(this, startPoint);
			result +=  cag.expressGraph();

			if (vertexSet().size() > 1)
				result += "\n";
		}

		if (vertexSet().size() > 1)
			result = surroundWithBrackets(result);

		return result;
	}

	protected String expressGraph(MindObject next) {
		return next.express() + "... etc.";
	}

	public Set<MindObject> getSources(){
		Set<MindObject> sources = new HashSet<>();

		for (MindObject vertex: vertexSet()){
			if (incomingEdgesOf(vertex).isEmpty())
				sources.add(vertex);
		}

		if (sources.isEmpty())
			sources = vertexSet();

		return sources;
	}

	public Set<MindObject> find(MindObject toFind){
		Set<MindObject> matches = new HashSet<>();

		for (MindObject vertex: vertexSet()){
			if (vertex.match(toFind))
				matches.add(vertex);
		}

		return matches;
	}

	public AssociationGraph intersect(AssociationGraph other){
		AssociationGraph intersection = new AssociationGraph();

		for (MindObject vertex: vertexSet()){
			if (other.containsVertex(vertex)){
				for (AssociationEdge edge: outgoingEdgesOf(vertex)) {
					if (other.containsEdge(edge.getStimulant(), edge.getTarget()))
						intersection.addAssociation(edge);
				}
			}
		}

		return intersection;
	}



	public AssociationGraph unfold(){
		AssociationGraph unfolded = new AssociationGraph();

		for (MindObject vertex:vertexSet()){
			vertex.unfoldInto(unfolded);
		}

		for (AssociationEdge edge: edgeSet()){
			unfolded.addAssociation(edge);
		}

		return unfolded;
	}

	// same edges and vertices
	public boolean contentEquals(AssociationGraph other){
		if (!Bla.stringRepresentationSetEquals(vertexSet(), other.vertexSet()))
			return false;
		if (!Bla.stringRepresentationSetEquals(edgeSet(), other.edgeSet())){
			Set<Bla.StringRepresentation<AssociationEdge>> diff = Bla.stringRepresentationSet(this.edgeSet());
			diff.removeAll(Bla.stringRepresentationSet(other.edgeSet()));
			return false;
		}

/*		for (AssociationEdge edge: edgeSet()){
			if (getEdgeWeight(edge) != other.getEdgeWeight(edge)){
				return false;
			}
		}*/

		return true;
/*		return vertexSet().containsAll(other.vertexSet())
				&& other.vertexSet().containsAll(vertexSet())
				&& edgeSet().containsAll(other.edgeSet())
				&& other.edgeSet().containsAll(edgeSet());*/
	}
}
