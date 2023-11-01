package seedu.address.model.venue;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // invalid address
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only

        // valid address
        assertTrue(Address.isValidAddress("#")); // only non-alphanumeric characters
        assertTrue(Address.isValidAddress("COM-3")); // contains non-alphanumeric characters
        assertTrue(Address.isValidAddress("theatre")); // alphabets only
        assertTrue(Address.isValidAddress("12345")); // numbers only
        assertTrue(Address.isValidAddress("com3")); // alphanumeric characters
        assertTrue(Address.isValidAddress("Sports Hall")); // with capital letters
        assertTrue(Address.isValidAddress("2nd Room From The Left")); // long names
    }

    @Test
    public void equals() {
        Address address = new Address("Valid Address");

        // same values -> returns true
        assertTrue(address.equals(new Address("Valid Address")));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new Name("Other Valid Address")));
    }

    @Test
    public void isToStringValid() {
        Address address = new Address("Road Name, Postal Code");
        assertTrue(address.toString().equals("Road Name, Postal Code"));
        assertFalse(address.toString().equals("Not the same address"));
    }
}
