package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalVenues.COM1;
import static seedu.address.testutil.TypicalVenues.BIZ2;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CreateVenueCommand;
import seedu.address.model.venue.Address;
import seedu.address.model.venue.Capacity;
import seedu.address.model.venue.Name;
import seedu.address.model.venue.Venue;
import seedu.address.testutil.VenueBuilder;

public class CreateVenueCommandParserTest {
    private CreateVenueCommandParser parser = new CreateVenueCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Venue expectedVenue = new VenueBuilder(COM1).build();

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + VENUE_NAME_COM1 + VENUE_ADDRESS_COM1 + VENUE_CAPACITY_COM1,
                new CreateVenueCommand(expectedVenue));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedVenueString = VENUE_NAME_COM1 + VENUE_ADDRESS_COM1 + VENUE_CAPACITY_COM1;

        // multiple names
        assertParseFailure(parser, VENUE_NAME_BIZ2 + validExpectedVenueString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENUE_NAME));

        // multiple addresses
        assertParseFailure(parser, VENUE_ADDRESS_BIZ2 + validExpectedVenueString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENUE_ADDRESS));

        // multiple capacities
        assertParseFailure(parser, VENUE_CAPACITY_BIZ2 + validExpectedVenueString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENUE_CAPACITY));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedVenueString + VENUE_NAME_BIZ2 + VENUE_ADDRESS_BIZ2 + VENUE_CAPACITY_BIZ2
                        + validExpectedVenueString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENUE_NAME,
                        PREFIX_VENUE_ADDRESS, PREFIX_VENUE_CAPACITY));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_VENUE_NAME + validExpectedVenueString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENUE_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_VENUE_ADDRESS + validExpectedVenueString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENUE_ADDRESS));

        // invalid phone
        assertParseFailure(parser, INVALID_VENUE_CAPACITY + validExpectedVenueString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENUE_CAPACITY));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedVenueString + INVALID_VENUE_NAME,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENUE_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedVenueString + INVALID_VENUE_ADDRESS,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENUE_ADDRESS));

        // invalid phone
        assertParseFailure(parser, validExpectedVenueString + INVALID_VENUE_CAPACITY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENUE_CAPACITY));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateVenueCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_VENUE_NAME_COM1 + VENUE_ADDRESS_COM1 + VENUE_CAPACITY_COM1,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, VENUE_NAME_COM1 + VALID_VENUE_ADDRESS_COM1 + VENUE_CAPACITY_COM1,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, VENUE_NAME_COM1 + VENUE_ADDRESS_COM1 + VALID_VENUE_CAPACITY_COM1,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
                VALID_VENUE_NAME_COM1 + VALID_VENUE_ADDRESS_COM1 + VALID_VENUE_CAPACITY_COM1,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_VENUE_NAME + VENUE_ADDRESS_COM1 + VENUE_CAPACITY_COM1,
                Name.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, VENUE_NAME_COM1 + INVALID_VENUE_ADDRESS + VENUE_CAPACITY_COM1,
                Address.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, VENUE_NAME_COM1 + VENUE_ADDRESS_COM1 + INVALID_VENUE_CAPACITY,
                Capacity.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_VENUE_NAME + VENUE_ADDRESS_COM1 + VENUE_CAPACITY_COM1,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + VENUE_NAME_COM1 + VENUE_ADDRESS_COM1 + VENUE_CAPACITY_COM1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateVenueCommand.MESSAGE_USAGE));
    }
}
