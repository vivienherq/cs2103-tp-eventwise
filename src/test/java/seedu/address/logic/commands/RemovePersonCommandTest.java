package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.FSC;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_OUT_OF_RANGE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TypicalAddressBook;

public class RemovePersonCommandTest {
    private Model model = new ModelManager(TypicalAddressBook.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexWithinRange_success() {
        // Get a copy of the model a.k.a the expected model
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        // Get the test event
        Event testEvent = expectedModel.getAddressBook().getEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        // Get the first person
        Person person = expectedModel.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // Build edited event
        Event testEditedEvent = new EventBuilder(testEvent).withPersons(List.of(person)).build();
        // Set the changes to expected model
        model.setEvent(testEvent, testEditedEvent);

        // Test the remove person command
        RemovePersonCommand removePersonCommand = new RemovePersonCommand(INDEX_FIRST_EVENT, INDEX_FIRST_PERSON);
        String expectedMessage = String.format(RemovePersonCommand.MESSAGE_SUCCESS,
                INDEX_FIRST_PERSON.getOneBased(), ALICE.getName().fullName,
                INDEX_FIRST_EVENT.getOneBased(), FSC.getName().eventName);

        assertCommandSuccess(removePersonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEventIndexOutOfRange_failure() {
        RemovePersonCommand removePersonCommand = new RemovePersonCommand(INDEX_OUT_OF_RANGE, INDEX_FIRST_PERSON);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        assertCommandFailure(removePersonCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidPersonIndexOutOfRange_failure() {
        RemovePersonCommand removePersonCommand = new RemovePersonCommand(INDEX_FIRST_EVENT, INDEX_OUT_OF_RANGE);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_PERSON_INDEX);

        assertCommandFailure(removePersonCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        final RemovePersonCommand standardCommand = new RemovePersonCommand(INDEX_FIRST_EVENT, INDEX_SECOND_PERSON);

        // same values -> returns true
        RemovePersonCommand commandWithSameValues = new RemovePersonCommand(INDEX_FIRST_EVENT, INDEX_SECOND_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemovePersonCommand(INDEX_SECOND_EVENT, INDEX_SECOND_PERSON)));
    }
}
