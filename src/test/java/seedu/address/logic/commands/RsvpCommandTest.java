package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.ACADEMIC;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.FromDate;
import seedu.address.model.event.ToDate;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.rsvp.Rsvp;
import seedu.address.model.rsvp.RsvpStatus;

public class RsvpCommandTest {

    private Model model;

    @BeforeEach
    public void initialize() {
        model = new ModelManager();
    }

    @Test
    public void constructor_nullRsvp_throwsNullPointerException() {
        Index index = Index.fromZeroBased(0);
        assertThrows(NullPointerException.class, () -> new RsvpCommand(null, index, RsvpStatus.CC));
        assertThrows(NullPointerException.class, () -> new RsvpCommand(index, null, RsvpStatus.CC));
        assertThrows(NullPointerException.class, () -> new RsvpCommand(index, index, null));

    }

    @Test
    public void execute_nullRsvp_throwsCommandException() {
        Index invalidIndex = Index.fromZeroBased(0);
        RsvpCommand rsvpCommand = new RsvpCommand(invalidIndex, invalidIndex, RsvpStatus.CC);
        assertThrows(CommandException.class, () -> rsvpCommand.execute(model));
    }

    @Test
    public void execute_personNotInEvent_throwsCommandException() {
        Person newPerson = new Person(new Name("Dom"), new Phone("9213"), new Email("dom@gma.com"));
        model.addPerson(newPerson);
        model.addEvent(ACADEMIC);
        Index validIndex = Index.fromZeroBased(0);
        RsvpCommand rsvpCommand = new RsvpCommand(validIndex, validIndex, RsvpStatus.CC);
        assertThrows(CommandException.class, () -> rsvpCommand.execute(model));
    }

    @Test
    public void execute_rsvpCommand_success() {
        Person newPerson = new Person(new Name("Dom"), new Phone("9213"), new Email("dom@gma.com"));
        List<Person> personList = new ArrayList<>();
        personList.add(newPerson);
        Event pe = new Event(new seedu.address.model.event.Name("PE"), new Description("Practical Exam"),
                new FromDate("10-10-2023"), new ToDate("11-10-2023"),
                null, personList, new ArrayList<>(), null);

        model = new ModelManager();
        model.addPerson(newPerson);
        model.addEvent(pe);
        Index validIndex = Index.fromZeroBased(0);
        RsvpCommand rsvpCommand = new RsvpCommand(validIndex, validIndex, RsvpStatus.CC);

        Model expectedModel = new ModelManager();
        expectedModel.addPerson(newPerson);
        expectedModel.addEvent(pe);
        Rsvp newRsvp = new Rsvp(pe, newPerson, RsvpStatus.CC);
        expectedModel.addRsvp(newRsvp);

        String expectedSuccessMessage =
                String.format(RsvpCommand.MESSAGE_SUCCESS, newRsvp.getEventName(),
                        newRsvp.getPersonName(), newRsvp.getRsvpStatus().getStatus());
        assertCommandSuccess(rsvpCommand, model, expectedSuccessMessage, expectedModel);
    }
}
