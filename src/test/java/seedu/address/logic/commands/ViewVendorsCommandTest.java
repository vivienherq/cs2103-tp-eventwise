package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ViewVendorsCommandTest {

    @Test
    public void execute_nonEmptyVendorsList_showsNonEmptyList() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ViewVendorsCommand viewVendorsCommand = new ViewVendorsCommand();
        String expectedString = "1: SUN Caters; Email: catering@sun.com; Phone: 64266426\n"
                + "2: UNS Decorates; Email: decorate@uns.com; Phone: 67896789\n"
                + "3: NSU Drinks; Email: drinks@nsu.com; Phone: 61234567\n";
        String expectedMessage = String.format(ViewVendorsCommand.MESSAGE_SUCCESS, expectedString);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(viewVendorsCommand, model, expectedMessage, expectedModel);
    }

}
