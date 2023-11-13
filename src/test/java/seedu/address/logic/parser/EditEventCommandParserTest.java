package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESC_CAREER_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESC_FSC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_FROM_DATE_CAREER_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_FROM_DATE_FSC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ID_CAREER_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_CAREER_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_FSC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_TO_DATE_CAREER_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_FROM_DATE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_TO_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_CAREER_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_FSC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_FROM_DATE_CAREER_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_FROM_DATE_FSC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_CAREER_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_FSC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TO_DATE_CAREER_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TO_DATE_FSC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.model.event.Description;
import seedu.address.model.event.FromDate;
import seedu.address.model.event.Name;
import seedu.address.model.event.ToDate;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditEventCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

    private EditEventCommandParser parser = new EditEventCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_EVENT_NAME_FSC, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, EditEventCommand.COMMAND_WORD + " "
                + PREFIX_EVENT_ID + "1", EditEventCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidID_failure() {
        // negative index
        assertParseFailure(parser, EditEventCommand.COMMAND_WORD + " "
                + PREFIX_EVENT_ID + "-5" + VALID_EVENT_NAME_FSC, ParserUtil.MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, EditEventCommand.COMMAND_WORD + " "
                + PREFIX_EVENT_ID + "0" + VALID_EVENT_NAME_FSC, ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, EditEventCommand.COMMAND_WORD + " "
                + PREFIX_EVENT_ID + "1 some random string", ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, EditEventCommand.COMMAND_WORD + " "
                + PREFIX_EVENT_ID + "1 i/ string", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, EVENT_ID_CAREER_FAIR
                + INVALID_EVENT_NAME, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, EVENT_ID_CAREER_FAIR
                + INVALID_EVENT_DESC, Description.MESSAGE_CONSTRAINTS); // invalid description
        assertParseFailure(parser, EVENT_ID_CAREER_FAIR
                + INVALID_EVENT_FROM_DATE, FromDate.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, EVENT_ID_CAREER_FAIR
                + INVALID_EVENT_TO_DATE, ToDate.MESSAGE_CONSTRAINTS); // invalid date

        // invalid description followed by valid name
        assertParseFailure(parser, EVENT_ID_CAREER_FAIR
                + INVALID_EVENT_DESC + EVENT_NAME_FSC, Description.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, EVENT_ID_CAREER_FAIR + INVALID_EVENT_NAME
                + INVALID_EVENT_DESC + VALID_EVENT_FROM_DATE_FSC
                + VALID_EVENT_TO_DATE_FSC, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = EditEventCommand.COMMAND_WORD + " " + PREFIX_EVENT_ID
                + targetIndex.getOneBased() + EVENT_NAME_CAREER_FAIR + EVENT_DESC_FSC
                + EVENT_FROM_DATE_CAREER_FAIR + EVENT_TO_DATE_CAREER_FAIR;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_EVENT_NAME_CAREER_FAIR)
                .withDescription(VALID_EVENT_DESCRIPTION_FSC).withFromDate(VALID_EVENT_FROM_DATE_CAREER_FAIR)
                .withToDate(VALID_EVENT_TO_DATE_CAREER_FAIR).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_EVENT;

        String userInput = EditEventCommand.COMMAND_WORD + " " + PREFIX_EVENT_ID
                + targetIndex.getOneBased() + EVENT_NAME_CAREER_FAIR + EVENT_DESC_FSC;



        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_EVENT_NAME_CAREER_FAIR)
                .withDescription(VALID_EVENT_DESCRIPTION_FSC).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_EVENT;
        String userInput = EditEventCommand.COMMAND_WORD + " " + PREFIX_EVENT_ID
                + targetIndex.getOneBased() + EVENT_NAME_CAREER_FAIR;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withName(VALID_EVENT_NAME_CAREER_FAIR).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = EditEventCommand.COMMAND_WORD + " " + PREFIX_EVENT_ID
                + targetIndex.getOneBased() + EVENT_DESC_CAREER_FAIR;
        descriptor = new EditEventDescriptorBuilder().withDescription(VALID_EVENT_DESCRIPTION_CAREER_FAIR).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = EditEventCommand.COMMAND_WORD + " " + PREFIX_EVENT_ID
                + targetIndex.getOneBased() + EVENT_FROM_DATE_CAREER_FAIR;
        descriptor = new EditEventDescriptorBuilder().withFromDate(VALID_EVENT_FROM_DATE_CAREER_FAIR).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = EditEventCommand.COMMAND_WORD + " " + PREFIX_EVENT_ID
                + targetIndex.getOneBased() + INVALID_EVENT_DESC + EVENT_DESC_FSC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_DESC));

        // invalid followed by valid
        userInput = EditEventCommand.COMMAND_WORD + " " + PREFIX_EVENT_ID
                + targetIndex.getOneBased() + EVENT_DESC_FSC + INVALID_EVENT_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_DESC));

        // multiple valid fields repeated
        userInput = EditEventCommand.COMMAND_WORD + " " + PREFIX_EVENT_ID + targetIndex.getOneBased()
                + EVENT_DESC_FSC + EVENT_NAME_FSC + EVENT_FROM_DATE_FSC
                + EVENT_DESC_FSC + EVENT_NAME_FSC + EVENT_FROM_DATE_FSC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_DESC, PREFIX_EVENT_NAME, PREFIX_EVENT_FROM));

        // multiple invalid values
        userInput = EditEventCommand.COMMAND_WORD + " " + targetIndex.getOneBased()
                + INVALID_EVENT_DESC + INVALID_EVENT_NAME + INVALID_EVENT_FROM_DATE
                + INVALID_EVENT_DESC + INVALID_EVENT_NAME + INVALID_EVENT_FROM_DATE;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_DESC, PREFIX_EVENT_NAME, PREFIX_EVENT_FROM));
    }
}
