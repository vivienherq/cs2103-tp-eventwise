package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DRINKS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_FOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENDOR_NAME_FOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENDOR_PHONE_FOOD;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VENDOR;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_VENDOR;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditVendorCommand.EditVendorDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.vendor.Vendor;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditVendorDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.VendorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditVendorCommandTest {

    private Model model;

    @BeforeEach
    public void init() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

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
    public void execute_allFieldsSpecifiedVendorInEvent_success() {
        Vendor firstVendor = model.getFilteredVendorList().get(INDEX_FIRST_VENDOR.getZeroBased());
        Vendor editedVendor = new VendorBuilder().build();

        Event firstEvent = model.getFilteredEventsList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event modifiedEvent = new EventBuilder(firstEvent).withVendors(List.of(firstVendor)).build();

        model.setEvent(firstEvent, modifiedEvent);
        model.setEventToView(modifiedEvent);

        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder(editedVendor).build();
        EditVendorCommand editVendorCommand = new EditVendorCommand(INDEX_FIRST_VENDOR, descriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        modifiedEvent = new EventBuilder(firstEvent).withVendors(List.of(editedVendor)).build();

        expectedModel.setEvent(firstEvent, modifiedEvent);

        String expectedMessage = String.format(editVendorCommand.MESSAGE_EDIT_VENDOR_SUCCESS,
                Messages.format(editedVendor));

        assertCommandSuccess(editVendorCommand, model, expectedMessage, expectedModel);
        assertTrue(model.getEventToView().getVendors().contains(editedVendor));

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
    public void execute_invalidVendorIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredVendorList().size() + 1);
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().build();
        EditVendorCommand editVendorCommand = new EditVendorCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editVendorCommand, model, Messages.MESSAGE_INVALID_VENDOR_INDEX);
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
