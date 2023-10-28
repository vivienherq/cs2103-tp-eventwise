package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VENUE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddEventDetailsCommand;

public class AddEventDetailsCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventDetailsCommand.MESSAGE_USAGE);

    private final AddEventDetailsCommandParser parser = new AddEventDetailsCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = String.format(" %s%d %s%d %s%d",
                PREFIX_EVENT_ID, INDEX_THIRD_EVENT.getOneBased(),
                PREFIX_PERSON, INDEX_SECOND_PERSON.getOneBased(),
                PREFIX_VENUE, INDEX_FIRST_VENUE.getOneBased());
        System.out.println(userInput);
        HashSet<Index> expectedPersons = new HashSet<>();
        expectedPersons.add(INDEX_SECOND_PERSON);
        AddEventDetailsCommand expectedCommand =
                new AddEventDetailsCommand(INDEX_THIRD_EVENT, expectedPersons, INDEX_FIRST_VENUE);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
