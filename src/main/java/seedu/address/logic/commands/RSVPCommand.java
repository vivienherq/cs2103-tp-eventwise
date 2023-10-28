package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.rsvp.RSVP;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;

public class RSVPCommand extends Command {
    public static final String COMMAND_WORD = "rsvp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the RSVP status of a person to an event. "
            + "Parameters: "
            + PREFIX_EVENT_ID + "EVENTID "
            + PREFIX_PERSON + "PERSONID\n "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_ID + "1 "
            + PREFIX_PERSON + "1";

    public static final String MESSAGE_SUCCESS = "RSVP status has been updated: %1$s";

//    private final Event event;
//    private final Person person;
//    private final RSVPStatus rsvpStatus;
    private final RSVP rsvp;

    /**
     * Creates an RSVPCommand to set the specified {@code RSVP}
     */
    public RSVPCommand(RSVP rsvp) {
        this.rsvp = rsvp;
    }


    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
//    public RSVPCommand(Event event, Person person) {
//        // Need check if event and person is null!
//        this.event = event;
//        this.person = person;
//    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
//        RSVP newRSVP = new RSVP(event, person);
        model.addRSVP(rsvp);
        return new CommandResult(String.format(MESSAGE_SUCCESS, ""));
    }
}
