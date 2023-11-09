package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteVenueCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and create a new {@code DeleteVenueCommand} object
 */
public class DeleteVenueCommandParser implements Parser<DeleteVenueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteVenueCommand}
     * and returns a {@code DeleteVenueCommand} object for execution.
     *
     * @param args arguments for {@code DeleteVenueCommand}
     * @return {@code DeleteVenueCommand}
     * @throws ParseException if the user input does not conform with the expected format
     */
    @Override
    public DeleteVenueCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_VENUE);

        if (!argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteVenueCommand.MESSAGE_USAGE));
        }

        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_VENUE);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_VENUE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteVenueCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argumentMultimap.getValue(PREFIX_VENUE).get());

        return new DeleteVenueCommand(index);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
