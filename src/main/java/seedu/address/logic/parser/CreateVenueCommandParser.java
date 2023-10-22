package seedu.address.logic.parser;

import seedu.address.logic.commands.CreateEventCommand;
import seedu.address.logic.commands.CreateVenueCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.venue.Capacity;
import seedu.address.model.venue.Name;
import seedu.address.model.venue.Address;
import seedu.address.model.venue.Venue;

import java.util.stream.Stream;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE_NAME;

/**
 * Parses input arguments and creates a new CreateVenueCommand object
 */
public class CreateVenueCommandParser implements Parser<CreateVenueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateVenueCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_VENUE_NAME, PREFIX_VENUE_ADDRESS, PREFIX_VENUE_CAPACITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_VENUE_NAME, PREFIX_VENUE_ADDRESS, PREFIX_VENUE_CAPACITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_VENUE_NAME, PREFIX_VENUE_ADDRESS, PREFIX_VENUE_CAPACITY);
        Name name = ParserUtil.parseVenueName(argMultimap.getValue(PREFIX_VENUE_NAME).get());
        Address address = ParserUtil.parseVenueAddress(argMultimap.getValue(PREFIX_VENUE_ADDRESS).get());
        Capacity capacity = ParserUtil.parseVenueCapacity(argMultimap.getValue(PREFIX_VENUE_CAPACITY).get());

        Venue venue = new Venue(name, address, capacity);

        return new CreateVenueCommand(venue);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
