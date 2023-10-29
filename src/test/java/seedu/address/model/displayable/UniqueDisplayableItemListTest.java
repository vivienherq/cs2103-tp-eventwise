package seedu.address.model.displayable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_CAREER_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_CAREER_FAIR;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.FOC;
import static seedu.address.testutil.TypicalEvents.FSC;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.displayable.exceptions.DisplayableItemNotFoundException;
import seedu.address.model.displayable.exceptions.DuplicateDisplayableItemException;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

public class UniqueDisplayableItemListTest {
    private final UniqueDisplayableItemList uniqueDisplayableItemList = new UniqueDisplayableItemList();

    @Test
    public void contains_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDisplayableItemList.contains(null));
    }

    @Test
    public void contains_displayableItemNotInList_returnsFalse() {
        assertFalse(uniqueDisplayableItemList.contains(FSC));
    }

    @Test
    public void contains_displayableItemInList_returnsTrue() {
        uniqueDisplayableItemList.add(FSC);
        assertTrue(uniqueDisplayableItemList.contains(FSC));
    }

    @Test
    public void contains_displayableItemWithSameIdentityFieldsInList_returnsTrue() {
        uniqueDisplayableItemList.add(FSC);
        Event editedFsc = new EventBuilder(FSC)
                .withDescription(VALID_EVENT_DESCRIPTION_CAREER_FAIR)
                .withDate(VALID_EVENT_DATE_CAREER_FAIR)
                .build();
        assertTrue(uniqueDisplayableItemList.contains(editedFsc));
    }

    @Test
    public void add_nullDisplayableItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDisplayableItemList.add(null));
    }

    @Test
    public void add_duplicateDisplayableItem_throwsDuplicateDisplayableItemException() {
        uniqueDisplayableItemList.add(FSC);
        assertThrows(DuplicateDisplayableItemException.class, () -> uniqueDisplayableItemList.add(FSC));
    }

    @Test
    public void setDisplayableItem_nullTargetDisplayableItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDisplayableItemList.setDisplayableItem(null, FSC));
    }

    @Test
    public void setDisplayableItem_nullEditedDisplayableItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueDisplayableItemList.setDisplayableItem(FSC, null));
    }

    @Test
    public void setDisplayableItem_targetDisplayableItemNotInList_throwsDisplayableItemNotFoundException() {
        assertThrows(DisplayableItemNotFoundException.class, () ->
                uniqueDisplayableItemList.setDisplayableItem(FSC, FSC));
    }

    @Test
    public void setDisplayableItem_editedDisplayableItemIsSameDisplayableItem_success() {
        uniqueDisplayableItemList.add(FSC);
        uniqueDisplayableItemList.setDisplayableItem(FSC, FSC);
        UniqueDisplayableItemList expectedUniqueDisplayableItemList = new UniqueDisplayableItemList();
        expectedUniqueDisplayableItemList.add(FSC);
        assertEquals(expectedUniqueDisplayableItemList, uniqueDisplayableItemList);
    }

    @Test
    public void setDisplayableItem_editedDisplayableItemHasSameIdentity_success() {
        uniqueDisplayableItemList.add(FSC);
        Event editedFsc = new EventBuilder(FSC)
                .withDescription(VALID_EVENT_DESCRIPTION_CAREER_FAIR)
                .withDate(VALID_EVENT_DATE_CAREER_FAIR)
                .build();
        uniqueDisplayableItemList.setDisplayableItem(FSC, editedFsc);
        UniqueDisplayableItemList expectedUniqueDisplayableItemList = new UniqueDisplayableItemList();
        expectedUniqueDisplayableItemList.add(editedFsc);
        assertEquals(expectedUniqueDisplayableItemList, uniqueDisplayableItemList);
    }

    @Test
    public void setDisplayableItem_editedDisplayableItemHasDifferentIdentity_success() {
        uniqueDisplayableItemList.add(FSC);
        uniqueDisplayableItemList.setDisplayableItem(FSC, FOC);
        UniqueDisplayableItemList expectedUniqueDisplayableItemList = new UniqueDisplayableItemList();
        expectedUniqueDisplayableItemList.add(FOC);
        assertEquals(expectedUniqueDisplayableItemList, uniqueDisplayableItemList);
    }

    @Test
    public void setDisplayableItem_editedDisplayableItemHasNonUniqueIdentity_throwsDuplicateDisplayableItemException() {
        uniqueDisplayableItemList.add(FSC);
        uniqueDisplayableItemList.add(FOC);
        assertThrows(DuplicateDisplayableItemException.class, () ->
                uniqueDisplayableItemList.setDisplayableItem(FSC, FOC));
    }

    @Test
    public void remove_nullDisplayableItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDisplayableItemList.remove(null));
    }

    @Test
    public void remove_displayableItemDoesNotExist_throwsDisplayableItemNotFoundException() {
        assertThrows(DisplayableItemNotFoundException.class, () -> uniqueDisplayableItemList.remove(FSC));
    }

    @Test
    public void remove_existingDisplayableItem_removesEvent() {
        uniqueDisplayableItemList.add(FSC);
        uniqueDisplayableItemList.remove(FSC);
        UniqueDisplayableItemList expectedUniqueDisplayableItemList = new UniqueDisplayableItemList();
        assertEquals(expectedUniqueDisplayableItemList, uniqueDisplayableItemList);
    }

    @Test
    public void setEvents_nullUniqueDisplayableItemList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueDisplayableItemList.setDisplayableItems((UniqueDisplayableItemList) null));
    }

    @Test
    public void setDisplayableItems_uniqueDisplayableItemList_replacesOwnListWithProvidedUniqueDisplayableItemList() {
        uniqueDisplayableItemList.add(FSC);
        UniqueDisplayableItemList expectedUniqueDisplayableItemList = new UniqueDisplayableItemList();
        expectedUniqueDisplayableItemList.add(FOC);
        uniqueDisplayableItemList.setDisplayableItems(expectedUniqueDisplayableItemList);
        assertEquals(expectedUniqueDisplayableItemList, uniqueDisplayableItemList);
    }

    @Test
    public void setDisplayableItems_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueDisplayableItemList.setDisplayableItems((List<DisplayableListViewItem>) null));
    }

    @Test
    public void setDisplayableItems_list_replacesOwnListWithProvidedList() {
        uniqueDisplayableItemList.add(FSC);
        List<DisplayableListViewItem> itemsList = Collections.singletonList(FOC);
        uniqueDisplayableItemList.setDisplayableItems(itemsList);
        UniqueDisplayableItemList expectedUniqueDisplayableItemList = new UniqueDisplayableItemList();
        expectedUniqueDisplayableItemList.add(FOC);
        assertEquals(expectedUniqueDisplayableItemList, uniqueDisplayableItemList);
    }

    @Test
    public void setDisplayableItems_listWithDuplicateDisplayableItems_throwsDuplicateEventException() {
        List<DisplayableListViewItem> listWithDuplicateDisplayableItems = Arrays.asList(FSC, FSC);
        assertThrows(DuplicateDisplayableItemException.class, () ->
                uniqueDisplayableItemList.setDisplayableItems(listWithDuplicateDisplayableItems));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueDisplayableItemList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equals() {
        assertTrue(uniqueDisplayableItemList.equals(uniqueDisplayableItemList));
        uniqueDisplayableItemList.add(FOC);
        assertFalse(uniqueDisplayableItemList.equals(Arrays.asList(FOC)));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueDisplayableItemList.asUnmodifiableObservableList().toString(),
                uniqueDisplayableItemList.toString());
    }
}
