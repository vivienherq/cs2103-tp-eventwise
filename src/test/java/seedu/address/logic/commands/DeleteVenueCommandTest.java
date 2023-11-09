package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VENUE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_VENUE;
import static seedu.address.testutil.TypicalVenues.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.venue.Venue;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteVenueCommand}.
 */
public class DeleteVenueCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Venue venueToDelete = model.getFilteredVenuesList().get(INDEX_FIRST_VENUE.getZeroBased());
        DeleteVenueCommand deleteVenueCommand = new DeleteVenueCommand(INDEX_FIRST_VENUE);

        String expectedMessage = String.format(DeleteVenueCommand.MESSAGE_DELETE_VENUE_SUCCESS,
                INDEX_FIRST_VENUE.getOneBased(), Messages.format(venueToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteVenue(venueToDelete);

        assertCommandSuccess(deleteVenueCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredVenuesList().size() + 1);
        DeleteVenueCommand deleteVenueCommand = new DeleteVenueCommand(outOfBoundIndex);

        assertCommandFailure(deleteVenueCommand, model, Messages.MESSAGE_INVALID_VENUE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteVenueCommand deleteFirstCommand = new DeleteVenueCommand(INDEX_FIRST_VENUE);
        DeleteVenueCommand deleteSecondCommand = new DeleteVenueCommand(INDEX_SECOND_VENUE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteVenueCommand deleteFirstCommandCopy = new DeleteVenueCommand(INDEX_FIRST_VENUE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteVenueCommand deleteVenueCommand = new DeleteVenueCommand(targetIndex);
        String expected = DeleteVenueCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteVenueCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
