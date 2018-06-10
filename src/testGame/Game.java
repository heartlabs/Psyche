package testGame;

import mindObjects.concepts.Having;
import psyche.*;
import mindObjects.Someone;
import util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by e1027424 on 28.12.15.
 */
public class Game {
    private static Psyche psyche = new Psyche();

       public static void main(String[] args) throws IOException {
           Log.out("Game starting");

           Location testLocation = new Location("Anna and a Tree are here");
           Someone anna = UniversalKnowledge.anna;
           Someone mainCharacter = UniversalKnowledge.mainCharacter;

           testLocation.addInteraction(anna);
           testLocation.addInteraction(UniversalKnowledge.tree);
           testLocation.addInteraction(mainCharacter);



           mainCharacter.inject(UniversalKnowledge.get());
           mainCharacter.getPublicProperties().add(new Having(mainCharacter, UniversalKnowledge.beard));

      //     mainCharacter.getPsyche().whatDoYouKnowAbout(mainCharacter).show("My Knowledge");
//           mainCharacter.addAssociation(UniversalKnowledge.anna, UniversalKnowledge.tree, 0.01);

           BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
           String command = "show";

           while (!command.equals("end")){
                try {
                    if (!command.isEmpty())
                        testLocation.processCommand(mainCharacter, command);
                }catch (ParsingException | InvalidSubtypeException ex){
                    Log.out("Confusing Language:\n\t" + ex.getMessage());
                }

               System.out.print("> ");
               command = br.readLine();
           }

           Log.out("Game finished");

    }
}
