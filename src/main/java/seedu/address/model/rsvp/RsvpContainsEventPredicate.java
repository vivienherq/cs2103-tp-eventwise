package seedu.address.model.rsvp;

import java.util.function.Predicate;

import seedu.address.model.event.Event;

/**
 * Tests that a {@code Rsvp}'s {@code Event} matches the event given.
 */
public class RsvpContainsEventPredicate implements Predicate<Rsvp> {
    private final Event event;
    public RsvpContainsEventPredicate(Event event) {
        this.event = event;
    }

    @Override
    public boolean test(Rsvp rsvp) {
        return rsvp.getEvent().isSameEvent(event);
    }
}
