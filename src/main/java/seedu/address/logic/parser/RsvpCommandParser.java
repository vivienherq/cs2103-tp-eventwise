package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RSVP_STATUS;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RsvpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.rsvp.RsvpStatus;

/**
 * Parses input arguments and creates a new RsvpCommand object
 */
public class RsvpCommandParser implements Parser<RsvpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RsvpCommand
     * and returns an RsvpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RsvpCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_ID, PREFIX_PERSON, PREFIX_RSVP_STATUS);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_ID, PREFIX_PERSON, PREFIX_RSVP_STATUS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RsvpCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_ID, PREFIX_PERSON, PREFIX_RSVP_STATUS);

        Index eventIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EVENT_ID).get());
        Index personIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON).get());
        RsvpStatus rsvpStatus = ParserUtil.parseRsvpStatus(argMultimap.getValue(PREFIX_RSVP_STATUS).get());

        return new RsvpCommand(eventIndex, personIndex, rsvpStatus);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
