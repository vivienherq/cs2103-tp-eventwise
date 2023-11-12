package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedRsvp.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.ACADEMIC;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.rsvp.Rsvp;
import seedu.address.model.rsvp.RsvpStatus;

public class JsonAdaptedRsvpTest {
    private static final Rsvp validRsvp = new Rsvp(ACADEMIC, ALICE, RsvpStatus.CC);

    @Test
    public void toModelType_validRsvp_returnsRsvp() throws Exception {
        JsonAdaptedRsvp rsvp = new JsonAdaptedRsvp(validRsvp);
        assertEquals(validRsvp, rsvp.toModelType());
    }

    @Test
    public void toModelType_nullEvent_throwsIllegalValueException() {
        JsonAdaptedRsvp rsvp = new JsonAdaptedRsvp(null, new JsonAdaptedPerson(ALICE), "CC");
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Event.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, rsvp::toModelType);
    }

    @Test
    public void toModelType_nullPerson_throwsIllegalValueException() {
        JsonAdaptedRsvp rsvp = new JsonAdaptedRsvp(new JsonAdaptedEvent(ACADEMIC), null, "CC");
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, rsvp::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedRsvp rsvp = new JsonAdaptedRsvp(new JsonAdaptedEvent(ACADEMIC),
                new JsonAdaptedPerson(ALICE), null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, RsvpStatus.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, rsvp::toModelType);
    }
}
