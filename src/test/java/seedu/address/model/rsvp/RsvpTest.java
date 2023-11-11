package seedu.address.model.rsvp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.ACADEMIC;
import static seedu.address.testutil.TypicalEvents.BASKETBALL;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.Name;

public class RsvpTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Rsvp(null, null, null));
    }

    @Test
    public void constructor_success() {
        Rsvp rsvp = new Rsvp(ACADEMIC, ALICE, RsvpStatus.CC);
        assertEquals(ACADEMIC, rsvp.getEvent());
        assertEquals(ALICE, rsvp.getPerson());
        assertEquals(RsvpStatus.CC, rsvp.getRsvpStatus());
    }

    @Test
    public void isSameRsvp() {
        Rsvp rsvp = new Rsvp(ACADEMIC, ALICE, RsvpStatus.CC);
        Rsvp othrRsvp = new Rsvp(ACADEMIC, ALICE, RsvpStatus.CC);
        assertTrue(rsvp.isSameRsvp(othrRsvp));
    }

    @Test
    public void isDifferentRsvp() {
        Rsvp rsvp = new Rsvp(ACADEMIC, ALICE, RsvpStatus.CC);
        Rsvp othrRsvp = new Rsvp(BASKETBALL, BENSON, RsvpStatus.TBC);
        assertFalse(rsvp.isSameRsvp(othrRsvp));
    }

    @Test
    public void getPersonEventDetails() {
        Rsvp rsvp = new Rsvp(ACADEMIC, ALICE, RsvpStatus.CC);
        Name eventName = new Name("Academic Awards Ceremony");
        seedu.address.model.person.Name personName = new seedu.address.model.person.Name("Alice Pauline");
        assertEquals(rsvp.getEventName(), eventName);
        assertEquals(rsvp.getPersonName(), personName);
    }
}
