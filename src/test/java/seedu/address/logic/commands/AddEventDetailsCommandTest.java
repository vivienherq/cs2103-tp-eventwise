package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;

public class AddEventDetailsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
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
}
