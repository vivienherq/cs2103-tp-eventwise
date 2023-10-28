package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalAddressBook;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ViewVendorsCommandTest {

    @Test
    public void execute_emptyVendorsList_showsEmptyList() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ViewVendorsCommand viewVendorsCommand = new ViewVendorsCommand();
        String expectedString = "";
        String expectedMessage = String.format(ViewVendorsCommand.MESSAGE_SUCCESS, expectedString);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(viewVendorsCommand, model, expectedMessage, expectedModel);
    }

}
