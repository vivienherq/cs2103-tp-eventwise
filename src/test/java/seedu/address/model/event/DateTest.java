package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidName = "abc";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidName));
    }

    @Test
    public void isValidDate() {
        // null name
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid name
        assertFalse(Date.isValidDate("2000/01/01")); // YYYY/MM/DD
        assertFalse(Date.isValidDate("12/15/2000")); // MM/DD/YYYY
        assertFalse(Date.isValidDate("29/02/2001")); // non leap year date
        assertFalse(Date.isValidDate("32/03/2000")); // invalid date

        // valid name
        assertTrue(Date.isValidDate("01/01/2000")); // valid date
        assertTrue(Date.isValidDate("29/02/2000")); // leap year date
        assertTrue(Date.isValidDate("15/01/1800")); // past date
        assertTrue(Date.isValidDate("20/02/2030")); // future date
    }

    @Test
    public void equals() {
        Date date = new Date("01/01/2000");

        // same values -> returns true
        assertTrue(date.equals(new Date("01/01/2000")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different values -> returns false
        assertFalse(date.equals(new Date("02/01/2000")));
    }

    @Test
    public void isToStringValid() {
        Date date = new Date("01/01/2000");
        assertTrue(date.toString().equals("01/01/2000"));
        assertFalse(date.toString().equals("Invalid Date"));
    }
}
