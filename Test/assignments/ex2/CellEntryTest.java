package assignments.ex2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellEntryTest {

    @Test
    void isValid() {
        assertTrue(new CellEntry("A1").isValid(), "Expected 'A1' to be valid");
        assertTrue(new CellEntry("Z99").isValid(), "Expected 'Z99' to be valid");
        assertTrue(new CellEntry("B10").isValid(), "Expected 'B10' to be valid");

        // Invalid cases
        assertFalse(new CellEntry("").isValid(), "Expected an empty string to be invalid");
        assertFalse(new CellEntry("1A").isValid(), "Expected '1A' to be invalid");
        assertFalse(new CellEntry("AA1").isValid(), "Expected 'AA1' to be invalid");
        assertFalse(new CellEntry("A1000").isValid(), "Expected 'A1000' to be invalid");
        assertFalse(new CellEntry("!@").isValid(), "Expected '!@' to be invalid");
    }

    @Test
    void getX() {
        CellEntry cell = new CellEntry("A1");
        assertEquals(0, cell.getX(), "Expected column 'A' to map to 0");

        cell = new CellEntry("B1");
        assertEquals(1, cell.getX(), "Expected column 'B' to map to 1");

        cell = new CellEntry("Z1");
        assertEquals(25, cell.getX(), "Expected column 'Z' to map to 25");

        // Invalid case
        cell = new CellEntry("1A");
        assertEquals(Ex2Utils.ERR, cell.getX(), "Expected invalid input to return Ex2Utils.ERR");
    }

    @Test
    void getY() {
        CellEntry cell = new CellEntry("A1");
        assertEquals(1, cell.getY(), "Expected row '1' to map to 1");

        cell = new CellEntry("A99");
        assertEquals(99, cell.getY(), "Expected row '99' to map to 99");

        // Invalid case
        cell = new CellEntry("A-1");
        assertEquals(Ex2Utils.ERR, cell.getY(), "Expected invalid input to return Ex2Utils.ERR");
    }



    @Test
    void testToString() {
        CellEntry cell = new CellEntry("B2");
        assertEquals("CellEntry [x=1, y=2]", cell.toString(), "Expected valid toString output for 'B2'");

        // Invalid case
        cell = new CellEntry("1A");
        assertEquals("CellEntry [x=" + Ex2Utils.ERR + ", y=" + Ex2Utils.ERR + "]", cell.toString(),
                "Expected toString output for invalid input '1A'");
    }
}
