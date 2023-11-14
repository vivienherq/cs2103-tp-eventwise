package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearEventsCommand extends Command {

    public static final String COMMAND_WORD = "clearEvents";
    public static final String MESSAGE_SUCCESS = "Events in address book has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.resetEvents();
        model.resetRsvps();
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        model.setEventToView(null);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
