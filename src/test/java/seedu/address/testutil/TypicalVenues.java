package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.vendor.Vendor;
import seedu.address.model.venue.Venue;

import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENDOR_EMAIL_FOOD;

/**
 * A utility class containing a list of {@code Venue} objects to be used in tests.
 */
public class TypicalVenues {

    public static final Venue LT27 = new VenueBuilder()
            .withName("LT27")
            .withAddress("Lecture Theatre 27, 10 Lower Kent Ridge Rd, Kent Ridge Campus, Singapore 119076")
            .withCapacity("400")
            .build();

    public static final Venue CLB = new VenueBuilder()
            .withName("CLB")
            .withAddress("Central Library, 12 Kent Ridge Crescent, Singapore 119275")
            .withCapacity("1000")
            .build();

    public static final Venue ECUBE = new VenueBuilder()
            .withName("ECube")
            .withAddress("4 Engineering Drive 3, Singapore 117583")
            .withCapacity("100")
            .build();

    // Manually added - Venue's details found in {@code CommandTestUtil}

    public static final Venue COM1 = new VenueBuilder()
            .withName(VALID_VENUE_NAME_COM1)
            .withAddress(VALID_VENUE_ADDRESS_COM1)
            .withCapacity(VALID_VENUE_CAPACITY_COM1)
            .build();

    public static final Venue BIZ2 = new VenueBuilder()
            .withName(VALID_VENUE_NAME_BIZ2)
            .withAddress(VALID_VENUE_ADDRESS_BIZ2)
            .withCapacity(VALID_VENUE_CAPACITY_BIZ2)
            .build();

    private TypicalVenues() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical venues.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Venue venue : getTypicalVenues()) {
            ab.addVenue(venue);
        }
        return ab;
    }

    public static List<Venue> getTypicalVenues() {
        return new ArrayList<>(Arrays.asList(LT27, CLB, ECUBE));
    }
}
