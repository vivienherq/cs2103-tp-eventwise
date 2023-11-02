package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.vendor.Vendor;

/**
 * Deletes a vendor identified using its displayed index from the address book.
 */
public class DeleteVendorCommand extends Command {

    public static final String COMMAND_WORD = "deleteVendor";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the vendor identified by the index number used in the displayed vendor list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " vdr/1";

    public static final String MESSAGE_DELETE_VENDOR_SUCCESS = "Deleted Vendor: %1$s";

    private final Index targetIndex;

    public DeleteVendorCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Vendor> lastShownList = model.getFilteredVendorList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
        }

        Vendor vendorToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteVendor(vendorToDelete);

        // Check if event contains vendorToDelete, if true, remove vendor from the event's vendor list
        for (Event event : model.getAddressBook().getEventList()) {
            if (event.getVendors().contains(vendorToDelete)) {
                List<Vendor> editedVendorList = new ArrayList<>(event.getVendors());
                editedVendorList.remove(vendorToDelete);
                Event updatedEvent = new Event(event.getName(), event.getDescription(),
                        event.getFromDate(), event.getToDate(), event.getNote(), event.getPersons(),
                        editedVendorList, event.getVenue());
                model.setEvent(event, updatedEvent);
            }
        }

        // Check if the current event that is being shown in the event details is affected
        Event eventToView = model.getEventToView();
        boolean isNotNull = eventToView != null;
        if (isNotNull && eventToView.getVendors().contains(vendorToDelete)) {
            Event currentlyShownEvent = model.getEventToView();
            List<Vendor> editedVendorList = new ArrayList<>(currentlyShownEvent.getVendors());
            editedVendorList.remove(vendorToDelete);
            Event updatedEvent = new Event(currentlyShownEvent.getName(), currentlyShownEvent.getDescription(),
                    currentlyShownEvent.getFromDate(), currentlyShownEvent.getToDate(), currentlyShownEvent.getNote(),
                    currentlyShownEvent.getPersons(), editedVendorList, currentlyShownEvent.getVenue());
            model.setEventToView(updatedEvent);
        }

        model.updateFilteredVendorList(Model.PREDICATE_SHOW_ALL_VENDOR);

        return new CommandResult(String.format(MESSAGE_DELETE_VENDOR_SUCCESS, Messages.format(vendorToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteVendorCommand)) {
            return false;
        }

        DeleteVendorCommand otherDeleteCommand = (DeleteVendorCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
