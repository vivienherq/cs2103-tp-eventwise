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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.rsvp.exceptions.RsvpNotFoundException;

public class UniqueRsvpListTest {

    private final UniqueRsvpList uniqueRsvpList = new UniqueRsvpList();
    private Rsvp validRsvp = new Rsvp(ACADEMIC, ALICE, RsvpStatus.CC);

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRsvpList.contains(null));
    }

    @Test
    public void contains_rsvpNotInList_returnsFalse() {
        assertFalse(uniqueRsvpList.contains(validRsvp));
    }

    @Test
    public void contains_rsvpInList_returnsTrue() {
        uniqueRsvpList.add(validRsvp);
        assertTrue(uniqueRsvpList.contains(validRsvp));
    }

    @Test
    public void get_duplicate_rsvp() {
        Optional<Rsvp> results = uniqueRsvpList.getDuplicateRsvp(validRsvp);
        assertFalse(results.isPresent());
        uniqueRsvpList.add(validRsvp);
        results = uniqueRsvpList.getDuplicateRsvp(validRsvp);
        assertTrue(results.isPresent());
        assertEquals(validRsvp, results.get());
    }

    @Test
    public void add_nullRsvp_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRsvpList.add(null));
    }

    @Test
    public void setRsvp_nullTargetRsvp_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRsvpList.setRsvp(null, validRsvp));
    }

    @Test
    public void setPerson_nullEditedRsvp_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRsvpList.setRsvp(validRsvp, null));
    }

    @Test
    public void setRsvp_editedRsvpIsSameRsvp_success() {
        uniqueRsvpList.add(validRsvp);
        uniqueRsvpList.setRsvp(validRsvp, validRsvp);
        UniqueRsvpList expectedUniqueRsvpList = new UniqueRsvpList();
        expectedUniqueRsvpList.add(validRsvp);
        assertEquals(expectedUniqueRsvpList, uniqueRsvpList);
    }

    @Test
    public void setPerson_editedRsvpHasDifferentIdentity_success() {
        Rsvp othrRsvp = new Rsvp(BASKETBALL, BENSON, RsvpStatus.TBC);
        uniqueRsvpList.add(validRsvp);
        uniqueRsvpList.setRsvp(validRsvp, othrRsvp);
        UniqueRsvpList expectedUniqueRsvpList = new UniqueRsvpList();
        expectedUniqueRsvpList.add(othrRsvp);
        assertEquals(expectedUniqueRsvpList, uniqueRsvpList);
    }

    @Test
    public void remove_nullRsvp_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRsvpList.remove(null));
    }

    @Test
    public void remove_notExistingRsvp_throwsRsvpNotFoundException() {
        assertThrows(RsvpNotFoundException.class, () -> uniqueRsvpList.remove(validRsvp));
    }

    @Test
    public void remove_existingRsvp_removesRsvp() {
        uniqueRsvpList.add(validRsvp);
        uniqueRsvpList.remove(validRsvp);
        UniqueRsvpList expectedUniqueRsvpList = new UniqueRsvpList();
        assertEquals(expectedUniqueRsvpList, uniqueRsvpList);
    }

    @Test
    public void setPersons_nullUniqueRsvpList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRsvpList.setRsvps((UniqueRsvpList) null));
    }

    @Test
    public void setRsvps_uniqueRsvpList_replacesOwnListWithProvidedUniqueRsvpList() {
        Rsvp othrRsvp = new Rsvp(BASKETBALL, BENSON, RsvpStatus.TBC);
        uniqueRsvpList.add(validRsvp);
        UniqueRsvpList expectedUniqueRsvpList = new UniqueRsvpList();
        expectedUniqueRsvpList.add(othrRsvp);
        uniqueRsvpList.setRsvps(expectedUniqueRsvpList);
        assertEquals(expectedUniqueRsvpList, uniqueRsvpList);
    }

    @Test
    public void setRsvps_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRsvpList.setRsvps((List<Rsvp>) null));
    }

    @Test
    public void setRsvps_list_replacesOwnListWithProvidedList() {
        uniqueRsvpList.add(validRsvp);
        Rsvp othrRsvp = new Rsvp(BASKETBALL, BENSON, RsvpStatus.TBC);
        List<Rsvp> rsvpList = Collections.singletonList(othrRsvp);
        uniqueRsvpList.setRsvps(rsvpList);
        UniqueRsvpList expectedUniqueRsvpList = new UniqueRsvpList();
        expectedUniqueRsvpList.add(othrRsvp);
        assertEquals(expectedUniqueRsvpList, uniqueRsvpList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueRsvpList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueRsvpList.asUnmodifiableObservableList().toString(), uniqueRsvpList.toString());
    }

    @Test
    public void isHashcodeValid() {
        UniqueRsvpList list1 = new UniqueRsvpList();
        UniqueRsvpList list2 = new UniqueRsvpList();
        UniqueRsvpList list3 = new UniqueRsvpList();
        list1.add(validRsvp);
        assertEquals(list2, list3);
        assertNotEquals(list1, list3);
    }
}
