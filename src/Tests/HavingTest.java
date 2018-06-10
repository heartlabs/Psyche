package Tests;

import mindObjects.Global;
import mindObjects.concepts.Having;
import mindObjects.Someone;
import mindObjects.Something;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by e1027424 on 29.12.15.
 */
public class HavingTest extends MindObjectTest{
    private static final Someone a = new Someone("a");
    private static final Something b = new Something("b");
    private static final Having having = new Having(a,b);
    private static final Having having2 = new Having(b,a);


    @Test
    public void testSubtype(){
//        assertFalse(having.isSubtypeOf(having2));
//        assertFalse(having2.isSubtypeOf(having));
//        assertFalse(having.isSubtypeOf(a));
//
//        Having someoneHasSomething = new Having(Global.someone, Global.something);
//
//        assertFalse(having2.isSubtypeOf(someoneHasSomething));
//        assertTrue(having.isSubtypeOf(someoneHasSomething));
//
//        assertFalse(having2.match(someoneHasSomething));
//        assertTrue(having.match(someoneHasSomething));
//        assertTrue(someoneHasSomething.match(having));
//        assertTrue(someoneHasSomething.match(someoneHasSomething));
    }
}
