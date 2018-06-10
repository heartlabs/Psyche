package Tests;

import org.junit.Test;
import mindObjects.Global;
import mindObjects.MindObject;
import mindObjects.Someone;
import mindObjects.Something;

import static org.junit.Assert.*;

/**
 * Created by e1027424 on 29.12.15.
 */
public abstract class MindObjectTest {
    protected void testEquals(MindObject a, MindObject differentFromA) throws Exception {
        assertEquals(a,a);
        assertEquals(differentFromA,differentFromA);
        assertNotEquals(a, differentFromA);
        assertNotEquals(differentFromA, a);

        assertTrue(a.isSubtypeOf(a));
        assertTrue(differentFromA.isSubtypeOf(differentFromA));
    }

    @Test
    public abstract void testEquals() throws Exception;

    @Test
    public void testSubtype() throws Exception{
        assertTrue(Global.someone.isSubtypeOf(Global.something));
        assertFalse(Global.something.isSubtypeOf(Global.someone));

        assertTrue(new Someone("a").isSubtypeOf(Global.something));
        assertTrue(new Someone("a").isSubtypeOf(Global.someone));

        assertTrue(new Something("b").isSubtypeOf(Global.something));
        assertFalse(new Something("b").isSubtypeOf(Global.someone));

    }
}
