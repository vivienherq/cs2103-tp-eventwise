package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.rsvp.Rsvp;
import seedu.address.model.rsvp.RsvpStatus;

import java.util.List;

public class TypicalRsvps {
    public static final Rsvp ALICE_FSC_CC = new RsvpBuilder()
            .withPerson(TypicalPersons.ALICE)
            .withEvent(TypicalEvents.FSC)
            .withRsvpStatus(RsvpStatus.CC)
            .build();

    public static final Rsvp BENSON_EXHIBITION_CCC = new RsvpBuilder()
            .withPerson(TypicalPersons.BENSON)
            .withEvent(TypicalEvents.EXHIBITION)
            .withRsvpStatus(RsvpStatus.CCC)
            .build();

    public static final Rsvp CARL_CODING_TBC = new RsvpBuilder()
            .withPerson(TypicalPersons.CARL)
            .withEvent(TypicalEvents.CODING)
            .withRsvpStatus(RsvpStatus.TBC)
            .build();

    private TypicalRsvps() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Rsvp rsvp : getTypicalRsvps()) {
            ab.addRsvp(rsvp);
        }
        return ab;
    }

    public static List<Rsvp> getTypicalRsvps() {
        return List.of(ALICE_FSC_CC, BENSON_EXHIBITION_CCC);
    }
}
