package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENDOR_EMAIL_DRINKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENDOR_NAME_DRINKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENDOR_PHONE_DRINKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENDOR_PHONE_FOOD;
import static seedu.address.logic.commands.CommandTestUtil.VENDOR_EMAIL_DRINKS;
import static seedu.address.logic.commands.CommandTestUtil.VENDOR_EMAIL_FOOD;
import static seedu.address.logic.commands.CommandTestUtil.VENDOR_NAME_DRINKS;
import static seedu.address.logic.commands.CommandTestUtil.VENDOR_PHONE_DRINKS;
import static seedu.address.logic.commands.CommandTestUtil.VENDOR_PHONE_FOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VENDOR;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_VENDOR;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_VENDOR;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditVendorCommand;
import seedu.address.logic.commands.EditVendorCommand.EditVendorDescriptor;
import seedu.address.model.vendor.Email;
import seedu.address.model.vendor.Name;
import seedu.address.model.vendor.Phone;
import seedu.address.testutil.EditVendorDescriptorBuilder;

public class EditVendorCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditVendorCommand.MESSAGE_USAGE);

    private EditVendorCommandParser parser = new EditVendorCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_VENDOR_NAME_DRINKS, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, EditVendorCommand.COMMAND_WORD + " "
                + PREFIX_VENDOR + "1", EditVendorCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, EditVendorCommand.COMMAND_WORD + " "
                + PREFIX_VENDOR + "-5" + VENDOR_NAME_DRINKS, ParserUtil.MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, EditVendorCommand.COMMAND_WORD + " "
                + PREFIX_VENDOR + "0" + VENDOR_NAME_DRINKS, ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, EditVendorCommand.COMMAND_WORD + " "
                + PREFIX_VENDOR + "1 some random string", ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, EditVendorCommand.COMMAND_WORD + " "
                + PREFIX_VENDOR + "1 i/ string", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, EditVendorCommand.COMMAND_WORD + " "
                + PREFIX_VENDOR + "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name

        assertParseFailure(parser, EditVendorCommand.COMMAND_WORD + " "
                + PREFIX_VENDOR + "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone

        assertParseFailure(parser, EditVendorCommand.COMMAND_WORD + " "
                + PREFIX_VENDOR + "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email

        // invalid phone followed by valid email
        assertParseFailure(parser, EditVendorCommand.COMMAND_WORD + " "
                + PREFIX_VENDOR + "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, EditVendorCommand.COMMAND_WORD + " "
                        + PREFIX_VENDOR + "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_VENDOR;
        String userInput = EditVendorCommand.COMMAND_WORD + " " + PREFIX_VENDOR
                + targetIndex.getOneBased() + VENDOR_PHONE_DRINKS
                + VENDOR_EMAIL_DRINKS + VENDOR_NAME_DRINKS;

        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withName(VALID_VENDOR_NAME_DRINKS)
                .withPhone(VALID_VENDOR_PHONE_DRINKS).withEmail(VALID_VENDOR_EMAIL_DRINKS)
                .build();
        EditVendorCommand expectedCommand = new EditVendorCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_VENDOR;
        String userInput = EditVendorCommand.COMMAND_WORD + " " + PREFIX_VENDOR
                + targetIndex.getOneBased() + VENDOR_PHONE_FOOD + VENDOR_EMAIL_DRINKS;

        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withPhone(VALID_VENDOR_PHONE_FOOD)
                .withEmail(VALID_VENDOR_EMAIL_DRINKS).build();
        EditVendorCommand expectedCommand = new EditVendorCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_VENDOR;
        String userInput = EditVendorCommand.COMMAND_WORD + " " + PREFIX_VENDOR
                + targetIndex.getOneBased() + VENDOR_NAME_DRINKS;
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withName(VALID_VENDOR_NAME_DRINKS).build();
        EditVendorCommand expectedCommand = new EditVendorCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = EditVendorCommand.COMMAND_WORD + " " + PREFIX_VENDOR
                + targetIndex.getOneBased() + VENDOR_PHONE_DRINKS;
        descriptor = new EditVendorDescriptorBuilder().withPhone(VALID_VENDOR_PHONE_DRINKS).build();
        expectedCommand = new EditVendorCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = EditVendorCommand.COMMAND_WORD + " " + PREFIX_VENDOR
                + targetIndex.getOneBased() + VENDOR_EMAIL_DRINKS;
        descriptor = new EditVendorDescriptorBuilder().withEmail(VALID_VENDOR_EMAIL_DRINKS).build();
        expectedCommand = new EditVendorCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_VENDOR;
        String userInput = EditVendorCommand.COMMAND_WORD + " " + PREFIX_VENDOR
                + targetIndex.getOneBased() + INVALID_PHONE_DESC + VENDOR_PHONE_FOOD;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENDOR_PHONE));

        // invalid followed by valid
        userInput = EditVendorCommand.COMMAND_WORD + " " + PREFIX_VENDOR
                + targetIndex.getOneBased() + VENDOR_PHONE_FOOD + INVALID_PHONE_DESC;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENDOR_PHONE));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + VENDOR_PHONE_DRINKS + VENDOR_EMAIL_DRINKS
                + VENDOR_PHONE_DRINKS + VENDOR_EMAIL_DRINKS
                + VENDOR_PHONE_FOOD + VENDOR_EMAIL_FOOD;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENDOR_PHONE, PREFIX_VENDOR_EMAIL));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_EMAIL_DESC;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENDOR_PHONE, PREFIX_VENDOR_EMAIL));
    }
}
