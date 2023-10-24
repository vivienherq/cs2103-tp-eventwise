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

    private final Index index;

    // Preferably move it to a descriptor class like EditPerson
    private final Set<Index> personIndexes;

    /**
     * @param index of the event in the event list to add event details
     * @param personIndexes of persons to be added to the event
     */
    public AddEventDetailsCommand(Index index, Set<Index> personIndexes) {
        // How can we add multiple persons at the same time???
        this.index = index;
        this.personIndexes = personIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> eventList = model.getFilteredEventsList();

        if (index.getZeroBased() >= eventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        // Use model to retrieve event to edit
        Event eventToEdit = eventList.get(index.getZeroBased());

        // Retrieves a list of persons that the user is trying to add
        List<Person> personsToAdd = getPersonsToAdd(model, personIndexes);

        // Checks for cases where the user did not specify a person to add
        if (personsToAdd.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_PERSON_SPECIFIED);
        }

        // Find the existing people in an event that we are trying to add.
        List<Person> existingPersons = getExistingPersons(eventToEdit, personsToAdd);

        // Get the new people we are trying to add.
        List<Person> newPersons = personsToAdd.stream()
                .filter(person -> !existingPersons.contains(person)).collect(Collectors.toList());

        // Edited event
        Event editedEvent = createEditedEvent(eventToEdit, newPersons);
        model.setEvent(eventToEdit, editedEvent);

        // Result messages
        String successfullyAddedMessage = String.format(MESSAGE_SUCCESS,
                index.getOneBased(), eventToEdit.getName(), getPersonNames(newPersons));
        String existingPersonsMessage = String.format(MESSAGE_EXISTING,
                index.getOneBased(), eventToEdit.getName(), getPersonNames(existingPersons));


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

    /**
     * Creates and returns an {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(Event eventToEdit, List<Person> personsToAdd) {
        assert eventToEdit != null;

        // Using set ensures that we don't add duplicate people into an event
        List<Person> currentAttendees = new ArrayList<>(eventToEdit.getPersons());

        for (Person person: personsToAdd) {
            currentAttendees.add(person);
        }

        return new Event(eventToEdit.getName(), eventToEdit.getDescription(),
                eventToEdit.getDate(), currentAttendees);
    }

    private static List<Person> getPersonsToAdd(Model model, Set<Index> personIndexes) {
        // Get person list from the model manager
        List<Person> personList = model.getFilteredPersonList();

        // Create a list of persons to add
        List<Person> personsToAdd = new ArrayList<>();

        for (Index personIndex: personIndexes) {
            Person person = personList.get(personIndex.getZeroBased());
            personsToAdd.add(person);
        }

        return personsToAdd;
    }

    private static List<Person> getExistingPersons(Event eventToEdit, List<Person> personsToAdd) {
        assert eventToEdit != null;

        List<Person> existingAttendees = new ArrayList<>();

        // Using set ensures that we don't add duplicate people into an event
        List<Person> currentAttendees = eventToEdit.getPersons();
        for (Person person : personsToAdd) {
            if (currentAttendees.contains(person)) {
                existingAttendees.add(person);
            }
        }

        return existingAttendees;
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
