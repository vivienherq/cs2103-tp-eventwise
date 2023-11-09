package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.rsvp.RsvpContainsEventPredicate;

/**
 * View details for a specified event in EventWise
 */
public class ViewEventCommand extends Command {

    public static final String COMMAND_WORD = "viewEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": View the details of an event specified"
            + "by the index number used in the last event listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_EVENT_ID + "1 ";

    public static final String MESSAGE_SUCCESS = "Showing Event %1$d: %2$s";
    private final Index index;

    /**
     * @param index of the event in the event list to view event details
     */
    public ViewEventCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> eventList = model.getAddressBook().getEventList();

        if (index.getZeroBased() >= eventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        // Use model to retrieve event
        Event eventToView = eventList.get(index.getZeroBased());

        model.updateFilteredEventRsvpList(new RsvpContainsEventPredicate(eventToView));
        // Set eventToView in the model
        model.setEventToView(eventToView);

        // Display success message
        return new CommandResult(String.format(MESSAGE_SUCCESS, index.getOneBased(), eventToView.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ViewEventCommand)) {
            return false;
        }

        ViewEventCommand otherCommand = (ViewEventCommand) other;
        return index.equals(otherCommand.index);
    }

}
