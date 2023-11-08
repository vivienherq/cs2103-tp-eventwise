package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VENUE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteVenueCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteVenueCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteVenueCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteVenueCommandParserTest {

    private DeleteVenueCommandParser parser = new DeleteVenueCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteVendorCommand() {
        String userInput = String.format(" %s%d", PREFIX_VENUE, INDEX_FIRST_VENUE.getOneBased());
        assertParseSuccess(parser, userInput, new DeleteVenueCommand(INDEX_FIRST_VENUE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteVenueCommand.MESSAGE_USAGE));
    }
}
