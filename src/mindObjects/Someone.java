package mindObjects;

import psyche.*;
import mindObjects.concepts.Asking;
import mindObjects.concepts.Telling;
import testGame.*;
import util.*;

import java.util.*;

/**
 * Created by Aaron on 21.12.2015.
 */
public class Someone extends Something {
    private Psyche psyche = new Psyche();
    private static double NOTICE_INTENSITY = 0.5d;
//    private Aura aura = new Aura();

    public Someone(String name, MindObject supertype){
        this(name);
        addSupertype(supertype);
    }

    public Someone(String name) {
        super(name);

        addSupertype(Global.someone);
    }

    public void addAssociation(MindObject from, MindObject to, Double intensity){
        psyche.addAssociation(from, to, intensity);
    }

    public void inject(AssociationGraph associations){
        psyche.inject(associations);
    }

    public Psyche getPsyche() {
        return psyche;
    }

 //   @Override
    public String ask(Someone talker, MindObject subject){
        Log.out("You ask me about " + subject);

        String result = getPsyche().whatDoYouKnowAbout(subject).expressGraph();

        Asking question = new Asking(talker,this, subject);
        getPsyche().notice(question, NOTICE_INTENSITY);

        return result;
    }

//    @Override
    public String tell(Someone talker, MindObject subject){
        Telling story = new Telling(talker,this, subject);
        getPsyche().notice(story, NOTICE_INTENSITY);

        return "I understand: " + subject.express();
    }


//    @Override
    public String flatAssociations(MindObject talker, MindObject subject){
        String result = "With " + subject.express() + " I associate:\n";
        for (Map.Entry<MindObject, Double> entry: getPsyche().getAssociations(subject).entrySet()){
            String value = Bla.format(entry.getValue());
            result += value + "..." + entry.getKey().express() + "\n";
        }

        Asking question = new Asking(talker, this, subject);
        getPsyche().notice(question, NOTICE_INTENSITY);

        return result;
    }



    @Override
    public String describeAura(){
        return getAura().express(this);
    }

    private String reactToSimpleCommands(Someone talker, String command, List<String> parameters){
        MindObject subject = Parser.parseExpression(talker, parameters.get(0));

        psyche.reactEmotionalTo(subject);

        if (command.equals("tell")){
            return tell(talker, subject);
        }
        if (command.equals("ask")){
            return ask(talker, subject);
        }
        if (command.equals("ask2")){
            return flatAssociations(talker, subject);
        }
        if (command.equals("show")){
            showPsycheGraph(subject);
            return "Showing Graph for " + subject.express() + "...";
        }


        throw new ParsingException("Unknown command: " + command + "(2)");
    }

    private String reactToMMOPCommands(Someone talker, String command, List<MindObject> parameters){
        psyche.reactEmotionalTo(parameters);

        if (command.equals("show")){
            getPsyche().whatDoYouKnowAbout(parameters).show();
            return "Showing Graph for " + parameters + "...";
        }
        if (parameters.size() == 2) {

            if (command.equals("explain")) {
                //           Log.debug("Explaining " + parameters.get(0) + "->" + parameters.get(1));
                ExpressableGraph explanation = psyche.explain(parameters.get(0), parameters.get(1));
//                explanation.show();
                return explanation.expressGraph();
            }
            if (command.equals("add")) {
                psyche.addAssociation(parameters.get(0), parameters.get(1), 0.5d);
                return "TESTING: Added association";
            }
        }

        throw new ParsingException("Unknown command: " + command + "(3+)");
    }

    @Override
    public String react(Someone talker, String command, List<String> parameters){
        talker.affectEmotionsOf(this);
        psyche.reactEmotionalTo(talker.getPublicProperties());

        

        if (parameters.size() == 1)
            return reactToSimpleCommands(talker, command, parameters);
        else if (parameters.size() > 1) {
            return reactToMMOPCommands(talker, command, Parser.parseParameters(talker, parameters));
        } else if (command.equals("show")) {
            getPsyche().show();
            return "Showing Graph...";
        }else if (command.equals("show2")) {
            getPsyche().showAll();
            return "Showing unfolded Graph...";
        }

        throw new ParsingException("Unknown command: " + command + "(?)");


    }

    @Override
    public void affectEmotionsOf(Someone other){
//        Log.debug(this + ": Affectioing emotions of " + other);
        getAura().affect(other.getPsyche().getMood());
//        Log.debug(describeAura());
    }

    private Aura getAura(){
        return new Aura(psyche.getMood());
    }


//    @Override
    public void showPsycheGraph(MindObject subject){
        getPsyche().whatDoYouKnowAbout(subject).show();
    }

    public void resetPsyche(){
        psyche = new Psyche();
    }

}
