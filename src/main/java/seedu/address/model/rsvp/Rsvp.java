package seedu.address.model.rsvp;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.model.event.Event;
import seedu.address.model.person.Name;
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
     * Constructs a {@code Rsvp} with the specified event, person, and RSVP status.
     * Every field must be present and not null.
     *
     * @param event      The event associated with the RSVP.
     * @param person     The person who is RSVPing.
     * @param rsvpStatus The RSVP status of the person for the event.
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
     * Checks if two RSVPs are the same, based on the event and person.
     * Two RSVPs are considered the same if they have the same event and person.
     *
     * @param otherRsvp The other RSVP to compare with.
     * @return True if both RSVPs have the same event and person, false otherwise.
     */
    public boolean isSameRsvp(Rsvp otherRsvp) {
        if (otherRsvp == this) {
            return true;
        }

        return otherRsvp != null
                && otherRsvp.getEvent().equals(getEvent())
                && otherRsvp.getPerson().equals(getPerson());
    }

    // The following three methods are to resolve Law of Demeter.
    public Name getPersonName() {
        return person.getName();
    }

    public seedu.address.model.event.Name getEventName() {
        return event.getName();
    }

    public List<Person> getEventGuests() {
        return event.getPersons();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Rsvp)) {
            return false;
        }

        Rsvp otherRsvp = (Rsvp) other;
        return event.equals(otherRsvp.event)
                && person.equals(otherRsvp.person)
                && rsvpStatus.equals(otherRsvp.rsvpStatus);
    }
}
