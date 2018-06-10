package testGame;

import psyche.*;
import mindObjects.*;
import mindObjects.concepts.Dead;
import mindObjects.concepts.Having;
import mindObjects.concepts.Killing;
import mindObjects.concepts.Loving;

/**
 * Created by e1027424 on 29.12.15.
 */
public class UniversalKnowledge {
    static Someone mainCharacter = new Someone("X");
    static Someone anna = new Someone("Anna");
    static Someone john = new Someone("John");

    static Someone mother = new Someone("Mother");
    static Someone father = new Someone("Father");

    static Something beard = new Something("Beard");
    static Something tree = new Something("Tree");
//    static Something oak = new Something("Oak", tree);


    static Feeling desire = (Feeling) Global.get("desire");
    static Feeling fear = (Feeling) Global.get("fear");

    public static AssociationGraph get(){
        AssociationGraph knowledge = new AssociationGraph();

        knowledge.addAssociation(mainCharacter, anna, 0.01d);

        knowledge.addAssociation(new Having(Global.someone, beard), desire, 0.4d);
        knowledge.addAssociation(john, fear, 0.5d);
        knowledge.addAssociation(beard, john, 0.3d);

        knowledge.addAssociation(anna, tree, 0.01);

        Dead someoneIsDead = new Dead(Global.someone);
        Killing someoneKillsSomeone = new Killing(Global.someone, Global.someone);
        Loving someoneLovesSomething = new Loving(Global.someone, Global.something);
        Loving someoneLovesSomeone = new Loving(Global.someone, Global.someone);

        knowledge.addAssociation(someoneKillsSomeone, someoneIsDead, 1d);
        knowledge.addAssociation(someoneIsDead, fear, 1d);

        knowledge.addAssociation(someoneLovesSomething, desire, 0.2d);
        knowledge.addAssociation(someoneLovesSomeone, desire, 0.8d);

        return knowledge;
    }
}
