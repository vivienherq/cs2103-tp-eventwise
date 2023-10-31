package seedu.address.logic.parser;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemovePersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

/**
 * Parses input arguments and create a new {@code RemovePersonCommand} object
 */
public class RemovePersonCommandParser implements Parser<RemovePersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemovePersonCommand}
     * and returns a {@code RemovePersonCommand} object for execution.
     * @param args arguments for {@code RemovePersonCommand}
     * @return {@code RemovePersonCommand}
     * @throws ParseException if the user input does not conform with the expected format
     */
    @Override
    public RemovePersonCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_ID, PREFIX_PERSON);

        // Preamble must be empty!!
        if (!argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemovePersonCommand.MESSAGE_USAGE));
        }

        // Should only have one prefix for EVENT_ID AND PERSON_ID
        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_ID, PREFIX_PERSON);

        // EVENT_ID and PERSON_ID must be present
        if (!arePrefixesPresent(argumentMultimap, PREFIX_EVENT_ID, PREFIX_PERSON)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemovePersonCommand.MESSAGE_USAGE));
        }

        Index eventIndex = ParserUtil.parseIndex(argumentMultimap.getValue(PREFIX_EVENT_ID).get());
        Index personIndex = ParserUtil.parseIndex(argumentMultimap.getValue(PREFIX_PERSON).get());

        return new RemovePersonCommand(eventIndex, personIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
