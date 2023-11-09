package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveVendorCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and create a new {@code RemoveVendorCommand} object
 */
public class RemoveVendorCommandParser implements Parser<RemoveVendorCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemoveVendorCommand}
     * and returns a {@code RemoveVendorCommand} object for execution.
     * @param args arguments for {@code RemoveVendorCommand}
     * @return {@code RemoveVendorCommand}
     * @throws ParseException if the user input does not conform with the expected format
     */
    @Override
    public RemoveVendorCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_ID, PREFIX_VENDOR);

        // Preamble must be empty!!
        if (!argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveVendorCommand.MESSAGE_USAGE));
        }

        // Should only have one prefix for EVENT_INDEX AND VENDOR_INDEX
        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_ID, PREFIX_VENDOR);

        // EVENT_ID and PERSON_ID must be present
        if (!arePrefixesPresent(argumentMultimap, PREFIX_EVENT_ID, PREFIX_VENDOR)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveVendorCommand.MESSAGE_USAGE));
        }

        Index eventIndex = ParserUtil.parseIndex(argumentMultimap.getValue(PREFIX_EVENT_ID).get());
        Index vendorIndex = ParserUtil.parseIndex(argumentMultimap.getValue(PREFIX_VENDOR).get());

        return new RemoveVendorCommand(eventIndex, vendorIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
