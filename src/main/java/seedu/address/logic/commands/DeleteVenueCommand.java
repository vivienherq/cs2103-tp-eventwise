package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.venue.Venue;

/**
 * Deletes a venue identified using its displayed index from EventWise.
 */
public class DeleteVenueCommand extends Command {

    public static final String COMMAND_WORD = "deleteVenue";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the venue identified by the index number used in the displayed venue list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_VENUE + "1";

    public static final String MESSAGE_DELETE_VENUE_SUCCESS = "Deleted Venue %1$d: %2$s";

    private final Index targetIndex;

    public DeleteVenueCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Venue> lastShownList = model.getFilteredVenuesList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VENUE_DISPLAYED_INDEX);
        }

        Venue venueToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteVenue(venueToDelete);
        String venueDetails = String.format("%s; Address: %s; Capacity: %s\n",
                venueToDelete.getName(), venueToDelete.getAddress(), venueToDelete.getCapacity());

        // Check if event contains venueToDelete, if true, set event venue to null
        for (Event event : model.getAddressBook().getEventList()) {
            if (event.getVenue() == null) {
                continue;
            }

            if (event.getVenue().isSameVenue(venueToDelete)) {
                Event updatedEvent = new Event(event.getName(), event.getDescription(),
                        event.getFromDate(), event.getToDate(), event.getNote(), event.getPersons(),
                        event.getVendors(), null);
                model.setEvent(event, updatedEvent);
            }
        }

        // Check if the current event that is being shown in the event details is affected
        Event eventToView = model.getEventToView();
        boolean isNotNull = eventToView != null && eventToView.getVenue() != null;
        if (isNotNull && eventToView.getVenue().isSameVenue(venueToDelete)) {
            Event currentlyShownEvent = model.getEventToView();
            Event updatedEvent = new Event(currentlyShownEvent.getName(), currentlyShownEvent.getDescription(),
                    currentlyShownEvent.getFromDate(), currentlyShownEvent.getToDate(), currentlyShownEvent.getNote(),
                    currentlyShownEvent.getPersons(), currentlyShownEvent.getVendors(), null);
            model.setEventToView(updatedEvent);
        }

        model.updateFilteredVenueList(Model.PREDICATE_SHOW_ALL_VENUES);

        return new CommandResult(String.format(MESSAGE_DELETE_VENUE_SUCCESS, targetIndex.getOneBased(), venueDetails));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteVenueCommand)) {
            return false;
        }

        DeleteVenueCommand otherDeleteCommand = (DeleteVenueCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
