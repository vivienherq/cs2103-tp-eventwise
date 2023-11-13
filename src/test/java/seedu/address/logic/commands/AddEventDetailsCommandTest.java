package seedu.address.logic.commands;

import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_VENDOR_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_VENUE_DISPLAYED_INDEX;
import static seedu.address.logic.commands.AddEventDetailsCommand.MESSAGE_EXISTING;
import static seedu.address.logic.commands.AddEventDetailsCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.AddEventDetailsCommand.MESSAGE_VENUE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VENUE;
import static seedu.address.testutil.TypicalIndexes.INDEX_OUT_OF_RANGE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_VENDOR;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.vendor.Vendor;
import seedu.address.model.venue.Venue;
import seedu.address.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddEventDetailsCommand.
 */
public class AddEventDetailsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidEventIndexOutOfRange_failure() {
        HashSet<Index> personIndexes = new HashSet<>();
        personIndexes.add(INDEX_SECOND_PERSON);

        HashSet<Index> vendorIndexes = new HashSet<>();

        AddEventDetailsCommand addEventDetailsCommand =
                new AddEventDetailsCommand(INDEX_OUT_OF_RANGE, personIndexes, vendorIndexes, null);

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        assertCommandFailure(addEventDetailsCommand, model, expectedMessage);
    }

    @Test
    public void execute_addPersonIndexOutOfRange_failure() {
        // Person Indexes should only contain the index of the second person
        HashSet<Index> personIndexes = new HashSet<>();
        personIndexes.add(INDEX_OUT_OF_RANGE);

        HashSet<Index> vendorIndexes = new HashSet<>();

        // Command to simulate
        AddEventDetailsCommand addEventDetailsCommand =
                new AddEventDetailsCommand(INDEX_FIRST_EVENT, personIndexes, vendorIndexes, INDEX_FIRST_VENUE);


        assertCommandFailure(addEventDetailsCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_addVendorIndexOutOfRange_failure() {
        HashSet<Index> personIndexes = new HashSet<>();

        HashSet<Index> vendorIndexes = new HashSet<>();
        vendorIndexes.add(INDEX_OUT_OF_RANGE);

        AddEventDetailsCommand addEventDetailsCommand =
                new AddEventDetailsCommand(INDEX_FIRST_EVENT, personIndexes, vendorIndexes, null);

        assertCommandFailure(addEventDetailsCommand, model, MESSAGE_INVALID_VENDOR_INDEX);
    }

    @Test
    public void execute_addVenueIndexOutOfRange_failure() {
        HashSet<Index> personIndexes = new HashSet<>();

        HashSet<Index> vendorIndexes = new HashSet<>();

        AddEventDetailsCommand addEventDetailsCommand =
                new AddEventDetailsCommand(INDEX_FIRST_EVENT, personIndexes, vendorIndexes, INDEX_OUT_OF_RANGE);

        assertCommandFailure(addEventDetailsCommand, model, MESSAGE_INVALID_VENUE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noPersonAndVendorSpecified_failure() {
        HashSet<Index> personIndexes = new HashSet<>();

        HashSet<Index> vendorIndexes = new HashSet<>();

        AddEventDetailsCommand addEventDetailsCommand =
                new AddEventDetailsCommand(INDEX_FIRST_EVENT, personIndexes, vendorIndexes, null);

        String expectedMessage = String.format(Messages.MESSAGE_EVENT_NO_PREFIX);
        assertCommandFailure(addEventDetailsCommand, model, expectedMessage);
    }

    @Test
    public void execute_addNewPersonToEvent_success() {
        HashSet<Index> personIndexes = new HashSet<>();
        personIndexes.add(INDEX_SECOND_PERSON);

        HashSet<Index> vendorIndexes = new HashSet<>();

        // Command to simulate
        AddEventDetailsCommand addEventDetailsCommand =
                new AddEventDetailsCommand(INDEX_FIRST_EVENT, personIndexes, vendorIndexes, null);

        // Create an edited model
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Simulate adding a person into an event by swapping the existing event object with the updated event object
        Event eventToEdit = expectedModel.getAddressBook().getEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        // Get the second person
        Person person = expectedModel.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        // Edited event
        Event editedEvent = new EventBuilder(eventToEdit).withPersons(List.of(person)).build();

        // Update the edited event
        expectedModel.setEvent(eventToEdit, editedEvent);

        // Expected success message
        String expectedSuccessMessage =
                String.format(AddEventDetailsCommand.MESSAGE_SUCCESS, INDEX_FIRST_EVENT.getOneBased(),
                        editedEvent.getName(), person.getName());

        // Check if the updated model
        assertCommandSuccess(addEventDetailsCommand, model, expectedSuccessMessage, expectedModel);
    }

    @Test
    public void execute_addExistingPersonToEvent_failure() {
        // Person Indexes should only contain the index of the second person
        HashSet<Index> personIndexes = new HashSet<>();
        personIndexes.add(INDEX_SECOND_PERSON);

        HashSet<Index> vendorIndexes = new HashSet<>();

        // Simulate adding a person into an event by swapping the existing event object with the updated event object
        Event testEvent = model.getAddressBook().getEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        // Get the second person
        Person person = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        // Edited event
        Event testEditedEvent = new EventBuilder(testEvent).withPersons(List.of(person)).build();

        // Update the event in the environment model to already contain person 2.
        model.setEvent(testEvent, testEditedEvent);

        // Command to simulate
        AddEventDetailsCommand addEventDetailsCommand =
                new AddEventDetailsCommand(INDEX_FIRST_EVENT, personIndexes, vendorIndexes, null);

        // Expected exception message
        String expectedMessage = String.format(MESSAGE_EXISTING,
                INDEX_FIRST_EVENT.getOneBased(), testEditedEvent.getName(), person.getName());

        // Check if the command fails
        assertCommandFailure(addEventDetailsCommand, model, expectedMessage);
    }

    @Test
    public void execute_addExistingAndNewPersonToEvent_success() {
        HashSet<Index> personIndexes = new HashSet<>();
        personIndexes.add(INDEX_FIRST_PERSON);
        personIndexes.add(INDEX_SECOND_PERSON);

        HashSet<Index> vendorIndexes = new HashSet<>();

        // Simulate adding a person into an event by swapping the existing event object with the updated event object
        Event testEvent = model.getAddressBook().getEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        // Person instances
        Person firstPerson = model.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        // Edited event with second person already inside.
        Event testEditedEvent = new EventBuilder(testEvent).withPersons(List.of(secondPerson)).build();

        // Update the event in the environment model to already contain person 2.
        model.setEvent(testEvent, testEditedEvent);

        // Command to simulate
        AddEventDetailsCommand addEventDetailsCommand =
                new AddEventDetailsCommand(INDEX_FIRST_EVENT, personIndexes, vendorIndexes, null);

        // Expected Model
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Simulate adding a person into an event by swapping the existing event object with the updated event object
        Event eventToEdit = expectedModel.getAddressBook().getEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        // Edited event
        Event editedEvent = new EventBuilder(eventToEdit).withPersons(List.of(firstPerson, secondPerson)).build();

        // Update the changes in the expected model
        expectedModel.setEvent(eventToEdit, editedEvent);

        // Expected exception message
        String existingPersonsMessage = String.format(MESSAGE_EXISTING,
                INDEX_FIRST_EVENT.getOneBased(), testEditedEvent.getName(), secondPerson.getName());

        String successfullyAddedMessage = String.format(MESSAGE_SUCCESS,
                INDEX_FIRST_EVENT.getOneBased(), testEditedEvent.getName(), firstPerson.getName());

        String expectedMessage = String.format("%s\n%s", successfullyAddedMessage, existingPersonsMessage);

        // Check if the updated model is the same as the expected model
        assertCommandSuccess(addEventDetailsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addVenueToEvent_success() {
        // Person Indexes should be empty
        HashSet<Index> personIndexes = new HashSet<>();

        // Vendor Indexes should be empty
        HashSet<Index> vendorIndexes = new HashSet<>();

        // Expected Model
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Simulate adding a person into an event by swapping the existing event object with the updated event object
        Event testEvent = expectedModel.getAddressBook().getEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        // Venue instance
        Venue firstVenue = expectedModel.getAddressBook().getVenueList().get(INDEX_FIRST_VENUE.getZeroBased());

        // Edited event with venue already set.
        Event testEditedEvent = new EventBuilder(testEvent).withVenue(firstVenue).build();

        // Update the event in the environment model to contain the venue.
        expectedModel.setEvent(testEvent, testEditedEvent);

        // Command to simulate
        AddEventDetailsCommand addEventDetailsCommand =
                new AddEventDetailsCommand(INDEX_FIRST_EVENT, personIndexes, vendorIndexes, INDEX_FIRST_VENUE);

        String venueMessage = String.format(MESSAGE_VENUE, firstVenue.getName());

        String expectedMessage = String.format(MESSAGE_SUCCESS,
                INDEX_FIRST_EVENT.getOneBased(), testEditedEvent.getName(), venueMessage);

        // Check if the updated model
        assertCommandSuccess(addEventDetailsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addNewVendorToEvent_success() {
        HashSet<Index> personIndexes = new HashSet<>();

        HashSet<Index> vendorIndexes = new HashSet<>();
        vendorIndexes.add(INDEX_SECOND_VENDOR);

        // Command to simulate
        AddEventDetailsCommand addEventDetailsCommand =
                new AddEventDetailsCommand(INDEX_FIRST_EVENT, personIndexes, vendorIndexes, null);

        // Create an edited model
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Simulate adding a person into an event by swapping the existing event object with the updated event object
        Event eventToEdit = expectedModel.getAddressBook().getEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        // Get the second vendor
        Vendor vendor = expectedModel.getAddressBook().getVendorList().get(INDEX_SECOND_VENDOR.getZeroBased());

        // Edited event
        Event editedEvent = new EventBuilder(eventToEdit).withVendors(List.of(vendor)).build();

        // Update the edited event
        expectedModel.setEvent(eventToEdit, editedEvent);

        // Expected success message
        String expectedSuccessMessage =
                String.format(AddEventDetailsCommand.MESSAGE_SUCCESS, INDEX_FIRST_EVENT.getOneBased(),
                        editedEvent.getName(), "");

        // Check if the updated model
        assertCommandSuccess(addEventDetailsCommand, model, expectedSuccessMessage, expectedModel);
    }
}
