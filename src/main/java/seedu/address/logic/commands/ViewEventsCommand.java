package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class ViewEventsCommand extends Command {

    public static final String COMMAND_WORD = "viewEvents";

    public static final String MESSAGE_SUCCESS = "Listed all events";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Event> eventList = model.getFilteredEventsList();
        String events = "List of Events:";
        for (int i = 0; i < eventList.size(); i++) {
            Event event = eventList.get(i);
            events += String.format("%d: %s", i + 1, event.toString());
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
