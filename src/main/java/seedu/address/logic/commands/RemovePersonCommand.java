package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.rsvp.Rsvp;

/**
 * Removes a person from a specified event in the address book.
 */
public class RemovePersonCommand extends Command {

    public static final String COMMAND_WORD = "removePerson";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a person from a specified event"
            + "by the index number used in the last event listing.\n"
            + "Parameters: " + PREFIX_EVENT_ID + "EVENT_INDEX (must be a positive integer)\n"
            + PREFIX_PERSON + "PERSON_INDEX (from the event attendees list)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_EVENT_ID + "3 " + PREFIX_PERSON + "2";

    public static final String MESSAGE_SUCCESS = "Removed Person %1$d: %2$s from Event %3$d: %4$s";

    private final Index eventIndex;

    private final Index personIndex;

    /**
     * Creates an RemovePersonCommand with {@code eventIndex} and {@code personIndex}
     */
    public RemovePersonCommand(Index eventIndex, Index personIndex) {
        this.eventIndex = eventIndex;
        this.personIndex = personIndex;
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

        // Validate if personIndex is valid
        if (personIndex.getZeroBased() > eventToEdit.getPersons().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        List<Person> eventAttendees = new ArrayList<>(eventToEdit.getPersons());
        Person removedPerson = eventAttendees.remove(personIndex.getZeroBased());
        eventToEdit.setPersons(eventAttendees);

        // Find the Rsvp object to remove
        Rsvp existingRsvp = model.findRsvp(eventToEdit, removedPerson);

        if (existingRsvp != null) {
            model.deleteRsvp(existingRsvp);
        }

        String successMessage = String.format(
                MESSAGE_SUCCESS, personIndex.getOneBased(), removedPerson.getName().toString(),
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

        if (!(other instanceof RemovePersonCommand)) {
            return false;
        }

        RemovePersonCommand otherCommand = (RemovePersonCommand) other;
        return eventIndex.equals(otherCommand.eventIndex) && personIndex.equals(otherCommand.personIndex);
    }
}
