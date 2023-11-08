package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.*;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditVenueCommand;
import seedu.address.logic.commands.EditVenueCommand.EditVenueDescriptor;
import seedu.address.model.venue.Address;
import seedu.address.model.venue.Capacity;
import seedu.address.model.venue.Name;
import seedu.address.testutil.EditVenueDescriptorBuilder;

public class EditVenueCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditVenueCommand.MESSAGE_USAGE);

    private EditVenueCommandParser parser = new EditVenueCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_VENUE_ADDRESS_BIZ2, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, EditVenueCommand.COMMAND_WORD + " "
                + PREFIX_VENUE + "1", EditVenueCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, EditVenueCommand.COMMAND_WORD + " "
                + PREFIX_VENUE + "-5" + VENUE_NAME_BIZ2, ParserUtil.MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, EditVenueCommand.COMMAND_WORD + " "
                + PREFIX_VENUE + "0" + VENUE_NAME_BIZ2, ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, EditVenueCommand.COMMAND_WORD + " "
                + PREFIX_VENUE + "1 some random string", ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, EditVenueCommand.COMMAND_WORD + " "
                + PREFIX_VENUE + "1 i/ string", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, EditVenueCommand.COMMAND_WORD + " "
                + PREFIX_VENUE + "1" + INVALID_VENUE_NAME, Name.MESSAGE_CONSTRAINTS); // invalid name

        assertParseFailure(parser, EditVenueCommand.COMMAND_WORD + " "
                + PREFIX_VENUE + "1" + INVALID_VENUE_ADDRESS, Address.MESSAGE_CONSTRAINTS); // invalid phone

        assertParseFailure(parser, EditVenueCommand.COMMAND_WORD + " "
                + PREFIX_VENUE + "1" + INVALID_VENUE_CAPACITY, Capacity.MESSAGE_CONSTRAINTS); // invalid email

        // invalid address followed by valid capacity
        assertParseFailure(parser, EditVenueCommand.COMMAND_WORD + " "
                + PREFIX_VENUE + "1" + INVALID_VENUE_ADDRESS + VENUE_CAPACITY_BIZ2, Address.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, EditVenueCommand.COMMAND_WORD + " "
                        + PREFIX_VENUE + "1" + INVALID_VENUE_NAME + INVALID_VENUE_ADDRESS + INVALID_VENUE_CAPACITY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_VENUE;
        String userInput = EditVenueCommand.COMMAND_WORD + " " + PREFIX_VENUE
                + targetIndex.getOneBased() + VENUE_NAME_BIZ2
                + VENUE_ADDRESS_BIZ2 + VENUE_CAPACITY_BIZ2;

        EditVenueDescriptor descriptor = new EditVenueDescriptorBuilder().withName(VALID_VENUE_NAME_BIZ2)
                .withAddress(VALID_VENUE_ADDRESS_BIZ2).withCapacity(VALID_VENUE_CAPACITY_BIZ2)
                .build();
        EditVenueCommand expectedCommand = new EditVenueCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_VENUE;
        String userInput = EditVenueCommand.COMMAND_WORD + " " + PREFIX_VENUE
                + targetIndex.getOneBased() + VENUE_ADDRESS_BIZ2 + VENUE_CAPACITY_BIZ2;

        EditVenueDescriptor descriptor = new EditVenueDescriptorBuilder().withAddress(VALID_VENUE_ADDRESS_BIZ2)
                .withCapacity(VALID_VENUE_CAPACITY_BIZ2).build();
        EditVenueCommand expectedCommand = new EditVenueCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_VENUE;
        String userInput = EditVenueCommand.COMMAND_WORD + " " + PREFIX_VENUE
                + targetIndex.getOneBased() + VENUE_NAME_BIZ2;
        EditVenueDescriptor descriptor = new EditVenueDescriptorBuilder().withName(VALID_VENUE_NAME_BIZ2).build();
        EditVenueCommand expectedCommand = new EditVenueCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = EditVenueCommand.COMMAND_WORD + " " + PREFIX_VENUE
                + targetIndex.getOneBased() + VENUE_ADDRESS_BIZ2;
        descriptor = new EditVenueDescriptorBuilder().withAddress(VALID_VENUE_ADDRESS_BIZ2).build();
        expectedCommand = new EditVenueCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = EditVenueCommand.COMMAND_WORD + " " + PREFIX_VENUE
                + targetIndex.getOneBased() + VENUE_CAPACITY_BIZ2;
        descriptor = new EditVenueDescriptorBuilder().withCapacity(VALID_VENUE_CAPACITY_BIZ2).build();
        expectedCommand = new EditVenueCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // invalid followed by valid
        Index targetIndex = INDEX_FIRST_VENUE;
        String userInput = EditVenueCommand.COMMAND_WORD + " " + PREFIX_VENUE
                + targetIndex.getOneBased() + INVALID_VENUE_ADDRESS + VENUE_ADDRESS_COM1;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENUE_ADDRESS));

        // valid followed by invalid
        userInput = EditVenueCommand.COMMAND_WORD + " " + PREFIX_VENUE
                + targetIndex.getOneBased() + VENUE_ADDRESS_COM1 + INVALID_VENUE_ADDRESS;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENUE_ADDRESS));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + VENUE_ADDRESS_BIZ2 + VENUE_CAPACITY_BIZ2
                + VENUE_ADDRESS_BIZ2 + VENUE_CAPACITY_BIZ2
                + VENUE_ADDRESS_COM1 + VENUE_CAPACITY_COM1;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENUE_ADDRESS, PREFIX_VENUE_CAPACITY));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_VENUE_ADDRESS + INVALID_VENUE_CAPACITY
                + INVALID_VENUE_ADDRESS + INVALID_VENUE_CAPACITY;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENUE_ADDRESS, PREFIX_VENUE_CAPACITY));
    }
}
