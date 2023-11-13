package seedu.address.model.vendor;

import org.junit.jupiter.api.Test;
import seedu.address.model.vendor.exceptions.DuplicateVendorException;
import seedu.address.model.vendor.exceptions.VendorNotFoundException;
import seedu.address.testutil.VendorBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENDOR_EMAIL_DRINKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENDOR_NAME_DRINKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENDOR_PHONE_DRINKS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVendors.DRINKS;
import static seedu.address.testutil.TypicalVendors.SUN;

public class UniqueVendorListTest {
    private final UniqueVendorList uniqueVendorList = new UniqueVendorList();

    @Test
    public void contains_nullVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.contains(null));
    }

    @Test
    public void contains_vendorNotInList_returnsFalse() {
        assertFalse(uniqueVendorList.contains(DRINKS));
    }

    @Test
    public void contains_vendorInList_returnsTrue() {
        uniqueVendorList.add(DRINKS);
        assertTrue(uniqueVendorList.contains(DRINKS));
    }

    @Test
    public void contains_vendorWithSameIdentityFieldsInList_returnsTrue() {
        uniqueVendorList.add(DRINKS);
        Vendor editedDrinks = new VendorBuilder(DRINKS)
                .withName(VALID_VENDOR_NAME_DRINKS)
                .withEmail(VALID_VENDOR_EMAIL_DRINKS)
                .withPhone(VALID_VENDOR_PHONE_DRINKS)
                .build();
        assertTrue(uniqueVendorList.contains(editedDrinks));
    }

    @Test
    public void add_nullVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.add(null));
    }

    @Test
    public void add_duplicateVendor_throwsDuplicateVendorException() {
        uniqueVendorList.add(DRINKS);
        assertThrows(DuplicateVendorException.class, () -> uniqueVendorList.add(DRINKS));
    }

    @Test
    public void setVendor_nullTargetVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.setVendor(null, DRINKS));
    }

    @Test
    public void setVendor_nullEditedVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.setVendor(DRINKS, null));
    }

    @Test
    public void setVendor_targetVendorNotInList_throwsVendorNotFoundException() {
        assertThrows(VendorNotFoundException.class, () -> uniqueVendorList.setVendor(DRINKS, DRINKS));
    }

    @Test
    public void setVendor_editedVendorIsSameVendor_success() {
        uniqueVendorList.add(DRINKS);
        uniqueVendorList.setVendor(DRINKS, DRINKS);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(DRINKS);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendor_editedVendorHasSameIdentity_success() {
        uniqueVendorList.add(DRINKS);
        Vendor editedDrinks = new VendorBuilder(DRINKS)
                .withName(VALID_VENDOR_NAME_DRINKS)
                .withEmail(VALID_VENDOR_EMAIL_DRINKS)
                .withPhone(VALID_VENDOR_PHONE_DRINKS)
                .build();
        uniqueVendorList.setVendor(DRINKS, editedDrinks);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(editedDrinks);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendor_editedVendorHasDifferentIdentity_success() {
        uniqueVendorList.add(DRINKS);
        uniqueVendorList.setVendor(DRINKS, SUN);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(SUN);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendor_editedVendorHasNonUniqueIdentity_throwsDuplicateVendorException() {
        uniqueVendorList.add(DRINKS);
        uniqueVendorList.add(SUN);
        assertThrows(DuplicateVendorException.class, () -> uniqueVendorList.setVendor(DRINKS, SUN));
    }

    @Test
    public void remove_nullVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.remove(null));
    }

    @Test
    public void remove_vendorDoesNotExist_throwsVendorNotFoundException() {
        assertThrows(VendorNotFoundException.class, () -> uniqueVendorList.remove(DRINKS));
    }

    @Test
    public void remove_existingVendor_removesVendor() {
        uniqueVendorList.add(DRINKS);
        uniqueVendorList.remove(DRINKS);
        UniqueVendorList expectedUniqueVenueList = new UniqueVendorList();
        assertEquals(expectedUniqueVenueList, uniqueVendorList);
    }

    @Test
    public void setVendors_nullUniqueVendorList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.setVendors((UniqueVendorList) null));
    }

    @Test
    public void setVendors_uniqueVendorList_replacesOwnListWithProvidedUniqueVendorList() {
        uniqueVendorList.add(DRINKS);
        UniqueVendorList expectedUniqueVenueList = new UniqueVendorList();
        expectedUniqueVenueList.add(SUN);
        uniqueVendorList.setVendors(expectedUniqueVenueList);
        assertEquals(expectedUniqueVenueList, uniqueVendorList);
    }

    @Test
    public void setVendors_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.setVendors((List<Vendor>) null));
    }

    @Test
    public void setVendors_list_replacesOwnListWithProvidedList() {
        uniqueVendorList.add(DRINKS);
        List<Vendor> vendorList = Collections.singletonList(SUN);
        uniqueVendorList.setVendors(vendorList);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(SUN);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendor_listWithDuplicateVendors_throwsDuplicateVendorException() {
        List<Vendor> listWithDuplicateVendors = Arrays.asList(DRINKS, DRINKS);
        assertThrows(DuplicateVendorException.class, () -> uniqueVendorList.setVendors(listWithDuplicateVendors));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueVendorList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equals() {
        assertTrue(uniqueVendorList.equals(uniqueVendorList));
        uniqueVendorList.add(DRINKS);
        assertFalse(uniqueVendorList.equals(Arrays.asList(DRINKS)));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueVendorList.asUnmodifiableObservableList().toString(), uniqueVendorList.toString());
    }
}
