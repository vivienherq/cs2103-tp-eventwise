package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TO;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.FromDate;
import seedu.address.model.event.Name;
import seedu.address.model.event.Note;
import seedu.address.model.event.ToDate;
import seedu.address.model.rsvp.Rsvp;

/**
 * Edits the details of an existing event in the address book.
 */
public class EditEventCommand extends Command {

    public static final String COMMAND_WORD = "editEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_EVENT_ID + "INDEX (must be a positive integer) "
            + "[" + PREFIX_EVENT_NAME + "NAME] "
            + "[" + PREFIX_EVENT_DESC + "DESC] "
            + "[" + PREFIX_EVENT_FROM + "DATE] "
            + "[" + PREFIX_EVENT_TO + "DATE] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_ID + "1 "
            + PREFIX_EVENT_NAME + "FSC 2024 "
            + PREFIX_EVENT_DESC + "Freshman Social Camp 2024";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book.";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editEventDescriptor details to edit the person with
     */
    public EditEventCommand(Index index, EditEventDescriptor editEventDescriptor) {
        requireNonNull(index);
        requireNonNull(editEventDescriptor);

        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventsList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = lastShownList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        if (!eventToEdit.isSameEvent(editedEvent) && model.hasEvent(editedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        // Find Rsvp objects affected by the change and swap the event
        List<Rsvp> rsvpList = new ArrayList<>(model.getFilteredRsvpList());
        for (Rsvp rsvp: rsvpList) {
            if (rsvp.getEvent().isSameEvent(eventToEdit)) {
                Rsvp editedRsvp = new Rsvp(editedEvent, rsvp.getPerson(), rsvp.getRsvpStatus());
                rsvpList.set(rsvpList.indexOf(rsvp), editedRsvp);
            }
        }

        model.setRsvps(rsvpList);

        // Update event details in UI
        model.setEvent(eventToEdit, editedEvent);

        // Check if the current event that is being shown in the event details is affected
        Event eventToView = model.getEventToView();
        if (eventToView != null && eventToView.isSameEvent(eventToEdit)) {
            model.setEventToView(editedEvent);
        }

        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, Messages.format(editedEvent)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        Name updatedName = editEventDescriptor.getName().orElse(eventToEdit.getName());
        Description updatedDescription = editEventDescriptor.getDescription().orElse(eventToEdit.getDescription());
        FromDate updatedFromDate = editEventDescriptor.getFromDate().orElse(eventToEdit.getFromDate());
        ToDate updatedToDate = editEventDescriptor.getToDate().orElse(eventToEdit.getToDate());
        Note updatedNote = editEventDescriptor.getNote().orElse(eventToEdit.getNote());

        return new Event(updatedName, updatedDescription, updatedFromDate, updatedToDate, updatedNote,
                eventToEdit.getPersons(), eventToEdit.getVendors(), eventToEdit.getVenue());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEventCommand)) {
            return false;
        }

        EditEventCommand otherEditEventCommand = (EditEventCommand) other;
        return index.equals(otherEditEventCommand.index)
                && editEventDescriptor.equals(otherEditEventCommand.editEventDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editEventDescriptor", editEventDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditEventDescriptor {
        private Name name;
        private Description description;
        private FromDate fromDate;
        private ToDate toDate;
        private Note note;

        public EditEventDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setName(toCopy.name);
            setDescription(toCopy.description);
            setFromDate(toCopy.fromDate);
            setToDate(toCopy.toDate);
            setNote(toCopy.note);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, description, fromDate, note);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setFromDate(FromDate fromDate) {
            this.fromDate = fromDate;
        }

        public Optional<FromDate> getFromDate() {
            return Optional.ofNullable(fromDate);
        }

        public void setToDate(ToDate toDate) {
            this.toDate = toDate;
        }

        public Optional<ToDate> getToDate() {
            return Optional.ofNullable(toDate);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            EditEventDescriptor otherEditPersonDescriptor = (EditEventDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(description, otherEditPersonDescriptor.description)
                    && Objects.equals(fromDate, otherEditPersonDescriptor.fromDate);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("description", description)
                    .add("date", fromDate)
                    .add("note", note)
                    .toString();
        }
    }
}
