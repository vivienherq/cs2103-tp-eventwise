package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.RemovePersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class RemovePersonCommandParserTest {
    private RemovePersonCommandParser parser = new RemovePersonCommandParser();

    @Test
    public void parse_indexSpecified_success() {
        String userInput = String.format(" %s%d %s%d",
                PREFIX_EVENT_ID, INDEX_FIRST_EVENT.getOneBased(),
                PREFIX_PERSON, INDEX_SECOND_PERSON.getOneBased());
        RemovePersonCommand expectedCommand = new RemovePersonCommand(INDEX_FIRST_EVENT, INDEX_SECOND_PERSON);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_duplicateIndexPrefix_failure() {
        String userInput = String.format(" %s%d %s%d",
                PREFIX_EVENT_ID, INDEX_FIRST_EVENT.getOneBased(),
                PREFIX_EVENT_ID, INDEX_SECOND_EVENT.getOneBased());
        String expectedMessage = String.format(Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_ID));
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_preambleBeforeIndexPrefix_failure() {
        String userInput = String.format("%s %s%d %s%d", PREAMBLE_NON_EMPTY,
                PREFIX_EVENT_ID, INDEX_FIRST_EVENT.getOneBased(),
                PREFIX_PERSON, INDEX_SECOND_PERSON.getOneBased());
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePersonCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_preambleBeforeIndexPrefix_throwsParseException() {
        String userInput = String.format("%s %s%d ", PREAMBLE_NON_EMPTY,
                PREFIX_EVENT_ID, INDEX_FIRST_EVENT.getOneBased(),
                PREFIX_PERSON, INDEX_SECOND_PERSON.getOneBased());
        RemovePersonCommandParser removePersonCommandParser = new RemovePersonCommandParser();
        assertThrows(ParseException.class, () -> removePersonCommandParser.parse(userInput));
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePersonCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, RemovePersonCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, RemovePersonCommand.COMMAND_WORD, expectedMessage);
    }
}
