package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FromDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FromDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidName = "abc";
        assertThrows(IllegalArgumentException.class, () -> new FromDate(invalidName));
    }

    @Test
    public void isValidDate() {
        // null name
        assertThrows(NullPointerException.class, () -> FromDate.isValidDate(null));

        // invalid name
        assertFalse(FromDate.isValidDate("2000/01/01")); // YYYY/MM/DD
        assertFalse(FromDate.isValidDate("12/15/2000")); // MM/DD/YYYY
        assertFalse(FromDate.isValidDate("29/02/2001")); // non leap year date
        assertFalse(FromDate.isValidDate("32/03/2000")); // invalid date

        // valid name
        assertTrue(FromDate.isValidDate("01/01/2000")); // valid date
        assertTrue(FromDate.isValidDate("29/02/2000")); // leap year date
        assertTrue(FromDate.isValidDate("15/01/1800")); // past date
        assertTrue(FromDate.isValidDate("20/02/2030")); // future date
    }

    @Test
    public void equals() {
        FromDate fromDate = new FromDate("01/01/2000");

        // same values -> returns true
        assertTrue(fromDate.equals(new FromDate("01/01/2000")));

        // same object -> returns true
        assertTrue(fromDate.equals(fromDate));

        // null -> returns false
        assertFalse(fromDate.equals(null));

        // different types -> returns false
        assertFalse(fromDate.equals(5.0f));

        // different values -> returns false
        assertFalse(fromDate.equals(new FromDate("02/01/2000")));
    }

    @Test
    public void isToStringValid() {
        FromDate fromDate = new FromDate("01/01/2000");
        assertTrue(fromDate.toString().equals("01/01/2000"));
        assertFalse(fromDate.toString().equals("Invalid Date"));
    }
}
