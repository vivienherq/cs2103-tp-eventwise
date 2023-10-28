package seedu.address.model.rsvp;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Represents a RSVP in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Rsvp {
    private Event event;
    private Person person;
    private RsvpStatus rsvpStatus;

    /**
     * Every field must be present and not null.
     */
    public Rsvp(Event event, Person person, RsvpStatus rsvpStatus) {
        requireAllNonNull(event, person, rsvpStatus);
        this.event = event;
        this.person = person;
        this.rsvpStatus = rsvpStatus;
    }

    public Event getEvent() {
        return this.event;
    }

    public Person getPerson() {
        return this.person;
    }

    public RsvpStatus getRsvpStatus() {
        return rsvpStatus;
    }

    /**
     * Returns true if both rsvp have the same event and person.
     */
    public boolean isSameRsvp(Rsvp otherRsvp) {
        if (otherRsvp == this) {
            return true;
        }

        return otherRsvp != null
                && otherRsvp.getEvent().equals(getEvent())
                && otherRsvp.getPerson().equals(getPerson());
    }
}
