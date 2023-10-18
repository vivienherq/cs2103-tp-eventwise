package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.FSC;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_GREATER_THAN_RANGE_EVENT;

public class ViewEventsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_emptyEventList_showsEmptyList() {

        ViewEventsCommand viewEventsCommand = new ViewEventsCommand();
        String expectedString = "List of Events:\n" +
                "1: FSC 2023; Description: Freshman Social Camp 2023; Date: 12-09-2023\n" +
                "2: FOC 2023; Description: Freshman Orientation Camp 2023; Date: 01-02-2023\n" +
                "3: FOW 2023; Description: Freshman Orientation Camp 2023; Date: 01-02-2023\n" +
                "4: Academic Awards Ceremony; Description: Celebrating student and research achievements; Date: 08-11-2023\n" +
                "5: Basketball Competition; Description: Inter Faculty Games Basketball Finals; Date: 05-11-2023\n" +
                "6: Code Competition; Description: Code for a good cause; Date: 19-12-2023\n" +
                "7: Diversity Women in Tech; Description: Promoting diversity and inclusion in STEM fields; Date: 21-11-2023\n" +
                "8: Tech Expo; Description: Explore the latest in technology and innovation; Date: 16-12-2023";
        String expectedMessage = String.format(ViewEventsCommand.MESSAGE_SUCCESS, expectedString);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(viewEventsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonEmptyEventList_showsNonEmptyList() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ViewEventsCommand viewEventsCommand = new ViewEventsCommand();
        String expectedString = "1: FSC 2023; Description: Freshman Social Camp 2023; Date: 12-09-2023\n" +
                "2: FOC 2023; Description: Freshman Orientation Camp 2023; Date: 01-02-2023\n" +
                "3: FOW 2023; Description: Freshman Orientation Camp 2023; Date: 01-02-2023\n" +
                "4: Academic Awards Ceremony; Description: Celebrating student and research achievements; Date: 08-11-2023\n" +
                "5: Basketball Competition; Description: Inter Faculty Games Basketball Finals; Date: 05-11-2023\n" +
                "6: Code Competition; Description: Code for a good cause; Date: 19-12-2023\n" +
                "7: Diversity Women in Tech; Description: Promoting diversity and inclusion in STEM fields; Date: 21-11-2023\n" +
                "8: Tech Expo; Description: Explore the latest in technology and innovation; Date: 16-12-2023\n";
        String expectedMessage = String.format(ViewEventsCommand.MESSAGE_SUCCESS, expectedString);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(viewEventsCommand, model, expectedMessage, expectedModel);
    }

}
