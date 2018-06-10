package psyche;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import mindObjects.*;

import javax.swing.*;
import java.awt.*;

public class Main {
	
    static Someone mainCharacter = new Someone("You");
    static Someone anna = new Someone("Anna");
    static Someone john = new Someone("John");

    static Something beard = new Something("Beard");
    static Something tree = new Something("Tree");

//    static Feeling desire = new Feeling("desire");
//    static Feeling fear = new Feeling("fear");

    public static void main(String[] args) {


    }
    

    private static void createAndShowGui(Graph graph) {
        JFrame frame = new JFrame("DemoGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JGraphXAdapter<MindObject, AssociationGraph.AssociationEdge> graphAdapter =
                new JGraphXAdapter<>(graph);

        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        frame.setFont(new Font("Andale Mono", Font.LAYOUT_LEFT_TO_RIGHT, 12));

        frame.add(new mxGraphComponent(graphAdapter));

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}
