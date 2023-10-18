package seedu.address.logic.commands;

import static seedu.address.logic.commands.AddEventDetailsCommand.MESSAGE_EXISTING;
import static seedu.address.logic.commands.AddEventDetailsCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_GREATER_THAN_RANGE_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

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
import seedu.address.testutil.EventBuilder;

public class AddEventDetailsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidIndexOutOfRange_failure() {
        HashSet<Index> personIndexes = new HashSet<>();
        personIndexes.add(INDEX_SECOND_PERSON);
        AddEventDetailsCommand addEventDetailsCommand =
                new AddEventDetailsCommand(INDEX_GREATER_THAN_RANGE_EVENT, personIndexes);

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        assertCommandFailure(addEventDetailsCommand, model, expectedMessage);
    }

    @Test
    public void execute_noPersonSpecified_failure() {
        HashSet<Index> personIndexes = new HashSet<>();
        AddEventDetailsCommand addEventDetailsCommand =
                new AddEventDetailsCommand(INDEX_FIRST_EVENT, personIndexes);

        String expectedMessage = String.format(Messages.MESSAGE_NO_PERSON_SPECIFIED);
        assertCommandFailure(addEventDetailsCommand, model, expectedMessage);
    }

    @Test
    public void execute_addNewPersonToEvent_success() {
        HashSet<Index> personIndexes = new HashSet<>();
        personIndexes.add(INDEX_SECOND_PERSON);

        // Command to simulate
        AddEventDetailsCommand addEventDetailsCommand = new AddEventDetailsCommand(INDEX_FIRST_EVENT, personIndexes);

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

        // Simulate adding a person into an event by swapping the existing event object with the updated event object
        Event testEvent = model.getAddressBook().getEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        // Get the second person
        Person person = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        // Edited event
        Event testEditedEvent = new EventBuilder(testEvent).withPersons(List.of(person)).build();

        // Update the event in the environment model to already contain person 2.
        model.setEvent(testEvent, testEditedEvent);

        // Command to simulate
        AddEventDetailsCommand addEventDetailsCommand = new AddEventDetailsCommand(INDEX_FIRST_EVENT, personIndexes);

        // Expected exception message
        String expectedMessage = String.format(MESSAGE_EXISTING,
                INDEX_FIRST_EVENT.getOneBased(), testEditedEvent.getName(), person.getName());

        // Check if the updated model
        assertCommandFailure(addEventDetailsCommand, model, expectedMessage);
    }

    @Test
    public void execute_addExistingAndNewPersonToEvent_success() {
        // Person Indexes should only contain the index of the second person
        HashSet<Index> personIndexes = new HashSet<>();
        personIndexes.add(INDEX_FIRST_PERSON);
        personIndexes.add(INDEX_SECOND_PERSON);

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
        AddEventDetailsCommand addEventDetailsCommand = new AddEventDetailsCommand(INDEX_FIRST_EVENT, personIndexes);

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

        // Check if the updated model
        assertCommandSuccess(addEventDetailsCommand, model, expectedMessage, expectedModel);
    }
}
