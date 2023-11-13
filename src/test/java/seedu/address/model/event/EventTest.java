package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_CAREER_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_FROM_DATE_CAREER_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_CAREER_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_FSC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NOTE_CAREER_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TO_DATE_CAREER_FAIR;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.FOW;
import static seedu.address.testutil.TypicalEvents.FSC;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

public class EventTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event(null, null, null, null, null));
    }

    @Test
    public void isSameEvent() {
        // same object -> returns true
        assertTrue(FSC.isSameEvent(FSC));

        // null -> returns false
        assertFalse(FSC.isSameEvent(null));

        // same name, all other attributes different -> returns true
        Event editedFsc = new EventBuilder(FSC).withDescription(VALID_EVENT_DESCRIPTION_CAREER_FAIR)
                .withFromDate(VALID_EVENT_FROM_DATE_CAREER_FAIR)
                .withToDate(VALID_EVENT_TO_DATE_CAREER_FAIR)
                .withNote(VALID_EVENT_NOTE_CAREER_FAIR).build();
        assertTrue(FSC.isSameEvent(editedFsc));

        // different name, all other attributes same -> returns false
        editedFsc = new EventBuilder(FSC).withName(VALID_EVENT_NAME_CAREER_FAIR).build();
        assertFalse(FSC.isSameEvent(editedFsc));

        // name differs in case, all other attributes same -> returns false
        editedFsc = new EventBuilder(FSC).withName(VALID_EVENT_NAME_FSC.toLowerCase()).build();
        assertFalse(FSC.isSameEvent(editedFsc));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_EVENT_NAME_FSC + " ";
        editedFsc = new EventBuilder(FSC).withName(nameWithTrailingSpaces).build();
        assertFalse(FSC.isSameEvent(editedFsc));
    }

    @Test
    public void getDisplayTitle_isValid() {
        assertEquals(FSC.getDisplayTitle(), FSC.getName().toString());
        assertNotEquals(FSC.getDisplayTitle(), FOW.getName().toString());
    }

    @Test
    public void getDisplayFirstText_isValid() {
        assertEquals(FSC.getDisplayFirstText(), FSC.getDescription().toString());
        assertNotEquals(FSC.getDisplayFirstText(), FOW.getDescription().toString());
    }

    @Test
    public void getDisplaySecondText_isValid() {
        assertEquals(FSC.getDisplaySecondText(),
                String.format("%s to %s", FSC.getFromDate().toString(), FSC.getToDate().toString()));
        assertNotEquals(FSC.getDisplaySecondText(),
                String.format("%s to %s", FOW.getFromDate().toString(), FOW.getToDate().toString()));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event fscCopy = new EventBuilder(FSC).build();
        assertTrue(FSC.equals(fscCopy));

        // same object -> returns true
        assertTrue(FSC.equals(FSC));

        // null -> returns false
        assertFalse(FSC.equals(null));

        // different type -> returns false
        assertFalse(FSC.equals(5));

        // different person -> returns false
        assertFalse(FSC.equals(FOW));

        // different name -> returns false
        Event editedFsc = new EventBuilder(FSC).withName(VALID_EVENT_NAME_CAREER_FAIR).build();
        assertFalse(FSC.equals(editedFsc));

        // different description -> returns false
        editedFsc = new EventBuilder(FSC).withDescription(VALID_EVENT_DESCRIPTION_CAREER_FAIR).build();
        assertFalse(FSC.equals(editedFsc));

        // different fromDate -> returns false
        editedFsc = new EventBuilder(FSC).withFromDate(VALID_EVENT_FROM_DATE_CAREER_FAIR).build();
        assertFalse(FSC.equals(editedFsc));

        // different toDate -> returns false
        editedFsc = new EventBuilder(FSC).withToDate(VALID_EVENT_TO_DATE_CAREER_FAIR).build();
        assertFalse(FSC.equals(editedFsc));
    }

    @Test
    public void toStringMethod() {
        String expected = Event.class.getCanonicalName() + "{name=" + FSC.getName() + ", description="
                + FSC.getDescription() + ", fromDate=" + FSC.getFromDate()
                + ", toDate=" + FSC.getToDate() + ", note=" + FSC.getNote() + "}";
        assertEquals(expected, FSC.toString());
    }

    @Test
    public void isHashcodeValid() {
        assertEquals(FSC.hashCode(), FSC.hashCode());
        assertNotEquals(FSC.hashCode(), FOW.hashCode());
    }
}
