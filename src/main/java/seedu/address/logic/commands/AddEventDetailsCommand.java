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
import seedu.address.model.vendor.Vendor;
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

    public static final String MESSAGE_SUCCESS = "Added to Event %1$d: %2$s\n";
    public static final String MESSAGE_EXISTING = "Already existing in Event %1$d: %2$s\n%3$s";
    public static final String MESSAGE_NO_ACTION = "Please select person(s) or vendor(s) to be added to the event "
            + "and/or set a venue to the event";
    public static final String MESSAGE_DUPLICATE_PERSONS =
            "Not allowed to add the same person twice to an event: Person %1$d: %2$s";

    public static final String MESSAGE_DUPLICATE_VENDORS =
            "Not allowed to add the same vendor twice to an event: Vendor %1$d: %2$s";

    public static final String MESSAGE_VENUE = "\nVenue: %1$s";

    private final Index index;

    private final Set<Index> personIndexes;
    private final Set<Index> vendorIndexes;

    private final Index venueIndex;

    /**
     * @param index of the event in the event list to add event details
     * @param personIndexes of persons to be added to the event
     */
    public AddEventDetailsCommand(Index index, Set<Index> personIndexes, Set<Index>vendorIndexes, Index venueIndex) {
        this.index = index;
        this.personIndexes = personIndexes;
        this.vendorIndexes = vendorIndexes;
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
        List<Person> personsToAdd = getPersonsToAdd(model, personIndexes);

        List<Vendor> vendorsToAdd = getVendorsToAdd(model, vendorIndexes);

        if (personsToAdd.isEmpty() && vendorsToAdd.isEmpty() && venueIndex == null) {
            throw new CommandException(MESSAGE_NO_ACTION);
        }

        // Find the existing people in an event that we are trying to add.
        List<Person> existingPersons = getExistingPersons(eventToEdit, personsToAdd);

        List<Vendor> existingVendors = getExistingVendors(eventToEdit, vendorsToAdd);

        // Get the new people we are trying to add.
        List<Person> newPersons = personsToAdd.stream()
                .filter(person -> !existingPersons.contains(person)).collect(Collectors.toList());

        List<Vendor> newVendors = vendorsToAdd.stream()
                .filter(vendor -> !existingVendors.contains(vendor)).collect(Collectors.toList());

        // Retrieve the venue list
        List<Venue> venueList = model.getFilteredVenuesList();

        // Retrieve the venue that the user is trying to add if any.
        Venue venueToAdd = getVenueToAdd(venueList, venueIndex);

        // Edited event
        Event editedEvent = createEditedEvent(eventToEdit, newPersons, newVendors, venueToAdd);
        model.setEvent(eventToEdit, editedEvent);

        // Result messages
        String successfullyAddedMessage = String.format(MESSAGE_SUCCESS,
                index.getOneBased(), eventToEdit.getName());

        if (!newPersons.isEmpty()) {
            successfullyAddedMessage += String.format(getPersonNames(newPersons));
        }

        if (!newVendors.isEmpty()) {
            successfullyAddedMessage += String.format(getVendorNames(newVendors));
        }

        if (venueToAdd != null) {
            successfullyAddedMessage += String.format(MESSAGE_VENUE, venueToAdd.getName());
        }

        String existingPersonsMessage = String.format(MESSAGE_EXISTING,
                index.getOneBased(), eventToEdit.getName(), getPersonNames(existingPersons));

        String existingVendorsMessage = String.format(MESSAGE_EXISTING,
                index.getOneBased(), eventToEdit.getName(), getVendorNames(existingVendors));

        // Set edited event to be shown in the UI
        model.setEventToView(editedEvent);

        if (!existingPersons.isEmpty() && newPersons.isEmpty()) {
            throw new CommandException(existingPersonsMessage);
        }

        if (!existingVendors.isEmpty() && newVendors.isEmpty()) {
            throw new CommandException(existingVendorsMessage);
        }

        if (!existingPersons.isEmpty() || !existingVendors.isEmpty()) {
            return new CommandResult(String.format("%s\n%s", successfullyAddedMessage,
                    existingPersonsMessage, existingVendorsMessage));
        } else {
            return new CommandResult(successfullyAddedMessage);
        }
    }

    private static Venue getVenueToAdd(List<Venue> venueList, Index venueIndex) throws CommandException {
        if (venueIndex == null) {
            return null;
        } else if (venueIndex.getZeroBased() >= venueList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VENUE_DISPLAYED_INDEX);
        } else {
            return venueList.get(venueIndex.getZeroBased());
        }
    }

    /**
     * Creates and returns an {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(Event eventToEdit, List<Person> personsToAdd,
                                           List<Vendor> vendorsToAdd, Venue venueToAdd) {
        assert eventToEdit != null;

        // Using set ensures that we don't add duplicate people into an event
        List<Person> currentAttendees = new ArrayList<>(eventToEdit.getPersons());
        List<Vendor> currentVendors = new ArrayList<>(eventToEdit.getVendors());

        for (Person person: personsToAdd) {
            currentAttendees.add(person);
        }

        for (Vendor vendor: vendorsToAdd) {
            currentVendors.add(vendor);
        }

        return new Event(eventToEdit.getName(), eventToEdit.getDescription(),
                eventToEdit.getDate(), currentAttendees, currentVendors, venueToAdd);
    }

    private static List<Person> getPersonsToAdd(Model model, Set<Index> personIndexes) throws CommandException {
        // Get person list from the model manager
        List<Person> personList = model.getFilteredPersonList();

        // Create a list of persons to add
        List<Person> personsToAdd = new ArrayList<>();

        for (Index personIndex: personIndexes) {
            if (personIndex.getZeroBased() > personList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person person = personList.get(personIndex.getZeroBased());

            if (personsToAdd.contains(person)) {
                String errorMessage = String.format(
                        MESSAGE_DUPLICATE_PERSONS, personIndex.getOneBased(), person.getName());
                throw new CommandException(errorMessage);
            }
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

    private static List<Vendor> getVendorsToAdd(Model model, Set<Index> vendorIndexes) throws CommandException {
        // Get vendor list from the model manager
        List<Vendor> vendorList = model.getFilteredVendorList();

        // Create a list of vendors to add
        List<Vendor> vendorsToAdd = new ArrayList<>();

        for (Index vendorIndex: vendorIndexes) {
            if (vendorIndex.getZeroBased() > vendorList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
            }

            Vendor vendor = vendorList.get(vendorIndex.getZeroBased());

            if (vendorsToAdd.contains(vendor)) {
                String errorMessage = String.format(
                        MESSAGE_DUPLICATE_VENDORS, vendorIndex.getOneBased(), vendor.getName());
                throw new CommandException(errorMessage);
            }
            vendorsToAdd.add(vendor);
        }

        return vendorsToAdd;
    }

    private static List<Vendor> getExistingVendors(Event eventToEdit, List<Vendor> vendorsToAdd) {
        assert eventToEdit != null;

        List<Vendor> existingVendors = new ArrayList<>();

        // Using set ensures that we don't add duplicate people into an event
        List<Vendor> currentVendors = eventToEdit.getVendors();

        for (Vendor vendor : vendorsToAdd) {
            if (currentVendors.contains(vendor)) {
                existingVendors.add(vendor);
            }
        }

        return existingVendors;
    }

    private String getVendorNames(List<Vendor> vendors) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < vendors.size(); i++) {
            Vendor vendor = vendors.get(i);
            stringBuilder.append(vendor.getName().toString());
            if (i < vendors.size() - 1) {
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
