package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.FSC;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VENDOR;
import static seedu.address.testutil.TypicalIndexes.INDEX_OUT_OF_RANGE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_VENDOR;
import static seedu.address.testutil.TypicalVendors.SUN;

import java.util.List;

import org.junit.jupiter.api.Test;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.vendor.Vendor;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TypicalAddressBook;

public class RemoveVendorCommandTest {
    private Model model = new ModelManager(TypicalAddressBook.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexWithinRange_success() {
        // Get a copy of the model a.k.a the expected model
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        // Get the test event
        Event testEvent = expectedModel.getAddressBook().getEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        // Get the first vendor
        Vendor testVendor = expectedModel.getAddressBook().getVendorList().get(INDEX_FIRST_VENDOR.getZeroBased());
        // Build edited event
        Event testEditedEvent = new EventBuilder(testEvent).withVendors(List.of(testVendor)).build();
        // Set the changes to expected model
        model.setEvent(testEvent, testEditedEvent);

        // Test the remove person command
        RemoveVendorCommand removeVendorCommand = new RemoveVendorCommand(INDEX_FIRST_EVENT, INDEX_FIRST_VENDOR);
        String expectedMessage = String.format(RemoveVendorCommand.MESSAGE_SUCCESS,
                INDEX_FIRST_VENDOR.getOneBased(), SUN.getName().vendorName,
                INDEX_FIRST_EVENT.getOneBased(), FSC.getName().eventName);

        assertCommandSuccess(removeVendorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEventIndexOutOfRange_failure() {
        RemoveVendorCommand removeVendorCommand = new RemoveVendorCommand(INDEX_OUT_OF_RANGE, INDEX_FIRST_VENDOR);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        assertCommandFailure(removeVendorCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidVendorIndexOutOfRange_failure() {
        RemoveVendorCommand removeVendorCommand = new RemoveVendorCommand(INDEX_FIRST_EVENT, INDEX_OUT_OF_RANGE);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_VENDOR_INDEX);

        assertCommandFailure(removeVendorCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        final RemoveVendorCommand standardCommand = new RemoveVendorCommand(INDEX_FIRST_EVENT, INDEX_SECOND_VENDOR);

        // same values -> returns true
        RemoveVendorCommand commandWithSameValues = new RemoveVendorCommand(INDEX_FIRST_EVENT, INDEX_SECOND_VENDOR);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemoveVendorCommand(INDEX_SECOND_EVENT, INDEX_SECOND_VENDOR)));
    }
}
