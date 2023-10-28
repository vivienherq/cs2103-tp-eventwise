package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RSVP_STATUS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.rsvp.Rsvp;
import seedu.address.model.rsvp.RsvpStatus;

/**
 * Update the RSVP of a person for an event in the address book.
 */
public class RsvpCommand extends Command {
    public static final String COMMAND_WORD = "rsvp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the RSVP status of a person to an event. "
            + "Parameters: "
            + PREFIX_EVENT_ID + "EVENTID "
            + PREFIX_PERSON + "PERSONID "
            + PREFIX_RSVP_STATUS + "RSVPSTATUS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_ID + "1 "
            + PREFIX_PERSON + "1 "
            + PREFIX_RSVP_STATUS + "CC";

    public static final String MESSAGE_SUCCESS = "RSVP status has been updated: %1$s, %2$s, %3$s";
    public static final String MESSAGE_INVALID_INDEX = "Event or Person does not exist!";
    public static final String MESSAGE_PERSON_NOT_IN_EVENT = "%1$s has not been added to %2$s!";

    private final Index eventIndex;
    private final Index personIndex;
    private final RsvpStatus rsvpStatus;

    /**
     * Creates an RSVPCommand to set the specified {@code RSVP}
     */
    public RsvpCommand(Index eventIndex, Index personIndex, RsvpStatus rsvpStatus) {

        this.eventIndex = eventIndex;
        this.personIndex = personIndex;
        this.rsvpStatus = rsvpStatus;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Rsvp rsvp = model.createRsvp(eventIndex, personIndex, rsvpStatus);
        if (rsvp == null) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }
        if (!model.isValidRsvp(rsvp)) {
            throw new CommandException(
                    String.format(MESSAGE_PERSON_NOT_IN_EVENT, rsvp.getPersonName(), rsvp.getEventName()));
        }
        model.addRsvp(rsvp);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                rsvp.getEventName(), rsvp.getPersonName(), rsvpStatus));
    }
}
