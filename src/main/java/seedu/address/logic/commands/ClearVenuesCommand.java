package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VENUES;

import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearVenuesCommand extends Command {

    public static final String COMMAND_WORD = "clearVenues";
    public static final String MESSAGE_SUCCESS = "Venues in address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.resetVenues();
        model.resetAllEventVenues();
        model.updateFilteredVenueList(PREDICATE_SHOW_ALL_VENUES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
