package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddEventDetailsCommand;
import seedu.address.logic.commands.ViewEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

import javax.swing.text.html.Option;

/**
 * Parses input arguments and create a new {@code AddEventDetailsCommand} object
 */
public class AddEventDetailsCommandParser implements Parser<AddEventDetailsCommand>{

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
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_ID, PREFIX_PERSON);

        // Should only have one prefix for EVENT_ID
        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_ID);

        // Minimally event prefix has to be present.
        if (!arePrefixesPresent(argumentMultimap, PREFIX_EVENT_ID)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewEventCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argumentMultimap.getValue(PREFIX_EVENT_ID).get());
        // Get a list of prefix: person/

        argumentMultimap.getAllValues(PREFIX_PERSON);

        return new AddEventDetailsCommand(index);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses {@code Collection<String> ids} into a {@code Set<Index>} if {@code ids} is non-empty.
     * If {@code ids} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Index>} containing zero tags.
     */
    private Optional<Set<Index>> parseIndexes(Collection<String> ids) throws ParseException {
        assert ids != null;

        if (ids.isEmpty()) {
            return Optional.empty();
        }

        Collection<String> indexSet = ids.size() == 1 && ids.contains("") ? Collections.emptySet() : ids;

        // GOAL: Get a collection of INDEXES
        return Optional.of(ParserUtil.parseIndexes(indexSet));
    }
}
