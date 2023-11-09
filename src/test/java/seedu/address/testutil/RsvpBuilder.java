package seedu.address.testutil;

import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.rsvp.Rsvp;
import seedu.address.model.rsvp.RsvpStatus;

public class RsvpBuilder {
    public static final Event DEFAULT_EVENT = TypicalEvents.ACADEMIC;
    public static final Person DEFAULT_PERSON = TypicalPersons.CARL;
    public static final RsvpStatus DEFAULT_RSVP_STATUS = RsvpStatus.TBC;

    private Event event;
    private Person person;
    private RsvpStatus rsvpStatus;

    /**
     * Creates a {@code RsvpBuilder} with the default details.
     */
    public RsvpBuilder() {
        event = DEFAULT_EVENT;
        person = DEFAULT_PERSON;
        rsvpStatus = DEFAULT_RSVP_STATUS;
    }

    /**
     * Initializes the RsvpBuilder with the data of {@code rsvpToCopy}.
     */
    public RsvpBuilder(Rsvp rsvpToCopy) {
        event = rsvpToCopy.getEvent();
        person = rsvpToCopy.getPerson();
        rsvpStatus = rsvpToCopy.getRsvpStatus();
    }

    public RsvpBuilder withEvent(Event event) {
        this.event = event;
        return this;
    }

    public RsvpBuilder withPerson(Person person) {
        this.person = person;
        return this;
    }

    public RsvpBuilder withRsvpStatus(RsvpStatus rsvpStatus) {
        this.rsvpStatus = rsvpStatus;
        return this;
    }

    public Rsvp build() {
        return new Rsvp(event, person, rsvpStatus);
    }
}
