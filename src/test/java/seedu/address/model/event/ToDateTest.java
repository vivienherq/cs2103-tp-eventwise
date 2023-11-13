package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ToDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ToDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "abc";
        assertThrows(IllegalArgumentException.class, () -> new ToDate(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null name
        assertThrows(NullPointerException.class, () -> ToDate.isValidDate(null));

        // invalid date
        assertFalse(ToDate.isValidDate("2000/01/01")); // YYYY/MM/DD
        assertFalse(ToDate.isValidDate("12/15/2000")); // MM/DD/YYYY
        assertFalse(ToDate.isValidDate("29/02/2001")); // non leap year date
        assertFalse(ToDate.isValidDate("32/03/2000")); // invalid date

        // valid date
        assertTrue(ToDate.isValidDate("01/01/2000")); // valid date
        assertTrue(ToDate.isValidDate("29/02/2000")); // leap year date
        assertTrue(ToDate.isValidDate("15/01/1800")); // past date
        assertTrue(ToDate.isValidDate("20/02/2030")); // future date
    }

    @Test
    public void equals() {
        ToDate toDate = new ToDate("01/01/2000");

        // same values -> returns true
        assertTrue(toDate.equals(new ToDate("01/01/2000")));

        // same object -> returns true
        assertTrue(toDate.equals(toDate));

        // null -> returns false
        assertFalse(toDate.equals(null));

        // different types -> returns false
        assertFalse(toDate.equals(5.0f));

        // different values -> returns false
        assertFalse(toDate.equals(new ToDate("02/01/2000")));
    }

    @Test
    public void isToStringValid() {
        ToDate toDate = new ToDate("01/01/2000");
        assertTrue(toDate.toString().equals("01/01/2000"));
        assertFalse(toDate.toString().equals("Invalid Date"));
    }

    @Test
    public void isHashcodeValid() {
        ToDate toDate = new ToDate("11-11-2023");
        ToDate sameToDate = new ToDate("11-11-2023");
        assertEquals(toDate.hashCode(), sameToDate.hashCode());
    }
}
