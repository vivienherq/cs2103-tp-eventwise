package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * View a list of all events in EventWise
 */
public class ViewEventsCommand extends Command {

    public static final String COMMAND_WORD = "viewEvents";

    public static final String MESSAGE_SUCCESS = "List of Events:\n%1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        ObservableList<Event> eventList = model.getFilteredEventsList();
        String events = "";
        for (int i = 0; i < eventList.size(); i++) {
            Event event = eventList.get(i);
            events += String.format("%d: %s; Description: %s; Date: %s\n", i + 1,
                    event.getName(), event.getDescription(), event.getFromDate());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, events));
    }
}
