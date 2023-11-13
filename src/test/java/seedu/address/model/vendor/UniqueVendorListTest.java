package seedu.address.model.vendor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVendors.SUN;
import static seedu.address.testutil.TypicalVendors.UNS;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.vendor.exceptions.DuplicateVendorException;
import seedu.address.model.vendor.exceptions.VendorNotFoundException;
import seedu.address.testutil.VendorBuilder;

public class UniqueVendorListTest {
    private final UniqueVendorList uniqueVendorList = new UniqueVendorList();

    @Test
    public void contains_nullVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.contains(null));
    }

    @Test
    public void contains_vendorNotInList_returnsFalse() {
        assertFalse(uniqueVendorList.contains(SUN));
    }

    @Test
    public void contains_vendorInList_returnsTrue() {
        uniqueVendorList.add(SUN);
        assertTrue(uniqueVendorList.contains(SUN));
    }

    @Test
    public void contains_vendorWithSameIdentityFieldsInList_returnsTrue() {
        uniqueVendorList.add(SUN);
        Vendor editedSun = new VendorBuilder(SUN)
                .build();
        assertTrue(uniqueVendorList.contains(editedSun));
    }

    @Test
    public void add_nullVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.add(null));
    }

    @Test
    public void add_duplicateVendor_throwsDuplicateVendorException() {
        uniqueVendorList.add(SUN);
        assertThrows(DuplicateVendorException.class, () -> uniqueVendorList.add(SUN));
    }

    @Test
    public void setVendor_nullTargetVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.setVendor(null, SUN));
    }

    @Test
    public void setVendor_nullEditedVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.setVendor(SUN, null));
    }

    @Test
    public void setVendor_targetVendorNotInList_throwsVendorNotFoundException() {
        assertThrows(VendorNotFoundException.class, () -> uniqueVendorList.setVendor(SUN, SUN));
    }

    @Test
    public void setVendor_editedVendorIsSameVendor_success() {
        uniqueVendorList.add(SUN);
        uniqueVendorList.setVendor(SUN, SUN);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(SUN);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendor_editedVendorHasSameIdentity_success() {
        uniqueVendorList.add(SUN);
        Vendor editedSun = new VendorBuilder(SUN)
                .build();
        uniqueVendorList.setVendor(SUN, editedSun);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(editedSun);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendor_editedVendorHasDifferentIdentity_success() {
        uniqueVendorList.add(SUN);
        uniqueVendorList.setVendor(SUN, UNS);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(UNS);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendor_editedVendorHasNonUniqueIdentity_throwsDuplicateVendorException() {
        uniqueVendorList.add(SUN);
        uniqueVendorList.add(UNS);
        assertThrows(DuplicateVendorException.class, () -> uniqueVendorList.setVendor(SUN, UNS));
    }

    @Test
    public void remove_nullVendorsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.remove(null));
    }

    @Test
    public void remove_vendorDoesNotExist_throwsVendorNotFoundException() {
        assertThrows(VendorNotFoundException.class, () -> uniqueVendorList.remove(SUN));
    }

    @Test
    public void remove_existingVendor_removesVendor() {
        uniqueVendorList.add(SUN);
        uniqueVendorList.remove(SUN);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendors_nullUniqueVendorList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.setVendors((UniqueVendorList) null));
    }

    @Test
    public void setVendors_uniqueVendorList_replacesOwnListWithProvidedUniqueVendorList() {
        uniqueVendorList.add(SUN);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(UNS);
        uniqueVendorList.setVendors(expectedUniqueVendorList);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendors_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.setVendors((List<Vendor>) null));
    }

    @Test
    public void setVendors_list_replacesOwnListWithProvidedList() {
        uniqueVendorList.add(SUN);
        List<Vendor> vendorList = Collections.singletonList(UNS);
        uniqueVendorList.setVendors(vendorList);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(UNS);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendors_listWithDuplicateVendors_throwsDuplicateVendorException() {
        List<Vendor> listWithDuplicateVendors = Arrays.asList(SUN, SUN);
        assertThrows(DuplicateVendorException.class, () -> uniqueVendorList.setVendors(listWithDuplicateVendors));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueVendorList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueVendorList.asUnmodifiableObservableList().toString(), uniqueVendorList.toString());
    }

    @Test
    public void isHashcodeValid() {
        UniqueVendorList list1 = new UniqueVendorList();
        UniqueVendorList list2 = new UniqueVendorList();
        UniqueVendorList list3 = new UniqueVendorList();
        list1.add(SUN);
        assertEquals(list2, list3);
        assertNotEquals(list1, list3);
    }

}
