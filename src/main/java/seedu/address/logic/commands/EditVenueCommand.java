package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VENUES;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.venue.Address;
import seedu.address.model.venue.Capacity;
import seedu.address.model.venue.Name;
import seedu.address.model.venue.Venue;

/**
 * Edits the details of an existing venue in the address book.
 */
public class EditVenueCommand extends Command {

    public static final String COMMAND_WORD = "editVenue";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the venue identified "
            + "by the index number used in the displayed venue list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_VENUE + "INDEX (must be a positive integer) "
            + "[" + PREFIX_VENUE_NAME + "NAME] "
            + "[" + PREFIX_VENUE_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_VENUE_CAPACITY + "CAPACITY]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_VENUE + "1 "
            + PREFIX_VENUE_ADDRESS + "5 Sports Drive 2, Singapore 117508 "
            + PREFIX_VENUE_CAPACITY + "300";

    public static final String MESSAGE_EDIT_VENUE_SUCCESS = "Edited Venue: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_VENUE = "This venue already exists in the address book.";

    private final Index index;
    private final EditVenueDescriptor editVenueDescriptor;

    /**
     * @param index of the venue in the filtered venue list to edit
     * @param editVenueDescriptor details to edit the venue with
     */
    public EditVenueCommand(Index index, EditVenueDescriptor editVenueDescriptor) {
        requireNonNull(index);
        requireNonNull(editVenueDescriptor);

        this.index = index;
        this.editVenueDescriptor = new EditVenueDescriptor(editVenueDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Venue> lastShownList = model.getFilteredVenuesList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VENUE_DISPLAYED_INDEX);
        }

        Venue venueToEdit = lastShownList.get(index.getZeroBased());
        Venue editedVenue = createEditedVenue(venueToEdit, editVenueDescriptor);

        if (!venueToEdit.isSameVenue(editedVenue) && model.hasVenue(editedVenue)) {
            throw new CommandException(MESSAGE_DUPLICATE_VENUE);
        }

        model.setVenue(venueToEdit, editedVenue);

        // Check if event contains venueToEdit, if true, update event venue
        for (Event event : model.getAddressBook().getEventList()) {
            if (event.getVenue() == null) {
                continue;
            }

            if (event.getVenue().isSameVenue(venueToEdit)) {
                Event updatedEvent = new Event(event.getName(), event.getDescription(),
                        event.getFromDate(), event.getToDate(), event.getNote(), event.getPersons(),
                        event.getVendors(), editedVenue);
                model.setEvent(event, updatedEvent);
            }
        }

        // Check if the current event that is being shown in the event details is affected
        Event eventToView = model.getEventToView();
        boolean isNotNull = eventToView != null && eventToView.getVenue() != null;
        if (isNotNull && eventToView.getVenue().isSameVenue(venueToEdit)) {
            Event currentlyShownEvent = model.getEventToView();
            Event updatedEvent = new Event(currentlyShownEvent.getName(), currentlyShownEvent.getDescription(),
                    currentlyShownEvent.getFromDate(), currentlyShownEvent.getToDate(), currentlyShownEvent.getNote(),
                    currentlyShownEvent.getPersons(), currentlyShownEvent.getVendors(), editedVenue);
            model.setEventToView(updatedEvent);
        }

        model.updateFilteredVenueList(PREDICATE_SHOW_ALL_VENUES);
        return new CommandResult(String.format(MESSAGE_EDIT_VENUE_SUCCESS, Messages.format(editedVenue)));
    }

    /**
     * Creates and returns a {@code Venue} with the details of {@code venueToEdit}
     * edited with {@code editVenueDescriptor}.
     */
    private static Venue createEditedVenue(Venue venueToEdit, EditVenueDescriptor editVenueDescriptor) {
        assert venueToEdit != null;

        Name updatedName = editVenueDescriptor.getName().orElse(venueToEdit.getName());
        Address updatedAddress = editVenueDescriptor.getAddress().orElse(venueToEdit.getAddress());
        Capacity updatedCapacity = editVenueDescriptor.getCapacity().orElse(venueToEdit.getCapacity());


        return new Venue(updatedName, updatedAddress, updatedCapacity);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditVenueCommand)) {
            return false;
        }

        EditVenueCommand otherEditVenueCommand = (EditVenueCommand) other;
        return index.equals(otherEditVenueCommand.index)
                && editVenueDescriptor.equals(otherEditVenueCommand.editVenueDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editVenueDescriptor", editVenueDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditVenueDescriptor {
        private Name name;
        private Address address;
        private Capacity capacity;

        public EditVenueDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditVenueDescriptor(EditVenueDescriptor toCopy) {
            setName(toCopy.name);
            setAddress(toCopy.address);
            setCapacity(toCopy.capacity);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, address, capacity);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setCapacity(Capacity capacity) {
            this.capacity = capacity;
        }

        public Optional<Capacity> getCapacity() {
            return Optional.ofNullable(capacity);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditVenueDescriptor)) {
                return false;
            }

            EditVenueDescriptor otherEditVenueDescriptor = (EditVenueDescriptor) other;
            return Objects.equals(name, otherEditVenueDescriptor.name)
                    && Objects.equals(address, otherEditVenueDescriptor.address)
                    && Objects.equals(capacity, otherEditVenueDescriptor.capacity);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("address", address)
                    .add("capacity", capacity)
                    .toString();
        }
    }
}
