package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.vendor.Vendor;

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
