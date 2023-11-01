package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditVenueCommand;
import seedu.address.logic.commands.EditVenueCommand.EditVenueDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditVenueCommand object
 */
public class EditVenueCommandParser implements Parser<EditVenueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditVenueCommand
     * and returns an EditVenueCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditVenueCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_VENUE, PREFIX_VENUE_NAME, PREFIX_VENUE_ADDRESS, PREFIX_VENUE_CAPACITY);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_VENUE, PREFIX_VENUE_NAME,
                PREFIX_VENUE_ADDRESS, PREFIX_VENUE_CAPACITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_VENUE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditVenueCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_VENUE).get());
        EditVenueDescriptor editVenueDescriptor = new EditVenueDescriptor();

        if (argMultimap.getValue(PREFIX_VENUE_NAME).isPresent()) {
            editVenueDescriptor.setName(ParserUtil.parseVenueName(argMultimap.getValue(PREFIX_VENUE_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_VENUE_ADDRESS).isPresent()) {
            editVenueDescriptor.setAddress(ParserUtil.parseVenueAddress(
                    argMultimap.getValue(PREFIX_VENUE_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_VENUE_CAPACITY).isPresent()) {
            editVenueDescriptor.setCapacity(ParserUtil.parseVenueCapacity(
                    argMultimap.getValue(PREFIX_VENUE_CAPACITY).get()));
        }

        if (!editVenueDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditVenueCommand.MESSAGE_NOT_EDITED);
        }

        return new EditVenueCommand(index, editVenueDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
