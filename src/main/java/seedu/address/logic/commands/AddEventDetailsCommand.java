package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.venue.Venue;

/**
 * Add details for a specified event in EventWise
 */
public class AddEventDetailsCommand extends Command {

    public static final String COMMAND_WORD = "addEventDetails";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add details to an event specified"
            + "by the index number used in the last event listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "[" + PREFIX_PERSON + "INDEX] "
            + "[" + PREFIX_VENUE + "VENUE_ID] "
            + "[" + PREFIX_VENDOR + "VENDOR_ID]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_EVENT_ID + "3 " + PREFIX_PERSON + "2";

    public static final String MESSAGE_SUCCESS = "Added to Event %1$d: %2$s\n%3$s";
    public static final String MESSAGE_EXISTING = "Already existing in Event %1$d: %2$s\n%3$s";
    public static final String MESSAGE_NO_ACTION = "Please select person(s) to be added to the event "
            + "and/or set a venue to the event";

    public static final String MESSAGE_VENUE = "Venue: %1$s";

    private final Index index;

    private final Set<Index> personIndexes;

    private final Index venueIndex;

    /**
     * @param index of the event in the event list to add event details
     * @param personIndexes of persons to be added to the event
     */
    public AddEventDetailsCommand(Index index, Set<Index> personIndexes, Index venueIndex) {
        this.index = index;
        this.personIndexes = personIndexes;
        this.venueIndex = venueIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // Retrieve list of events saved in EventWise
        List<Event> eventList = model.getFilteredEventsList();

        // Check if index greater than list size
        if (index.getZeroBased() >= eventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        // Use model to retrieve event to edit
        Event eventToEdit = eventList.get(index.getZeroBased());

        // Retrieves a list of persons that the user is trying to add
        List<Person> personsToAdd = model.getPersons(personIndexes);

        if (personsToAdd.isEmpty() && venueIndex == null) {
            throw new CommandException(MESSAGE_NO_ACTION);
        }

        // Find the existing people in an event that we are trying to add.
        List<Person> existingPersons = new ArrayList<>(eventToEdit.getPersons());
        existingPersons.retainAll(personsToAdd);

        // Get the new people we are trying to add.
        List<Person> newPersons = personsToAdd.stream()
                .filter(person -> !existingPersons.contains(person)).collect(Collectors.toList());

        // Retrieve the venue that the user is trying to add if any.
        Venue venueToAdd = model.getVenue(venueIndex);

        // Edited event
        Event editedEvent = model.createEditedEvent(eventToEdit, newPersons, venueToAdd);
        model.setEvent(eventToEdit, editedEvent);

        // Result messages
        String successfullyAddedMessage = String.format(MESSAGE_SUCCESS,
                index.getOneBased(), eventToEdit.getName(), getPersonNames(newPersons));
        String existingPersonsMessage = String.format(MESSAGE_EXISTING,
                index.getOneBased(), eventToEdit.getName(), getPersonNames(existingPersons));

        // Venue to add
        if (venueToAdd != null && newPersons.isEmpty()) {
            String venueMessage = String.format(MESSAGE_VENUE, venueToAdd.getName());
            successfullyAddedMessage = String.format(MESSAGE_SUCCESS,
                    index.getOneBased(), eventToEdit.getName(), venueMessage);
        } else if (venueToAdd != null) {
            successfullyAddedMessage += ("\n" + String.format(MESSAGE_VENUE, venueToAdd.getName()));
        }

        // Set edited event to be shown in the UI
        model.setEventToView(editedEvent);

        if (!existingPersons.isEmpty() && newPersons.isEmpty()) {
            throw new CommandException(existingPersonsMessage);
        } else if (!existingPersons.isEmpty()) {
            return new CommandResult(String.format("%s\n%s", successfullyAddedMessage, existingPersonsMessage));
        } else {
            return new CommandResult(successfullyAddedMessage);
        }
    }

    private String getPersonNames(List<Person> persons) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            stringBuilder.append(person.getName().toString());
            if (i < persons.size() - 1) {
                stringBuilder.append(System.lineSeparator());
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddEventDetailsCommand)) {
            return false;
        }

        AddEventDetailsCommand otherCommand = (AddEventDetailsCommand) other;
        return index.equals(otherCommand.index);
    }
}
