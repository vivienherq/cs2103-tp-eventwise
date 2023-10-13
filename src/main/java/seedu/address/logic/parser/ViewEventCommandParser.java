package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ViewEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;

/**
 * Parses input arguments and create a new {@code ViewEventCommand} object
 */
public class ViewEventCommandParser implements Parser<ViewEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ViewEventCommand}
     * and returns a {@code ViewEventCommand} object for execution.
     * @param args arguments for {@code ViewEventCommand}
     * @return {@code ViewEventCommand}
     * @throws ParseException if the user input does not conform with the expected format
     */
    public ViewEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_ID);
        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_ID);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_EVENT_ID)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewEventCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argumentMultimap.getValue(PREFIX_EVENT_ID).get());

        return new ViewEventCommand(index);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
