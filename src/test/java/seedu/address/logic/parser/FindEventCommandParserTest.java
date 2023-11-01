package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindEventCommand;
import seedu.address.model.event.NameContainsKeywordsPredicate;

public class FindEventCommandParserTest {
    private FindEventCommandParser parser = new FindEventCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindEventCommand expectedCommand =
                new FindEventCommand(new NameContainsKeywordsPredicate(Arrays.asList("FOC", "FOW")));
        assertParseSuccess(parser, "FOC FOW", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n FOC \n \t FOW  \t", expectedCommand);
    }
}
