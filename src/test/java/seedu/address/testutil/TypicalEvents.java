package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event ACADEMIC = new EventBuilder().withName("Academic Awards Ceremony")
            .withDescription("Celebrating student and research achievements")
            .withDate("08-11-2023").build();

    public static final Event BASKETBALL = new EventBuilder().withName("Basketball Competition")
            .withDescription("Inter Faculty Games Basketball Finals")
            .withDate("05-11-2023").build();

    public static final Event CODING = new EventBuilder().withName("Code Competition")
            .withDescription("Code for a good cause")
            .withDate("19-12-2023").build();

    public static final Event DIVERSITY = new EventBuilder().withName("Diversity Women in Tech")
            .withDescription("Promoting diversity and inclusion in STEM fields")
            .withDate("21-11-2023").build();

    public static final Event EXHIBITION = new EventBuilder().withName("Tech Expo")
            .withDescription("Explore the latest in technology and innovation")
            .withDate("16-12-2023").build();

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(ACADEMIC, BASKETBALL, CODING, DIVERSITY, EXHIBITION));
    }
}
