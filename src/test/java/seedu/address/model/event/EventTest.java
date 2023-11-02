package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(FSC.isSameEvent(FSC));

        // null -> returns false
        assertFalse(FSC.isSameEvent(null));

        //        // same name, all other attributes different -> returns true
        //        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
        //                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        //        assertTrue(ALICE.isSamePerson(editedAlice));
        //
        //        // different name, all other attributes same -> returns false
        //        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        //        assertFalse(ALICE.isSamePerson(editedAlice));
        //
        //        // name differs in case, all other attributes same -> returns false
        //        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        //        assertFalse(BOB.isSamePerson(editedBob));
        //
        //        // name has trailing spaces, all other attributes same -> returns false
        //        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        //        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        //        assertFalse(BOB.isSamePerson(editedBob));
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

        //        // different name -> returns false
        //        Event editedAlice = new EventBuilder(FSC).withName(VALID_NAME_BOB).build();
        //        assertFalse(ALICE.equals(editedAlice));
        //
        //        // different phone -> returns false
        //        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        //        assertFalse(ALICE.equals(editedAlice));
        //
        //        // different email -> returns false
        //        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        //        assertFalse(ALICE.equals(editedAlice));
        //
        //        // different address -> returns false
        //        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        //        assertFalse(ALICE.equals(editedAlice));
        //
        //        // different tags -> returns false
        //        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        //        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Event.class.getCanonicalName() + "{name=" + FSC.getName() + ", description="
                + FSC.getDescription() + ", fromDate=" + FSC.getFromDate()
                + ", toDate=" + FSC.getToDate() + ", note=" + FSC.getNote() + "}";
        assertEquals(expected, FSC.toString());
    }
}
