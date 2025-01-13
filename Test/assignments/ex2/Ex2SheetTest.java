package assignments.ex2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Ex2SheetTest {
    private Ex2Sheet ex2Sheet;

    @BeforeEach
    void setUp() {
        int width = 5; // Example width
        int height = 5; // Example height
        ex2Sheet = new Ex2Sheet(width, height);

    }

    @Test
    void testValue() {
        ex2Sheet.set(1, 1, "TestValue");
        assertEquals("TestValue", ex2Sheet.value(1, 1));
        assertEquals("Invalid cell coordinates", ex2Sheet.value(-1, -1));
    }

    @Test
    void testGet1() {
        ex2Sheet.set(2, 2, "CellData");
        assertNotNull(ex2Sheet.get(2, 2));
        assertThrows(IllegalArgumentException.class, () -> ex2Sheet.get(-1, -1));
    }

    @Test
    void testGet2() {
        ex2Sheet.set(1, 1, "Test");
        assertNotNull(ex2Sheet.get("B2")); // Assuming "B2" maps to (1,1)
        assertNull(ex2Sheet.get("InvalidCord"));
        assertNull(ex2Sheet.get(""));
    }

    @Test
    void testWidth() {
        assertEquals(5, ex2Sheet.width());
    }

    @Test
    void testHeight() {
        assertEquals(5, ex2Sheet.height());
    }

    @Test
    void testSet() {
        ex2Sheet.set(3, 3, "SetTest");
        assertEquals("SetTest", ex2Sheet.value(3, 3));
        assertThrows(IllegalArgumentException.class, () -> ex2Sheet.set(-1, -1, "Invalid"));
    }

    @Test
    void testEval1() {
        ex2Sheet.set(0, 0, "=2+3");
        assertEquals(" 5.0", ex2Sheet.eval(0, 0)); // Assuming the formula is correctly evaluated
        assertEquals(Ex2Utils.EMPTY_CELL, ex2Sheet.eval(1, 1)); // No data in cell
        assertThrows(IllegalArgumentException.class, () -> ex2Sheet.eval(-1, -1));
    }

    @Test
    void testIsIn() {
        assertTrue(ex2Sheet.isIn(0, 0));
        assertTrue(ex2Sheet.isIn(4, 4));
        assertFalse(ex2Sheet.isIn(-1, -1));
        assertFalse(ex2Sheet.isIn(5, 5));
    }

    @Test
    void testDepth() {
        ex2Sheet.set(0, 0, "=A1");
        int[][] depth = ex2Sheet.depth();
        assertNotNull(depth);
        assertEquals(0, depth[0][0]);
    }

    @Test
    void testLoad() {
    }

    @Test
    void testSave() {
    }

    @Test
    void testEval2() {
        ex2Sheet.set(1, 1, "SUM(1,2)");
        String evalResult = ex2Sheet.eval(1, 1);
        assertNotNull(evalResult);
    }
}