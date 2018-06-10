package testGame;

import org.apache.commons.lang3.StringUtils;
import mindObjects.Someone;
import mindObjects.Something;
import util.Log;

import java.util.*;

/**
 * Created by e1027424 on 04.01.16.
 */
public class Location {
    private final String description;
    private final Map<String, Something> interactions = new HashMap<>();

    public Location(String description){
        this.description = description;

    }

    public void addInteraction (Something interaction){
        interactions.put(interaction.getName(), interaction);
    }

    public String getDescription(){
        return description;
    }



    public void processCommand(Someone mainCharacter, String command){
        if (processSimpleCommand(command))
            return;

        if (command.equals("D1")){
            processCommand(mainCharacter, "tell Anna (§H John Tree)");
            processCommand(mainCharacter, "tell Anna (§H John Tree)");
            processCommand(mainCharacter, "ask2 Anna John");
            return;
        }
        if (command.equals("mood")){
            Log.out(mainCharacter.describeAura());
            return;
        }


        String[] tokens = command.trim().split("\\s+");

        if (tokens.length < 2)
            throw new ParsingException("A command must consist of Type + Adressed [+ Parameter]");

        String commandWord = tokens[0];
        Something adressed = interactions.get(tokens[1]);

        if (adressed == null)
            throw new ParsingException("Who is " + tokens[1]);

        String[] parameterArr = Arrays.copyOfRange(tokens, 2, tokens.length);
        String parameterString = StringUtils.join(parameterArr, " ");

        List<String> parameterList = Parser.getParts(parameterString);

/*        Log.debug(commandWord);
        Log.debug(adressed.express());
        Log.debug(parameterString);*/


        Log.out(adressed.react(mainCharacter, commandWord, parameterList));
        Log.out("---\n");

        adressed.affectEmotionsOf(mainCharacter);

        Log.out(adressed.describeAura());
        if (adressed != mainCharacter)
            Log.out(mainCharacter.describeAura());


    }

    private boolean processSimpleCommand(String command) {
        if (command.equals("show")){
            Log.out(description);
            for (String i: interactions.keySet())
                Log.out("there is: [" + i + "]");
        }else{
            return false;
        }


        return true;
    }

}
