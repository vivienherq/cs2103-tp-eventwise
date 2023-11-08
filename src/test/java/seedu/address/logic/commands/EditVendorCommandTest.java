package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VENDOR;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_VENDOR;
import static seedu.address.testutil.TypicalVendors.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditVendorCommand.EditVendorDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.vendor.Vendor;
import seedu.address.testutil.*;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditVendorCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Vendor editedVendor = new VendorBuilder().build();
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder(editedVendor).build();
        EditVendorCommand editVendorCommand = new EditVendorCommand(INDEX_FIRST_VENDOR, descriptor);

        String expectedMessage = String.format(editVendorCommand.MESSAGE_EDIT_VENDOR_SUCCESS,
                Messages.format(editedVendor));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setVendor(model.getFilteredVendorList().get(0), editedVendor);

        assertCommandSuccess(editVendorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastVendor = Index.fromOneBased(model.getFilteredVendorList().size());
        Vendor lastVendor = model.getFilteredVendorList().get(indexLastVendor.getZeroBased());

        VendorBuilder vendorInList = new VendorBuilder(lastVendor);
        Vendor editedVendor = vendorInList.withName(VALID_VENDOR_NAME_FOOD).withPhone(VALID_VENDOR_PHONE_FOOD)
                .build();

        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withName(VALID_VENDOR_NAME_FOOD)
                .withPhone(VALID_VENDOR_PHONE_FOOD).build();
        EditVendorCommand editVendorCommand = new EditVendorCommand(indexLastVendor, descriptor);

        String expectedMessage = String.format(EditVendorCommand.MESSAGE_EDIT_VENDOR_SUCCESS,
                Messages.format(editedVendor));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setVendor(lastVendor, editedVendor);

        assertCommandSuccess(editVendorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditVendorCommand editVendorCommand = new EditVendorCommand(INDEX_FIRST_VENDOR, new EditVendorDescriptor());
        Vendor editedVendor = model.getFilteredVendorList().get(INDEX_FIRST_VENDOR.getZeroBased());

        String expectedMessage = String.format(EditVendorCommand.MESSAGE_EDIT_VENDOR_SUCCESS,
                Messages.format(editedVendor));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editVendorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Vendor firstVendor = model.getFilteredVendorList().get(INDEX_FIRST_VENDOR.getZeroBased());
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder(firstVendor).build();
        EditVendorCommand editVendorCommand = new EditVendorCommand(INDEX_SECOND_VENDOR, descriptor);

        assertCommandFailure(editVendorCommand, model, EditVendorCommand.MESSAGE_DUPLICATE_VENDOR);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditVendorCommand standardCommand = new EditVendorCommand(INDEX_FIRST_VENDOR, DESC_DRINKS);

        // same values -> returns true
        EditVendorDescriptor copyDescriptor = new EditVendorDescriptor(DESC_DRINKS);
        EditVendorCommand commandWithSameValues = new EditVendorCommand(INDEX_FIRST_VENDOR, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearVendorsCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditVendorCommand(INDEX_SECOND_VENDOR, DESC_DRINKS)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditVendorCommand(INDEX_FIRST_VENDOR, DESC_FOOD)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditVendorDescriptor editVendorDescriptor = new EditVendorDescriptor();
        EditVendorCommand editVendorCommand = new EditVendorCommand(index, editVendorDescriptor);
        String expected = EditVendorCommand.class.getCanonicalName() + "{index=" + index + ", editVendorDescriptor="
                + editVendorDescriptor + "}";
        assertEquals(expected, editVendorCommand.toString());
    }
}
