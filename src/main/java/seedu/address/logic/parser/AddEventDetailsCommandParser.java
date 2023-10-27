package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddEventDetailsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and create a new {@code AddEventDetailsCommand} object
 */
public class AddEventDetailsCommandParser implements Parser<AddEventDetailsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddEventDetailsCommand}
     * and returns a {@code ViewEventCommand} object for execution.
     * @param args arguments for {@code ViewEventCommand}
     * @return {@code ViewEventCommand}
     * @throws ParseException if the user input does not conform with the expected format
     */
    @Override
    public AddEventDetailsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_ID, PREFIX_PERSON, PREFIX_VENUE, PREFIX_VENDOR);

        // Should only have one prefix for EVENT_ID AND VENUE
        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_ID, PREFIX_VENUE);

        // Minimally event prefix has to be present.
        if (!arePrefixesPresent(argumentMultimap, PREFIX_EVENT_ID)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddEventDetailsCommand.MESSAGE_USAGE));
        }

        // Event ID to add people
        Index index = ParserUtil.parseIndex(argumentMultimap.getValue(PREFIX_EVENT_ID).get());

        // Get a list of prefix: person/
        Set<Index> personIndexes = ParserUtil.parseIndexes(argumentMultimap.getAllValues(PREFIX_PERSON));

        // Venue ID to set venue
        if (argumentMultimap.getValue(PREFIX_VENUE).isEmpty()) {
            return new AddEventDetailsCommand(index, personIndexes, null);
        }

        Index venueIndex = ParserUtil.parseIndex(argumentMultimap.getValue(PREFIX_VENUE).get());
        return new AddEventDetailsCommand(index, personIndexes, venueIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
