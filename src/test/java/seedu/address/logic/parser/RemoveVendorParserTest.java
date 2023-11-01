package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VENDOR;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.RemoveVendorCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class RemoveVendorParserTest {
    private RemoveVendorCommandParser parser = new RemoveVendorCommandParser();

    @Test
    public void parse_indexSpecified_success() {
        String userInput = String.format(" %s%d %s%d",
                PREFIX_EVENT_ID, INDEX_FIRST_EVENT.getOneBased(),
                PREFIX_VENDOR, INDEX_FIRST_VENDOR.getOneBased());
        RemoveVendorCommand expectedCommand = new RemoveVendorCommand(INDEX_FIRST_EVENT, INDEX_FIRST_VENDOR);
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
                PREFIX_VENDOR, INDEX_FIRST_VENDOR.getOneBased());
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveVendorCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_preambleBeforeIndexPrefix_throwsParseException() {
        String userInput = String.format("%s %s%d ", PREAMBLE_NON_EMPTY,
                PREFIX_EVENT_ID, INDEX_FIRST_EVENT.getOneBased(),
                PREFIX_VENDOR, INDEX_FIRST_VENDOR.getOneBased());
        RemoveVendorCommandParser removeVendorCommandParser = new RemoveVendorCommandParser();
        assertThrows(ParseException.class, () -> removeVendorCommandParser.parse(userInput));
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveVendorCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, RemoveVendorCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, RemoveVendorCommand.COMMAND_WORD, expectedMessage);
    }
}
