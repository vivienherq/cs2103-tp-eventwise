package seedu.address.model.venue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_ADDRESS_CLB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_CAPACITY_CLB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_NAME_CLB;
import static seedu.address.testutil.TypicalVenues.CLB;
import static seedu.address.testutil.TypicalVenues.LT27;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.VenueBuilder;

public class VenueTest {

    @Test
    public void isSameVenue() {
        // same object -> returns true
        assertTrue(LT27.isSameVenue(LT27));

        // null -> returns false
        assertFalse(LT27.isSameVenue(null));

        // same name, all other attributes different -> returns true
        Venue editedLT27 = new VenueBuilder(LT27)
                .withAddress(VALID_VENUE_ADDRESS_CLB)
                .withCapacity(VALID_VENUE_CAPACITY_CLB)
                .build();
        assertTrue(LT27.isSameVenue(editedLT27));

        // different name, all other attributes same -> returns false
        editedLT27 = new VenueBuilder(LT27).withName(VALID_VENUE_NAME_CLB).build();
        assertFalse(LT27.isSameVenue(editedLT27));

        // name differs in case, all other attributes same -> returns false
        Venue editedClb = new VenueBuilder(CLB).withName(VALID_VENUE_NAME_CLB.toLowerCase()).build();
        assertFalse(CLB.isSameVenue(editedClb));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_VENUE_NAME_CLB + " ";
        editedClb = new VenueBuilder(CLB).withName(nameWithTrailingSpaces).build();
        assertFalse(CLB.isSameVenue(editedClb));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Venue lTCopy = new VenueBuilder(LT27).build();
        assertTrue(lTCopy.equals(lTCopy));

        // same object -> returns true
        assertTrue(LT27.equals(LT27));

        // null -> returns false
        assertFalse(LT27.equals(null));

        // different type -> returns false
        assertFalse(LT27.equals(5));

        // different person -> returns false
        assertFalse(LT27.equals(CLB));

        // different name -> returns false
        Venue editedLT27 = new VenueBuilder(LT27).withName(VALID_VENUE_NAME_CLB).build();
        assertFalse(LT27.equals(editedLT27));

        // different phone -> returns false
        editedLT27 = new VenueBuilder(LT27).withAddress(VALID_VENUE_ADDRESS_CLB).build();
        assertFalse(LT27.equals(editedLT27));

        // different email -> returns false
        editedLT27 = new VenueBuilder(LT27).withCapacity(VALID_VENUE_CAPACITY_CLB).build();
        assertFalse(LT27.equals(editedLT27));
    }

    @Test
    public void toStringMethod() {
        String expected = Venue.class.getCanonicalName() + "{name=" + LT27.getName() + ", address=" + LT27.getAddress()
                + ", capacity=" + LT27.getCapacity() + "}";
        assertEquals(expected, LT27.toString());
    }
}
