package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VENUES;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.venue.Venue;

/**
 * View a list of all events in EventWise
 */
public class ViewVenuesCommand extends Command {

    public static final String COMMAND_WORD = "viewVenues";

    public static final String MESSAGE_SUCCESS = "List of Venues:\n%1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredVenueList(PREDICATE_SHOW_ALL_VENUES);
        ObservableList<Venue> venueList = model.getFilteredVenuesList();
        String venues = "";
        for (int i = 0; i < venueList.size(); i++) {
            Venue venue = venueList.get(i);
            venues += String.format("%d: %s; Address: %s; Capacity: %s\n", i + 1,
                    venue.getName(), venue.getAddress(), venue.getCapacity());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, venues));
    }
}
