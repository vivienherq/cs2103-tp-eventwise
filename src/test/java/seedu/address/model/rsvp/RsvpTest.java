package seedu.address.model.rsvp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.ACADEMIC;
import static seedu.address.testutil.TypicalEvents.BASKETBALL;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalRsvps.ALICE_FSC_CC;
import static seedu.address.testutil.TypicalRsvps.BENSON_EXHIBITION_CCC;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.Name;
import seedu.address.testutil.RsvpBuilder;
import seedu.address.testutil.TypicalEvents;
import seedu.address.testutil.TypicalPersons;

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

    @Test
    public void isHashcodeValid() {
        assertEquals(ALICE_FSC_CC.hashCode(), ALICE_FSC_CC.hashCode());
        assertNotEquals(ALICE_FSC_CC.hashCode(), BENSON_EXHIBITION_CCC.hashCode());
    }

    @Test
    public void equals() {
        Rsvp aliceCopy = new RsvpBuilder()
                .withPerson(TypicalPersons.ALICE)
                .withEvent(TypicalEvents.FSC)
                .withRsvpStatus(RsvpStatus.CC)
                .build();

        // same values -> returns true
        assertTrue(ALICE_FSC_CC.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE_FSC_CC.equals(ALICE_FSC_CC));

        // null -> returns false
        assertFalse(ALICE_FSC_CC.equals(null));

        // different type -> returns false
        assertFalse(ALICE_FSC_CC.equals(5));

        // different person -> returns false
        assertFalse(ALICE_FSC_CC.equals(BENSON_EXHIBITION_CCC));

        Rsvp bensonCopy = new RsvpBuilder()
                .withPerson(BENSON)
                .withEvent(TypicalEvents.FSC)
                .withRsvpStatus(RsvpStatus.CC)
                .build();
        // different name -> returns false
        assertFalse(ALICE_FSC_CC.equals(bensonCopy));

        Rsvp fowCopy = new RsvpBuilder()
                .withPerson(ALICE)
                .withEvent(TypicalEvents.FOW)
                .withRsvpStatus(RsvpStatus.CC)
                .build();
        // different event -> returns false
        assertFalse(ALICE_FSC_CC.equals(fowCopy));

        Rsvp notComingCopy = new RsvpBuilder()
                .withPerson(ALICE)
                .withEvent(TypicalEvents.FOW)
                .withRsvpStatus(RsvpStatus.CCC)
                .build();
        // different rsvp status -> returns false
        assertFalse(ALICE_FSC_CC.equals(notComingCopy));
    }
}
