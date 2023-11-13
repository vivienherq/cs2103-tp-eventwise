package seedu.address.model.venue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_ADDRESS_CLB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_CAPACITY_CLB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_NAME_CLB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVenues.CLB;
import static seedu.address.testutil.TypicalVenues.LT27;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.venue.exceptions.DuplicateVenueException;
import seedu.address.model.venue.exceptions.VenueNotFoundException;
import seedu.address.testutil.VenueBuilder;

public class UniqueVenueListTest {
    private final UniqueVenueList uniqueVenueList = new UniqueVenueList();

    @Test
    public void contains_nullVenue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVenueList.contains(null));
    }

    @Test
    public void contains_venueNotInList_returnsFalse() {
        assertFalse(uniqueVenueList.contains(CLB));
    }

    @Test
    public void contains_venueInList_returnsTrue() {
        uniqueVenueList.add(CLB);
        assertTrue(uniqueVenueList.contains(CLB));
    }

    @Test
    public void contains_venueWithSameIdentityFieldsInList_returnsTrue() {
        uniqueVenueList.add(CLB);
        Venue editedClb = new VenueBuilder(CLB)
                .withName(VALID_VENUE_NAME_CLB)
                .withAddress(VALID_VENUE_ADDRESS_CLB)
                .withCapacity(VALID_VENUE_CAPACITY_CLB)
                .build();
        assertTrue(uniqueVenueList.contains(editedClb));
    }

    @Test
    public void add_nullVenue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVenueList.add(null));
    }

    @Test
    public void add_duplicateVenue_throwsDuplicateVenueException() {
        uniqueVenueList.add(CLB);
        assertThrows(DuplicateVenueException.class, () -> uniqueVenueList.add(CLB));
    }

    @Test
    public void setVenue_nullTargetVenue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVenueList.setVenue(null, CLB));
    }

    @Test
    public void setVenue_nullEditedVenue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVenueList.setVenue(CLB, null));
    }

    @Test
    public void setEvent_targetVenueNotInList_throwsVenueNotFoundException() {
        assertThrows(VenueNotFoundException.class, () -> uniqueVenueList.setVenue(CLB, CLB));
    }

    @Test
    public void setVenue_editedVenueIsSameVenue_success() {
        uniqueVenueList.add(CLB);
        uniqueVenueList.setVenue(CLB, CLB);
        UniqueVenueList expectedUniqueVenueList = new UniqueVenueList();
        expectedUniqueVenueList.add(CLB);
        assertEquals(expectedUniqueVenueList, uniqueVenueList);
    }

    @Test
    public void setVenue_editedVenueHasSameIdentity_success() {
        uniqueVenueList.add(CLB);
        Venue editedClb = new VenueBuilder(CLB)
                .withName(VALID_VENUE_NAME_CLB)
                .withAddress(VALID_VENUE_ADDRESS_CLB)
                .withCapacity(VALID_VENUE_CAPACITY_CLB)
                .build();
        uniqueVenueList.setVenue(CLB, editedClb);
        UniqueVenueList expectedUniqueVenueList = new UniqueVenueList();
        expectedUniqueVenueList.add(editedClb);
        assertEquals(expectedUniqueVenueList, uniqueVenueList);
    }

    @Test
    public void setVenue_editedVenueHasDifferentIdentity_success() {
        uniqueVenueList.add(CLB);
        uniqueVenueList.setVenue(CLB, LT27);
        UniqueVenueList expectedUniqueVenueList = new UniqueVenueList();
        expectedUniqueVenueList.add(LT27);
        assertEquals(expectedUniqueVenueList, uniqueVenueList);
    }

    @Test
    public void setVenue_editedVenueHasNonUniqueIdentity_throwsDuplicateVenueException() {
        uniqueVenueList.add(CLB);
        uniqueVenueList.add(LT27);
        assertThrows(DuplicateVenueException.class, () -> uniqueVenueList.setVenue(CLB, LT27));
    }

    @Test
    public void remove_nullVenue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVenueList.remove(null));
    }

    @Test
    public void remove_venueDoesNotExist_throwsVenueNotFoundException() {
        assertThrows(VenueNotFoundException.class, () -> uniqueVenueList.remove(CLB));
    }

    @Test
    public void remove_existingVenue_removesVenue() {
        uniqueVenueList.add(CLB);
        uniqueVenueList.remove(CLB);
        UniqueVenueList expectedUniqueVenueList = new UniqueVenueList();
        assertEquals(expectedUniqueVenueList, uniqueVenueList);
    }

    @Test
    public void setVenues_nullUniqueVenueList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVenueList.setVenues((UniqueVenueList) null));
    }

    @Test
    public void setVenues_uniqueVenueList_replacesOwnListWithProvidedUniqueVenueList() {
        uniqueVenueList.add(CLB);
        UniqueVenueList expectedUniqueVenueList = new UniqueVenueList();
        expectedUniqueVenueList.add(LT27);
        uniqueVenueList.setVenues(expectedUniqueVenueList);
        assertEquals(expectedUniqueVenueList, uniqueVenueList);
    }

    @Test
    public void setVenues_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVenueList.setVenues((List<Venue>) null));
    }

    @Test
    public void setVenues_list_replacesOwnListWithProvidedList() {
        uniqueVenueList.add(LT27);
        List<Venue> vendorList = Collections.singletonList(CLB);
        uniqueVenueList.setVenues(vendorList);
        UniqueVenueList expectedUniqueVenueList = new UniqueVenueList();
        expectedUniqueVenueList.add(CLB);
        assertEquals(expectedUniqueVenueList, uniqueVenueList);
    }

    @Test
    public void setVenues_listWithDuplicateVenues_throwsDuplicateVenueException() {
        List<Venue> listWithDuplicateVenues = Arrays.asList(CLB, CLB);
        assertThrows(DuplicateVenueException.class, () -> uniqueVenueList.setVenues(listWithDuplicateVenues));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueVenueList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equals() {
        assertTrue(uniqueVenueList.equals(uniqueVenueList));
        uniqueVenueList.add(CLB);
        assertFalse(uniqueVenueList.equals(Arrays.asList(CLB)));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueVenueList.asUnmodifiableObservableList().toString(), uniqueVenueList.toString());
    }
}
