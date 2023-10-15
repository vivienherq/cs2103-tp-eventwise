package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.ACADEMIC;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_GREATER_THAN_RANGE_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ViewEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_withinRange_success() {
        ViewEventCommand viewEventCommand = new ViewEventCommand(INDEX_FIRST_EVENT);
        String expectedMessage = String.format(ViewEventCommand.MESSAGE_SUCCESS,
                INDEX_FIRST_EVENT.getOneBased(), ACADEMIC.getName().eventName);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(viewEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_outOfRange_failure() {
        ViewEventCommand viewEventCommand = new ViewEventCommand(INDEX_GREATER_THAN_RANGE_EVENT);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        assertCommandFailure(viewEventCommand, model, expectedMessage);
    }
}
