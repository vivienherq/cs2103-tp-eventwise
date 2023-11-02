package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event ACADEMIC = new EventBuilder().withName("Academic Awards Ceremony")
            .withDescription("Celebrating student and research achievements")
            .withFromDate("08-11-2023")
            .withToDate("09-11-2023")
            .withNote("Food and drinks are provided")
            .withPersons(TypicalPersons.getTypicalPersons())
            .withVendors(TypicalVendors.getTypicalVendors())
            .withVenue(TypicalVenues.LT27)
            .build();

    public static final Event BASKETBALL = new EventBuilder().withName("Basketball Competition")
            .withDescription("Inter Faculty Games Basketball Finals")
            .withFromDate("05-11-2023")
            .withToDate("09-11-2023")
            .withNote("Food and drinks are provided")
            .build();

    public static final Event CODING = new EventBuilder().withName("Code Competition")
            .withDescription("Code for a good cause")
            .withFromDate("19-12-2023")
            .withToDate("09-11-2024")
            .withNote("Food and drinks are provided")
            .build();

    public static final Event DIVERSITY = new EventBuilder().withName("Diversity Women in Tech")
            .withDescription("Promoting diversity and inclusion in STEM fields")
            .withFromDate("21-11-2023")
            .withToDate("09-11-2024")
            .withNote("Food and drinks are provided")
            .build();

    public static final Event EXHIBITION = new EventBuilder().withName("Tech Expo")
            .withDescription("Explore the latest in technology and innovation")
            .withFromDate("16-12-2023")
            .withToDate("09-11-2024")
            .withNote("Food and drinks are provided")
            .build();

    public static final Event FSC = new EventBuilder().withName("FSC 2024")
            .withDescription("Freshman Social Camp 2024").withFromDate("12-09-2024")
            .withToDate("09-11-2024")
            .withNote("Food and drinks are provided")
            .build();
    public static final Event FOC = new EventBuilder().withName("FOC 2023")
            .withDescription("Freshman Orientation Camp 2023").withFromDate("01-02-2023")
            .withToDate("09-11-2024")
            .withNote("Food and drinks are provided")
            .build();
    public static final Event FOW = new EventBuilder().withName("FOW 2023")
            .withDescription("Freshman Orientation Camp 2023").withFromDate("01-02-2023")
            .withToDate("09-11-2024")
            .withNote("Food and drinks are provided")
            .build();

    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical events.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(FSC, FOC, FOW, ACADEMIC, BASKETBALL, CODING, DIVERSITY, EXHIBITION));
    }
}
