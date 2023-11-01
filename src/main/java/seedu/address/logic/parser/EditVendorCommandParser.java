package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR_PHONE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditVendorCommand;
import seedu.address.logic.commands.EditVendorCommand.EditVendorDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditVendorCommandParser implements Parser<EditVendorCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditVendorCommand
     * and returns an EditEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditVendorCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_VENDOR, PREFIX_VENDOR_NAME, PREFIX_VENDOR_PHONE, PREFIX_VENDOR_EMAIL);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_VENDOR, PREFIX_VENDOR_NAME,
                PREFIX_VENDOR_PHONE, PREFIX_VENDOR_EMAIL);

        if (!arePrefixesPresent(argMultimap, PREFIX_VENDOR)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditVendorCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_VENDOR).get());
        EditVendorDescriptor editVendorDescriptor = new EditVendorDescriptor();

        if (argMultimap.getValue(PREFIX_VENDOR_NAME).isPresent()) {
            editVendorDescriptor.setName(ParserUtil.parseVendorName(argMultimap.getValue(PREFIX_VENDOR_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_VENDOR_PHONE).isPresent()) {
            editVendorDescriptor.setPhone(ParserUtil.parseVendorPhone(
                    argMultimap.getValue(PREFIX_VENDOR_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_VENDOR_EMAIL).isPresent()) {
            editVendorDescriptor.setEmail(ParserUtil.parseVendorEmail(argMultimap.getValue(PREFIX_VENDOR_EMAIL).get()));
        }

        if (!editVendorDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditVendorCommand.MESSAGE_NOT_EDITED);
        }

        return new EditVendorCommand(index, editVendorDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
