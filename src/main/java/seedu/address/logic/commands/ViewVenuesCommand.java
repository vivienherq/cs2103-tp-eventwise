package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VENUES;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * View a list of all venues in EventWise
 */
public class ViewVenuesCommand extends Command {

    public static final String COMMAND_WORD = "viewVenues";

    public static final String MESSAGE_SUCCESS = "Listed all venue(s)";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredVenueList(PREDICATE_SHOW_ALL_VENUES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
