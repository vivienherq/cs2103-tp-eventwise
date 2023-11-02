package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TO;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditEventCommandParser implements Parser<EditEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditEventCommand
     * and returns an EditEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_EVENT_ID, PREFIX_EVENT_NAME, PREFIX_EVENT_DESC,
                PREFIX_EVENT_FROM, PREFIX_EVENT_TO, PREFIX_EVENT_NOTE);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_ID, PREFIX_EVENT_NAME,
                PREFIX_EVENT_DESC, PREFIX_EVENT_FROM, PREFIX_EVENT_TO, PREFIX_EVENT_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_ID)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditEventCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EVENT_ID).get());
        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();

        if (argMultimap.getValue(PREFIX_EVENT_NAME).isPresent()) {
            editEventDescriptor.setName(ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_DESC).isPresent()) {
            editEventDescriptor.setDescription(ParserUtil.parseDescription(
                    argMultimap.getValue(PREFIX_EVENT_DESC).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_FROM).isPresent()) {
            editEventDescriptor.setFromDate(ParserUtil.parseFromDate(argMultimap.getValue(PREFIX_EVENT_FROM).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_TO).isPresent()) {
            editEventDescriptor.setToDate(ParserUtil.parseToDate(argMultimap.getValue(PREFIX_EVENT_TO).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_NOTE).isPresent()) {
            editEventDescriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_EVENT_NOTE).get()));
        }

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEventCommand(index, editEventDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
