package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.vendor.Vendor;

/**
 * Removes a vendor from a specified event in the address book.
 */
public class RemoveVendorCommand extends Command {

    public static final String COMMAND_WORD = "removeVendor";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a vendor from a specified event"
            + "by the index number used in the last event listing.\n"
            + "Parameters: " + PREFIX_EVENT_ID + "EVENT_INDEX (must be a positive integer)\n"
            + PREFIX_VENDOR + "VENDOR_INDEX (from the event vendors list)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_EVENT_ID + "2 " + PREFIX_VENDOR + "3";

    public static final String MESSAGE_SUCCESS = "Removed Vendor %1$d: %2$s from Event %3$d: %4$s";

    private final Index eventIndex;

    private final Index vendorIndex;

    /**
     * Creates an RemoveVendorCommand with {@code eventIndex} and {@code vendorIndex}
     */
    public RemoveVendorCommand(Index eventIndex, Index vendorIndex) {
        this.eventIndex = eventIndex;
        this.vendorIndex = vendorIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Event> eventList = model.getFilteredEventsList();

        // Validate if eventIndex is valid
        if (eventIndex.getZeroBased() > eventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = eventList.get(eventIndex.getZeroBased());

        // Validate if vendorIndex is valid
        if (vendorIndex.getZeroBased() > eventToEdit.getVendors().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
        }

        List<Vendor> eventVendors = new ArrayList<>(eventToEdit.getVendors());
        Vendor removedVendor = eventVendors.remove(vendorIndex.getZeroBased());
        eventToEdit.setVendors(eventVendors);

        String successMessage = String.format(
                MESSAGE_SUCCESS, vendorIndex.getOneBased(), removedVendor.getName().toString(),
                eventIndex.getOneBased(), eventToEdit.getName());

        model.setEventToView(eventToEdit);

        // Check if person exists in event's person list
        return new CommandResult(successMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemoveVendorCommand)) {
            return false;
        }

        RemoveVendorCommand otherCommand = (RemoveVendorCommand) other;
        return eventIndex.equals(otherCommand.eventIndex) && vendorIndex.equals(otherCommand.vendorIndex);
    }
}
