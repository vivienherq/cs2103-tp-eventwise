package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalVendors.FOOD;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CreateVendorCommand;
import seedu.address.model.vendor.Email;
import seedu.address.model.vendor.Name;
import seedu.address.model.vendor.Phone;
import seedu.address.model.vendor.Vendor;
import seedu.address.testutil.VendorBuilder;

public class CreateVendorCommandParserTest {
    private CreateVendorCommandParser parser = new CreateVendorCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Vendor expectedVendor = new VendorBuilder(FOOD).build();

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + VENDOR_NAME_FOOD + VENDOR_PHONE_FOOD + VENDOR_EMAIL_FOOD,
                new CreateVendorCommand(expectedVendor));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedVendorString = VENDOR_NAME_FOOD + VENDOR_PHONE_FOOD + VENDOR_EMAIL_FOOD;

        // multiple names
        assertParseFailure(parser, VENDOR_NAME_DRINKS + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENDOR_NAME));

        // multiple phones
        assertParseFailure(parser, VENDOR_PHONE_DRINKS + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENDOR_PHONE));

        // multiple emails
        assertParseFailure(parser, VENDOR_EMAIL_DRINKS + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENDOR_EMAIL));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedVendorString + VENDOR_NAME_DRINKS + VENDOR_PHONE_DRINKS + VENDOR_EMAIL_DRINKS
                        + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENDOR_NAME,
                        PREFIX_VENDOR_PHONE, PREFIX_VENDOR_EMAIL));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENDOR_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENDOR_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENDOR_PHONE));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedVendorString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENDOR_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedVendorString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENDOR_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedVendorString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENDOR_PHONE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateVendorCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_VENDOR_NAME_FOOD + VENDOR_PHONE_FOOD + VENDOR_EMAIL_FOOD,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, VENDOR_NAME_FOOD + VALID_VENDOR_PHONE_FOOD + VENDOR_EMAIL_FOOD,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, VENDOR_NAME_FOOD + VENDOR_PHONE_FOOD + VALID_VENDOR_EMAIL_FOOD,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_VENDOR_NAME_FOOD + VALID_VENDOR_PHONE_FOOD + VALID_VENDOR_EMAIL_FOOD,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + VENDOR_PHONE_FOOD + VENDOR_EMAIL_FOOD,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, VENDOR_NAME_FOOD + INVALID_PHONE_DESC + VENDOR_EMAIL_FOOD,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, VENDOR_NAME_FOOD + VENDOR_PHONE_FOOD + INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + VENDOR_PHONE_FOOD + INVALID_EMAIL_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + VENDOR_NAME_FOOD + VENDOR_PHONE_FOOD + VENDOR_EMAIL_FOOD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateVendorCommand.MESSAGE_USAGE));
    }
}
