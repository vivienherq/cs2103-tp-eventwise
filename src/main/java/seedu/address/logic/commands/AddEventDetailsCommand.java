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
import seedu.address.model.rsvp.Rsvp;
import seedu.address.model.rsvp.RsvpContainsEventPredicate;
import seedu.address.model.vendor.Vendor;
import seedu.address.model.venue.Venue;

/**
 * Add details for a specified event in EventWise
 */
public class AddEventDetailsCommand extends Command {

    public static final String COMMAND_WORD = "addEventDetails";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add details to an event specified "
            + "by the index number used in the last event listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "[" + PREFIX_PERSON + "INDEX] "
            + "[" + PREFIX_VENUE + "VENUE_ID] "
            + "[" + PREFIX_VENDOR + "VENDOR_ID]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_EVENT_ID + "3 " + PREFIX_PERSON + "2";

    public static final String MESSAGE_SUCCESS = "Added to Event %1$d: %2$s\n%3$s";
    public static final String MESSAGE_EXISTING = "Already existing in Event %1$d: %2$s\n%3$s";
    public static final String MESSAGE_NO_ACTION = "Please select person(s) or vendor(s) to be added to the event "
            + "and/or set a venue to the event";

    public static final String MESSAGE_VENUE = "Venue: %1$s";

    private final Index index;

    private final Set<Index> personIndexes;
    private final Set<Index> vendorIndexes;

    private final Index venueIndex;

    /**
     * @param index of the event in the event list to add event details
     * @param personIndexes of persons to be added to the event
     */
    public AddEventDetailsCommand(Index index, Set<Index> personIndexes, Set<Index> vendorIndexes, Index venueIndex) {
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
        List<Person> personsToAdd = model.getPersons(personIndexes);

        List<Vendor> vendorsToAdd = model.getVendors(vendorIndexes);

        if (personsToAdd.isEmpty() && vendorsToAdd.isEmpty() && venueIndex == null) {
            throw new CommandException(MESSAGE_NO_ACTION);
        }

        // Find the existing people in an event that we are trying to add.
        List<Person> existingPersons = new ArrayList<>(eventToEdit.getPersons());
        existingPersons.retainAll(personsToAdd);

        // Get the new people we are trying to add.
        List<Person> newPersons = personsToAdd.stream()
                .filter(person -> !existingPersons.contains(person)).collect(Collectors.toList());

        // Find the existing vendors in an event that we are trying to add.
        List<Vendor> existingVendors = new ArrayList<>(eventToEdit.getVendors());
        existingVendors.retainAll(vendorsToAdd);

        // Get the new vendors we are trying to add.
        List<Vendor> newVendors = vendorsToAdd.stream()
                .filter(vendor -> !existingVendors.contains(vendor)).collect(Collectors.toList());

        // Retrieve the venue that the user is trying to add if any.
        Venue venueToAdd = model.getVenue(venueIndex);

        // Edited event
        Event editedEvent = model.createEditedEvent(eventToEdit, newPersons, newVendors, venueToAdd);
        model.setEvent(eventToEdit, editedEvent);

        // Result messages
        String successfullyAddedMessage = String.format(MESSAGE_SUCCESS,
                index.getOneBased(), eventToEdit.getName(), getPersonNames(newPersons), getVendorNames(newVendors));
        String existingPersonsMessage = String.format(MESSAGE_EXISTING,
                index.getOneBased(), eventToEdit.getName(), getPersonNames(existingPersons));
        String existingVendorsMessage = String.format(MESSAGE_EXISTING,
                index.getOneBased(), eventToEdit.getName(), getVendorNames(existingVendors));

        // Venue to add
        if (venueToAdd != null && newPersons.isEmpty() && newVendors.isEmpty()) {
            String venueMessage = String.format(MESSAGE_VENUE, venueToAdd.getName());
            successfullyAddedMessage = String.format(MESSAGE_SUCCESS,
                    index.getOneBased(), eventToEdit.getName(), venueMessage);
        } else if (venueToAdd != null) {
            successfullyAddedMessage += ("\n" + String.format(MESSAGE_VENUE, venueToAdd.getName()));
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
        model.updateFilteredEventRsvpList(new RsvpContainsEventPredicate(editedEvent));

        // Set edited event to be shown in the UI
        model.setEventToView(editedEvent);

        if (!existingPersons.isEmpty() && newPersons.isEmpty() && !existingVendors.isEmpty() && newVendors.isEmpty()) {
            throw new CommandException(existingPersonsMessage + existingVendorsMessage);
        } else if (!existingPersons.isEmpty() && newPersons.isEmpty()) {
            throw new CommandException(existingPersonsMessage);
        } else if (!existingVendors.isEmpty() && newVendors.isEmpty()) {
            throw new CommandException(existingVendorsMessage);
        } else if (!existingPersons.isEmpty() && !existingVendors.isEmpty()) {
            return new CommandResult(String.format("%s\n%s\n%s",
                    successfullyAddedMessage, existingPersonsMessage, existingVendorsMessage));
        } else if (!existingPersons.isEmpty()) {
            return new CommandResult(String.format("%s\n%s", successfullyAddedMessage, existingPersonsMessage));
        } else if (!existingVendors.isEmpty()) {
            return new CommandResult(String.format("%s\n%s", successfullyAddedMessage, existingVendorsMessage));
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
