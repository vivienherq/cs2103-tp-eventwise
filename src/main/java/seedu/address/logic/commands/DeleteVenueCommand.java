package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.venue.Venue;

/**
 * Deletes a venue identified using its displayed index from EventWise.
 */
public class DeleteVenueCommand extends Command {

    public static final String COMMAND_WORD = "deleteVenue";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the venue identified by the index number used in the displayed venue list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

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
