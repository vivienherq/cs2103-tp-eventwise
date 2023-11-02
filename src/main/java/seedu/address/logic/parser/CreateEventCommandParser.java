package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_EVENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NOTE;

import java.util.stream.Stream;

import seedu.address.logic.commands.CreateEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;
import seedu.address.model.event.Note;
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class CreateEventCommandParser implements Parser<CreateEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME, PREFIX_EVENT_DESC,
                        PREFIX_EVENT_DATE, PREFIX_EVENT_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_NAME, PREFIX_EVENT_DESC, PREFIX_EVENT_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_NAME, PREFIX_EVENT_DESC,
                PREFIX_EVENT_DATE, PREFIX_EVENT_NOTE);
        Name name = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_EVENT_DESC).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_EVENT_DATE).get());
        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_EVENT_NOTE).orElse(null));

        if (!date.isNotPast()) {
            throw new ParseException(String.format(MESSAGE_INVALID_EVENT_DATE,
                    CreateEventCommand.MESSAGE_INVALID_DATE));
        }

        Event event = new Event(name, description, date, note);

        return new CreateEventCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
