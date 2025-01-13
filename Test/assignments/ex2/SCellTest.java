package assignments.ex2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SCellTest {

    @Test
    void getOrder() {
        Ex2Sheet sheet = new Ex2Sheet(5, 5); // Assuming Ex2Sheet is properly defined
        SCell cell = new SCell("=A1+B2", sheet); // Pass Ex2Sheet instance to SCell
        sheet.set(0, 0, "5");  // Set value for A1
        sheet.set(1, 1, "10"); // Set value for B2

        int order = cell.getOrder();
        assertTrue(order > 0, "Order should be greater than 0 for a formula cell.");
    }





    @Test
    void testToString() {
        SCell cell = new SCell("Hello World");
        cell.setData("Hello World");

        assertEquals("Hello World", cell.toString(), "toString() should return the cell data.");

    }

    @Test
    void setData() {
        SCell cell = new SCell("123");
        cell.setData("123");

        assertEquals("123", cell.getData(), "setData() should set the correct data.");
        assertEquals(Ex2Utils.NUMBER, cell.getType(), "setData() should identify number type correctly.");

        cell.setData("=A1+B2");
        assertEquals(Ex2Utils.FORM, cell.getType(), "setData() should identify formula type correctly.");

        cell.setData("Hello");
        assertEquals(Ex2Utils.TEXT, cell.getType(), "setData() should identify text type correctly.");
    }


    @Test
    void getData() {
        SCell cell = new SCell("Sample Data");
        cell.setData("Sample Data");

        assertEquals("Sample Data", cell.getData(), "getData() should return the correct data.");

    }

    @Test
    void getType() {
        SCell cell = new SCell("42");
        cell.setData("42");

        assertEquals(Ex2Utils.NUMBER, cell.getType(), "getType() should return the correct type for a number.");

        cell.setData("=A1+B2");
        assertEquals(Ex2Utils.FORM, cell.getType(), "getType() should return the correct type for a formula.");

        cell.setData("Hello");
        assertEquals(Ex2Utils.TEXT, cell.getType(), "getType() should return the correct type for text.");
    }


    @Test
    void setType() {
        SCell cell = new SCell(" "+ Ex2Utils.NUMBER);
        cell.setType(Ex2Utils.NUMBER);

        assertEquals(Ex2Utils.NUMBER, cell.getType(), "setType() should set the correct type.");
    }


    @Test
    void setOrder() {
        SCell cell = new SCell();
        cell.setOrder(5);

        assertEquals(5, cell.getOrder(), "setOrder() should set the correct order.");
    }
}


