package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.vendor.Vendor;

import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;

/**
 * A utility class containing a list of {@code Venue} objects to be used in tests.
 */
public class TypicalVendors {

    public static final Vendor SUN = new VendorBuilder()
            .withName("SUN Caters")
            .withPhone("64266426")
            .withEmail("catering@sun.com")
            .build();

    public static final Vendor UNS = new VendorBuilder()
            .withName("UNS Decorates")
            .withPhone("67896789")
            .withEmail("decorate@uns.com")
            .build();

    public static final Vendor NSU = new VendorBuilder()
            .withName("NSU Drinks")
            .withPhone("61234567")
            .withEmail("drinks@nsu.com")
            .build();

    // Manually added - Vendor's details found in {@code CommandTestUtil}

    public static final Vendor DRINKS = new VendorBuilder()
            .withName(VALID_VENDOR_NAME_DRINKS)
            .withPhone(VALID_VENDOR_PHONE_DRINKS)
            .withEmail(VALID_VENDOR_EMAIL_DRINKS)
            .build();

    public static final Vendor FOOD = new VendorBuilder()
            .withName(VALID_VENDOR_NAME_FOOD)
            .withPhone(VALID_VENDOR_PHONE_FOOD)
            .withEmail(VALID_VENDOR_EMAIL_FOOD)
            .build();

    private TypicalVendors() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical venues.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Vendor vendor : getTypicalVendors()) {
            ab.addVendor(vendor);
        }
        return ab;
    }

    public static List<Vendor> getTypicalVendors() {
        return new ArrayList<>(Arrays.asList(SUN, UNS, NSU));
    }
}
