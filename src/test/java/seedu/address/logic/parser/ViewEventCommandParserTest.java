package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewEventCommand;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

public class ViewEventCommandParserTest {
    private ViewEventCommandParser parser = new ViewEventCommandParser();

    @Test
    public void parse_indexSpecified_success() {
        String userInput = String.format(" %s%d", PREFIX_EVENT_ID, INDEX_FIRST_EVENT.getOneBased());
        ViewEventCommand expectedCommand = new ViewEventCommand(INDEX_FIRST_EVENT);
        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewEventCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, ViewEventCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, ViewEventCommand.COMMAND_WORD, expectedMessage);
    }
}
