package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearGuestsCommandTest {
    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearGuestsCommand(), model, ClearGuestsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        AddressBook expectedAddressBook = getTypicalAddressBook();
        expectedAddressBook.resetGuests();
        Model expectedModel = new ModelManager(expectedAddressBook, new UserPrefs());

        assertCommandSuccess(new ClearGuestsCommand(), model, ClearGuestsCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
