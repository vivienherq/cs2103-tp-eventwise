package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.ViewEventCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ViewEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        assertCommandFailure(new ViewEventCommand(INDEX_FIRST_EVENT), model,
                String.format(MESSAGE_SUCCESS, INDEX_FIRST_EVENT.getOneBased(), "Event name"));
    }
}
