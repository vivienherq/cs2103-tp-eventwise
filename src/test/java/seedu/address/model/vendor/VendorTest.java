package seedu.address.model.vendor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENDOR_EMAIL_FOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENDOR_NAME_FOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENDOR_PHONE_FOOD;
import static seedu.address.testutil.TypicalVendors.DRINKS;
import static seedu.address.testutil.TypicalVendors.FOOD;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.VendorBuilder;

public class VendorTest {

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(DRINKS.isSameVendor(DRINKS));

        // null -> returns false
        assertFalse(DRINKS.isSameVendor(null));

        // same name, all other attributes different -> returns true
        Vendor editedDrinks = new VendorBuilder(DRINKS)
                .withPhone(VALID_VENDOR_PHONE_FOOD)
                .withEmail(VALID_VENDOR_EMAIL_FOOD)
                .build();
        assertTrue(DRINKS.isSameVendor(editedDrinks));

        // different name, all other attributes same -> returns false
        editedDrinks = new VendorBuilder(DRINKS).withName(VALID_VENDOR_NAME_FOOD).build();
        assertFalse(DRINKS.isSameVendor(editedDrinks));

        // name differs in case, all other attributes same -> returns false
        Vendor editedFood = new VendorBuilder(FOOD).withName(VALID_VENDOR_NAME_FOOD.toLowerCase()).build();
        assertFalse(FOOD.isSameVendor(editedFood));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_VENDOR_NAME_FOOD + " ";
        editedFood = new VendorBuilder(FOOD).withName(nameWithTrailingSpaces).build();
        assertFalse(FOOD.isSameVendor(editedFood));
    }

    @Test
    public void getDisplayTitle_isValid() {
        assertEquals(DRINKS.getDisplayTitle(), DRINKS.getName().toString());
        assertNotEquals(DRINKS.getDisplayTitle(), FOOD.getName().toString());
    }

    @Test
    public void getDisplayFirstText_isValid() {
        assertEquals(DRINKS.getDisplayFirstText(), DRINKS.getPhone().toString());
        assertNotEquals(DRINKS.getDisplayFirstText(), FOOD.getPhone().toString());
    }

    @Test
    public void getDisplaySecondText_isValid() {
        assertEquals(DRINKS.getDisplaySecondText(), DRINKS.getEmail().toString());
        assertNotEquals(DRINKS.getDisplaySecondText(), FOOD.getEmail().toString());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Vendor drinksCopy = new VendorBuilder(DRINKS).build();
        assertTrue(DRINKS.equals(drinksCopy));

        // same object -> returns true
        assertTrue(DRINKS.equals(DRINKS));

        // null -> returns false
        assertFalse(DRINKS.equals(null));

        // different type -> returns false
        assertFalse(DRINKS.equals(5));

        // different person -> returns false
        assertFalse(DRINKS.equals(FOOD));

        // different name -> returns false
        Vendor editedDrinks = new VendorBuilder(DRINKS).withName(VALID_VENDOR_NAME_FOOD).build();
        assertFalse(DRINKS.equals(editedDrinks));

        // different phone -> returns false
        editedDrinks = new VendorBuilder(DRINKS).withPhone(VALID_VENDOR_PHONE_FOOD).build();
        assertFalse(DRINKS.equals(editedDrinks));

        // different email -> returns false
        editedDrinks = new VendorBuilder(DRINKS).withEmail(VALID_VENDOR_EMAIL_FOOD).build();
        assertFalse(DRINKS.equals(editedDrinks));
    }

    @Test
    public void toStringMethod() {
        String expected = Vendor.class.getCanonicalName() + "{name=" + DRINKS.getName() + ", phone=" + DRINKS.getPhone()
                + ", email=" + DRINKS.getEmail() + "}";
        assertEquals(expected, DRINKS.toString());
    }

    @Test
    public void isHashcodeValid() {
        assertEquals(DRINKS.hashCode(), DRINKS.hashCode());
        assertNotEquals(DRINKS.hashCode(), FOOD.hashCode());
    }
}
