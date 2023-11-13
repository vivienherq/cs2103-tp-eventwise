package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null name
        assertThrows(NullPointerException.class, () -> Description.isValidDesc(null));

        // invalid name
        assertFalse(Description.isValidDesc("")); // empty string
        assertFalse(Description.isValidDesc(" ")); // spaces only
        assertFalse(Description.isValidDesc("^")); // only non-alphanumeric characters
        assertFalse(Description.isValidDesc("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Description.isValidDesc("peter jack")); // alphabets only
        assertTrue(Description.isValidDesc("12345")); // numbers only
        assertTrue(Description.isValidDesc("peter the 2nd")); // alphanumeric characters
        assertTrue(Description.isValidDesc("Capital Tan")); // with capital letters
        assertTrue(Description.isValidDesc("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        Description desc = new Description("Valid Description");

        // same values -> returns true
        assertTrue(desc.equals(new Description("Valid Description")));

        // same object -> returns true
        assertTrue(desc.equals(desc));

        // null -> returns false
        assertFalse(desc.equals(null));

        // different types -> returns false
        assertFalse(desc.equals(5.0f));

        // different values -> returns false
        assertFalse(desc.equals(new Name("Other Valid Description")));
    }

    @Test
    public void isToStringValid() {
        Description description = new Description("Valid Description");
        assertTrue(description.toString().equals("Valid Description"));
        assertFalse(description.toString().equals("Invalid Description"));
    }

    @Test
    public void isHashcodeValid() {
        Description description = new Description("Valid Description");
        Description sameDescription = new Description("Valid Description");
        assertEquals(description.hashCode(), sameDescription.hashCode());
    }
}
