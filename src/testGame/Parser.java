package testGame;

import psyche.*;
import mindObjects.*;
import mindObjects.concepts.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by e1027424 on 04.01.16.
 */
public class Parser {

    private static MindObject returnFirstNotNullOrPanic(String expression, MindObject ... objects){
        for (MindObject object: objects){
            if (object != null)
                return object;
        }

        throw new ParsingException("Did not understand: " + expression);
    }

    public static MindObject parseExpression (Someone subject, String expression) {
        //      Log.debug("Parsing expression '" + expression + "'");

        expression = expression.replaceAll("\\s+", " ").trim(); // represent all whitespaces as one space

        return returnFirstNotNullOrPanic
                (
                        expression,
                        Global.get(expression),
                        getSomething(subject, expression),
                        getConcept(subject, expression)
                );
    }

    public static Something getSomething(Someone subject, String expression){
        if (expression.matches("[a-zA-Z]+")) {

            Set<Something> matches = subject.getPsyche().getByName(expression);
            if (matches.size() > 1)
                throw new ParsingException("There are several occurrences of " + expression + " in " + subject.express() + "'s mind");

            if (matches.size() == 1)
                return matches.iterator().next();
        }

        return null;
    }




    public static Concept getConcept(final Someone subject, final String rawExpression){
        Pattern cidFinder = Pattern.compile("^§[A-Z][a-z]*");
        Matcher matcher =  cidFinder.matcher(rawExpression);
        String conceptIdentifier;

        if (matcher.find())
            conceptIdentifier = matcher.group(0);
        else
            return null;
//            throw new ParsingException("Concept Identifier must start with §[Capital Letter]");

        String expression = rawExpression.replace(conceptIdentifier, "").trim();

        //    Log.debug("concept '" + expression + "'");


        List<String> parts = getParts(expression);
        List<MindObject> parameters = new ArrayList<>();

        for (String parameter: parts){
            parameters.add(parseExpression(subject, parameter));
        }

        return createConcept(subject, conceptIdentifier, parameters);
    }

    public static Concept createConcept(Someone target, String contextIdentifier, List<MindObject> arguments) {
        try {
            switch (contextIdentifier) {
                case ("§H"):case ("§Have"):
                    return new Having(arguments.get(0), arguments.get(1));
                case("§T"):case ("§Tell"):
                    return new Telling(arguments.get(0), arguments.get(1), arguments.get(2));
                case("§A"):case ("§Ask"):
                    return new Asking(arguments.get(0), arguments.get(1), arguments.get(2));
                case("§K"):case ("§Kill"):
                    return new Killing(arguments.get(0), arguments.get(1));
                case("§D"):case ("§Dead"):
                    return new Dead(arguments.get(0));
                case("§L"):case ("§Love"):
                    return new Loving(arguments.get(0), arguments.get(1));
                default:
                    throw new ParsingException("Invalid contextIdentifier: " + contextIdentifier);
            }
        }catch (InvalidSubtypeException ex){
            throw new InvalidSubtypeException("Error while creating Concept " + contextIdentifier + ": " + ex.getMessage());
        }catch (IndexOutOfBoundsException ex){
            // Warning: catches ALL IOBExs
            throw new ParsingException("There are more parameters needed for creating " + contextIdentifier);
        }

    }

    // TODO Make sth proper
    public static List<String> getParts(String expression){
        String currentPart = "";
        List<String> parts = new ArrayList<>();
        int parenthesesLevel = 0;

        for (Character c: expression.toCharArray()){
            if (parenthesesLevel == 0 && c == '(' && !currentPart.isEmpty())
                throw new ParsingException("Space before '(' was forgotten in " + expression);

            if (parenthesesLevel == 0) {
                if (c == '(')
                    parenthesesLevel++;
                else if (c == ')')
                    throw new ParsingException("')' without matching '('");
                else if (c == ' ' && currentPart.length() > 0) {
                    parts.add(currentPart);
                    currentPart = "";
                } else {
                    currentPart += c;
                }
            }else{

                if (parenthesesLevel > 1 || c != ')')
                    currentPart += c;

                if (c == '(')
                    parenthesesLevel++;
                else if (c == ')' ) {
                    parenthesesLevel--;

                    if (currentPart.length() > 0 && parenthesesLevel == 1) {
                        parts.add(currentPart);
                        currentPart = "";
                    }
                }
            }
        }

        if (!currentPart.isEmpty())
            parts.add(currentPart);

        return parts;
    }

    public static List<MindObject> parseParameters(Someone subject, List<String> parameters){
        List<MindObject> result = new ArrayList<>();

        for (String parameter: parameters){
            result.add(parseExpression(subject, parameter));
        }

        return result;
    }
}
