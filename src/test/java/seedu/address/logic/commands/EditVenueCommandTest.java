package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CLB;
import static seedu.address.logic.commands.CommandTestUtil.DESC_LT27;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_ADDRESS_COM1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_NAME_CLB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_NAME_COM1;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VENUE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_VENUE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditVenueCommand.EditVenueDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.venue.Venue;
import seedu.address.testutil.EditVenueDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.VenueBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditVenueCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Venue editedVenue = new VenueBuilder().build();
        EditVenueDescriptor descriptor = new EditVenueDescriptorBuilder(editedVenue).build();
        EditVenueCommand editVenueCommand = new EditVenueCommand(INDEX_FIRST_VENUE, descriptor);

        String expectedMessage = String.format(editVenueCommand.MESSAGE_EDIT_VENUE_SUCCESS,
                Messages.format(editedVenue));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setVenue(model.getFilteredVenuesList().get(0), editedVenue);

        assertCommandSuccess(editVenueCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allFieldsSpecifiedVenueInEvent_success() {
        Venue firstVenue = model.getFilteredVenuesList().get(INDEX_FIRST_VENUE.getZeroBased());
        Venue editedVenue = new VenueBuilder().build();

        Event firstEvent = model.getFilteredEventsList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event modifiedEvent = new EventBuilder(firstEvent).withVenue(firstVenue).build();

        model.setEvent(firstEvent, modifiedEvent);
        model.setEventToView(modifiedEvent);

        EditVenueDescriptor descriptor = new EditVenueDescriptorBuilder(editedVenue).build();
        EditVenueCommand editVenueCommand = new EditVenueCommand(INDEX_FIRST_VENUE, descriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        modifiedEvent = new EventBuilder(firstEvent).withVenue(editedVenue).build();

        expectedModel.setEvent(firstEvent, modifiedEvent);

        String expectedMessage = String.format(editVenueCommand.MESSAGE_EDIT_VENUE_SUCCESS,
                Messages.format(editedVenue));

        assertCommandSuccess(editVenueCommand, model, expectedMessage, expectedModel);
        assertTrue(model.getEventToView().getVenue().isSameVenue(editedVenue));
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastVenue = Index.fromOneBased(model.getFilteredVenuesList().size());
        Venue lastVenue = model.getFilteredVenuesList().get(indexLastVenue.getZeroBased());

        VenueBuilder venueInList = new VenueBuilder(lastVenue);
        Venue editedVenue = venueInList.withName(VALID_VENUE_NAME_COM1).withAddress(VALID_VENUE_ADDRESS_COM1)
                .build();

        EditVenueDescriptor descriptor = new EditVenueDescriptorBuilder().withName(VALID_VENUE_NAME_COM1)
                .withAddress(VALID_VENUE_ADDRESS_COM1).build();
        EditVenueCommand editVenueCommand = new EditVenueCommand(indexLastVenue, descriptor);

        String expectedMessage = String.format(EditVenueCommand.MESSAGE_EDIT_VENUE_SUCCESS,
                Messages.format(editedVenue));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setVenue(lastVenue, editedVenue);

        assertCommandSuccess(editVenueCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditVenueCommand editVenueCommand = new EditVenueCommand(INDEX_FIRST_VENUE, new EditVenueDescriptor());
        Venue editedVenue = model.getFilteredVenuesList().get(INDEX_FIRST_VENUE.getZeroBased());

        String expectedMessage = String.format(EditVenueCommand.MESSAGE_EDIT_VENUE_SUCCESS,
                Messages.format(editedVenue));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editVenueCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateVenueUnfilteredList_failure() {
        Venue firstVenue = model.getFilteredVenuesList().get(INDEX_FIRST_VENUE.getZeroBased());
        EditVenueDescriptor descriptor = new EditVenueDescriptorBuilder(firstVenue).build();
        EditVenueCommand editVenueCommand = new EditVenueCommand(INDEX_SECOND_VENUE, descriptor);

        assertCommandFailure(editVenueCommand, model, EditVenueCommand.MESSAGE_DUPLICATE_VENUE);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredVenuesList().size() + 1);
        EditVenueDescriptor descriptor = new EditVenueDescriptorBuilder().withName(VALID_VENUE_NAME_CLB).build();
        EditVenueCommand editVenueCommand = new EditVenueCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editVenueCommand, model, Messages.MESSAGE_INVALID_VENUE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditVenueCommand standardCommand = new EditVenueCommand(INDEX_FIRST_VENUE, DESC_LT27);

        // same values -> returns true
        EditVenueDescriptor copyDescriptor = new EditVenueDescriptor(DESC_LT27);
        EditVenueCommand commandWithSameValues = new EditVenueCommand(INDEX_FIRST_VENUE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearVenuesCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditVenueCommand(INDEX_SECOND_VENUE, DESC_LT27)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditVenueCommand(INDEX_FIRST_VENUE, DESC_CLB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditVenueDescriptor editVenueDescriptor = new EditVenueDescriptor();
        EditVenueCommand editVenueCommand = new EditVenueCommand(index, editVenueDescriptor);
        String expected = EditVenueCommand.class.getCanonicalName() + "{index=" + index + ", editVenueDescriptor="
                + editVenueDescriptor + "}";
        assertEquals(expected, editVenueCommand.toString());
    }

}
