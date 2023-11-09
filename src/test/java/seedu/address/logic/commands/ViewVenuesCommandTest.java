package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ViewVenuesCommandTest {

    @Test
    public void execute_nonEmptyVenuesList_showsNonEmptyList() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ViewVenuesCommand viewVenuesCommand = new ViewVenuesCommand();
        String expectedResult = String.format(ViewVenuesCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(viewVenuesCommand, model, expectedResult, expectedModel);
    }
}
