package seedu.address.model.rsvp;

import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class RSVP {
    private Event event;
    private Person person;
    private RSVPStatus rsvpStatus;

    public RSVP(Event event, Person person, RSVPStatus rsvpStatus) {
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

    public RSVPStatus getRsvpStatus() {
        return rsvpStatus;
    }

    public boolean isSameRSVP(RSVP otherRSVP) {
        if (otherRSVP == this) {
            return true;
        }

        return otherRSVP != null
                && otherRSVP.getEvent().equals(getEvent())
                && otherRSVP.getPerson().equals(getPerson());
    }

//    public static boolean isValidStatus(String status) {
//        for (RSVPStatus rsvpStatus: RSVPStatus.values()) {
//            if (rsvpStatus.getStatus().equals(status)) {
//                return true;
//            }
//        }
//        return false;
//    }
}
