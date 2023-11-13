package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RSVP_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RsvpCommand;
import seedu.address.model.rsvp.RsvpStatus;

public class RsvpCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RsvpCommand.MESSAGE_USAGE);

    private RsvpCommandParser parser = new RsvpCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // Missing PREFIX_EVENT_ID
        assertParseFailure(parser, RsvpCommand.COMMAND_WORD + " "
                + PREFIX_PERSON + "1 " + PREFIX_RSVP_STATUS + "CC" , MESSAGE_INVALID_FORMAT);
        // Missing PREFIX_PERSON
        assertParseFailure(parser, RsvpCommand.COMMAND_WORD + " "
                + PREFIX_EVENT_ID + "1 " + PREFIX_RSVP_STATUS + "CC", MESSAGE_INVALID_FORMAT);
        // Missing PREFIX_RSVP_STATUS
        assertParseFailure(parser, RsvpCommand.COMMAND_WORD + " "
                + PREFIX_EVENT_ID + "1 " + PREFIX_PERSON + "1 ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        String invalidInput1 = " " + PREFIX_EVENT_ID + "-1 " + PREFIX_PERSON + "1 " + PREFIX_RSVP_STATUS + "CC";
        String invalidInput2 = " " + PREFIX_EVENT_ID + "1 " + PREFIX_PERSON + "-1 " + PREFIX_RSVP_STATUS + "CC";
        String invalidInput3 = " " + PREFIX_EVENT_ID + "1 " + PREFIX_PERSON + "1 " + PREFIX_RSVP_STATUS + "AA";
        assertParseFailure(parser, invalidInput1, ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, invalidInput2, ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, invalidInput3, RsvpStatus.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        String invalidPreamble = "invalid" + PREFIX_EVENT_ID + "-1 " + PREFIX_PERSON + "1 " + PREFIX_RSVP_STATUS + "CC";
        assertParseFailure(parser, invalidPreamble, String.format(MESSAGE_INVALID_COMMAND_FORMAT, RsvpCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = " " + PREFIX_EVENT_ID + "1 " + PREFIX_PERSON + "1 " + PREFIX_RSVP_STATUS + "CC";
        Index index = Index.fromZeroBased(0);
        RsvpCommand expectedCommand = new RsvpCommand(index, index, RsvpStatus.CC);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
