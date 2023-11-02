package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESC_CAREER_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESC_FSC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_FROM_DATE_CAREER_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_FROM_DATE_FSC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_CAREER_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_FSC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_TO_DATE_CAREER_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_TO_DATE_FSC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_FROM_DATE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_FSC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_FROM_DATE_FSC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_FSC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TO_DATE_FSC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEvents.FSC;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CreateEventCommand;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.FromDate;
import seedu.address.model.event.Name;
import seedu.address.testutil.EventBuilder;

public class CreateEventCommandParserTest {
    private CreateEventCommandParser parser = new CreateEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder(FSC).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_FSC
                + EVENT_DESC_FSC + EVENT_FROM_DATE_FSC + EVENT_TO_DATE_FSC, new CreateEventCommand(expectedEvent));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedEventString = EVENT_NAME_CAREER_FAIR
                + EVENT_DESC_CAREER_FAIR + EVENT_FROM_DATE_CAREER_FAIR + EVENT_TO_DATE_CAREER_FAIR;

        // multiple names
        assertParseFailure(parser, EVENT_NAME_FSC + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_NAME));

        // multiple descriptions
        assertParseFailure(parser, EVENT_DESC_FSC + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_DESC));

        // multiple dates
        assertParseFailure(parser, EVENT_FROM_DATE_FSC + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_FROM));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedEventString + EVENT_DESC_FSC + EVENT_FROM_DATE_FSC + EVENT_NAME_FSC
                        + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_NAME, PREFIX_EVENT_DESC,
                        PREFIX_EVENT_FROM, PREFIX_EVENT_TO));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_EVENT_NAME + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EVENT_DESC + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_DESC));

        // invalid from date
        assertParseFailure(parser, INVALID_EVENT_FROM_DATE + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_FROM));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedEventString + INVALID_EVENT_NAME,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedEventString + INVALID_EVENT_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_DESC));

        // invalid phone
        assertParseFailure(parser, validExpectedEventString + INVALID_EVENT_FROM_DATE,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_FROM));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateEventCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_EVENT_NAME_FSC + EVENT_DESC_FSC + EVENT_FROM_DATE_FSC + EVENT_TO_DATE_FSC,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, EVENT_NAME_FSC + VALID_EVENT_DESCRIPTION_FSC + EVENT_FROM_DATE_FSC
                        + EVENT_TO_DATE_FSC, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, EVENT_NAME_FSC + EVENT_DESC_FSC + VALID_EVENT_FROM_DATE_FSC + EVENT_TO_DATE_FSC,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_EVENT_NAME_FSC + VALID_EVENT_DESCRIPTION_FSC
                        + VALID_EVENT_FROM_DATE_FSC + VALID_EVENT_TO_DATE_FSC, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_EVENT_NAME + EVENT_DESC_FSC
                + EVENT_FROM_DATE_FSC + EVENT_TO_DATE_FSC, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, EVENT_NAME_FSC + INVALID_EVENT_DESC
                + EVENT_FROM_DATE_FSC + EVENT_TO_DATE_FSC, Description.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, EVENT_NAME_FSC + EVENT_DESC_FSC
                + INVALID_EVENT_FROM_DATE + EVENT_TO_DATE_FSC, FromDate.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_EVENT_NAME + EVENT_DESC_FSC + INVALID_EVENT_FROM_DATE
                + EVENT_TO_DATE_FSC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EVENT_NAME_FSC + EVENT_DESC_FSC + EVENT_FROM_DATE_FSC
                + EVENT_TO_DATE_FSC, String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateEventCommand.MESSAGE_USAGE));
    }
}
