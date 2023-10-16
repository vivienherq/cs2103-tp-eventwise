package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event FSC = new EventBuilder().withName("FSC 2023")
            .withDescription("Freshman Social Camp 2023").withDate("01/01/2023")
            .build();
    public static final Event FOC = new EventBuilder().withName("FOC 2023")
            .withDescription("Freshman Orientation Camp 2023").withDate("01/02/2023")
            .build();
    public static final Event FOW = new EventBuilder().withName("FOW 2023")
            .withDescription("Freshman Orientation Camp 2023").withDate("01/02/2023")
            .build();

    private TypicalEvents() {} // prevents instantiation


    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(FSC, FOC, FOW));
    }
}
