package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // Person

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";

    // Events

    public static final String VALID_EVENT_NAME_CAREER_FAIR = "Career Fair";
    public static final String VALID_EVENT_DESCRIPTION_CAREER_FAIR = "Over 100 companies will be present";
    public static final String VALID_EVENT_FROM_DATE_CAREER_FAIR = "14-11-2024";
    public static final String VALID_EVENT_TO_DATE_CAREER_FAIR = "15-11-2024";

    public static final String VALID_EVENT_NAME_FSC = "FSC 2024";
    public static final String VALID_EVENT_DESCRIPTION_FSC = "Freshman Social Camp 2024";
    public static final String VALID_EVENT_FROM_DATE_FSC = "12-09-2024";
    public static final String VALID_EVENT_TO_DATE_FSC = "15-09-2024";

    // Person Prefixes

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;

    // Event Prefixes
    public static final String EVENT_ID_CAREER_FAIR = " " + PREFIX_EVENT_ID + 1;
    public static final String EVENT_NAME_CAREER_FAIR = " " + PREFIX_EVENT_NAME + VALID_EVENT_NAME_CAREER_FAIR;
    public static final String EVENT_DESC_CAREER_FAIR = " " + PREFIX_EVENT_DESC + VALID_EVENT_DESCRIPTION_CAREER_FAIR;
    public static final String EVENT_FROM_DATE_CAREER_FAIR = " " + PREFIX_EVENT_FROM
            + VALID_EVENT_FROM_DATE_CAREER_FAIR;
    public static final String EVENT_TO_DATE_CAREER_FAIR = " " + PREFIX_EVENT_TO + VALID_EVENT_TO_DATE_CAREER_FAIR;

    public static final String EVENT_ID_FSC = " " + PREFIX_EVENT_ID + 2;
    public static final String EVENT_NAME_FSC = " " + PREFIX_EVENT_NAME + VALID_EVENT_NAME_FSC;
    public static final String EVENT_DESC_FSC = " " + PREFIX_EVENT_DESC + VALID_EVENT_DESCRIPTION_FSC;
    public static final String EVENT_FROM_DATE_FSC = " " + PREFIX_EVENT_FROM + VALID_EVENT_FROM_DATE_FSC;
    public static final String EVENT_TO_DATE_FSC = " " + PREFIX_EVENT_TO + VALID_EVENT_TO_DATE_FSC;


    // '&' not allowed in names
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&";
    // 'a' not allowed in phones
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a";
    // missing '@' symbol
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo";
    // '' not allowed in names
    public static final String INVALID_EVENT_NAME = " " + PREFIX_EVENT_NAME;
    // '' not allowed in descriptions
    public static final String INVALID_EVENT_DESC = " " + PREFIX_EVENT_DESC;
    // '&30-30-2000 not allowed in dates
    public static final String INVALID_EVENT_FROM_DATE = " " + PREFIX_EVENT_FROM + "30-30-2000";
    public static final String INVALID_EVENT_TO_DATE = " " + PREFIX_EVENT_TO + "30-30-2000";


    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final EditEventCommand.EditEventDescriptor DESC_FSC;
    public static final EditEventCommand.EditEventDescriptor DESC_CAREER;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .build();
        DESC_FSC = new EditEventDescriptorBuilder().withName(VALID_EVENT_NAME_FSC)
                .withDescription(VALID_EVENT_DESCRIPTION_FSC).withFromDate(VALID_EVENT_FROM_DATE_FSC)
                .withToDate(VALID_EVENT_TO_DATE_FSC).build();
        DESC_CAREER = new EditEventDescriptorBuilder().withName(VALID_EVENT_NAME_CAREER_FAIR)
                .withDescription(VALID_EVENT_DESCRIPTION_CAREER_FAIR).withFromDate(VALID_EVENT_FROM_DATE_CAREER_FAIR)
                .withToDate(VALID_EVENT_FROM_DATE_CAREER_FAIR).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }
}
