package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

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

    public static final String MESSAGE_SUCCESS = "%1$s has been successfully added to Event %2$d: %3$s";
    public static final String MESSAGE_EXISTING = "%1$s is already added to Event %2$d: %3$s";

    private final Index index;

    // Preferably move it to a descriptor class like EditPerson
    private final Set<Index> personIndexes;

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

        Set<Person> personsToAdd = getPersonsToAdd(model, personIndexes);

        // Temporary Code
        if (personsToAdd.isEmpty()) {
            throw new CommandException("Please select a person to add to the event");
        }

        // Find people who already have been added and feedback
        // TODO: Create a dedicated method to handle adding results
        // Join objects added
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (Person person : personsToAdd) {
            stringJoiner.add(person.getName().toString());
        }

        // Edited event
        Event editedEvent = createEditedEvent(eventToEdit, personsToAdd);
        model.setEvent(eventToEdit, editedEvent);

        // Display success message
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, stringJoiner, index.getOneBased(), eventToEdit.getName()));
    }

    /**
     * Creates and returns an {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(Event eventToEdit, Set<Person> personsToAdd) {
        assert eventToEdit != null;

        // Using set ensures that we don't add duplicate people into an event
        Set<Person> currentAttendees = eventToEdit.getPersons();
        currentAttendees.addAll(personsToAdd);

        return new Event(eventToEdit.getName(), eventToEdit.getDescription(),
                eventToEdit.getDate(), currentAttendees);
    }

    private static Set<Person> getPersonsToAdd(Model model, Set<Index> personIndexes) {
        // Get person list from the model manager
        List<Person> personList = model.getFilteredPersonList();

        // Create a set of persons to add
        Set<Person> personSet = new HashSet<>();

        for (Index personIndex: personIndexes) {
            Person personToAdd = personList.get(personIndex.getZeroBased());
            personSet.add(personToAdd);
        }

        return personSet;
    }
}
