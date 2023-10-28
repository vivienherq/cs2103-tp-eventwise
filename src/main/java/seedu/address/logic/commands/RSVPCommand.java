package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.rsvp.RSVP;
import seedu.address.model.rsvp.RSVPStatus;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

public class RSVPCommand extends Command {
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

    public static final String MESSAGE_SUCCESS = "RSVP status has been updated: %1$s";
    private final Index eventIndex;
    private final Index personIndex;
    private final RSVPStatus rsvpStatus;

    /**
     * Creates an RSVPCommand to set the specified {@code RSVP}
     */
    public RSVPCommand(Index eventIndex, Index personIndex, RSVPStatus rsvpStatus) {

        this.eventIndex = eventIndex;
        this.personIndex = personIndex;
        this.rsvpStatus = rsvpStatus;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        RSVP rsvp = model.createRSVP(eventIndex, personIndex, rsvpStatus);
        model.addRSVP(rsvp);
        return new CommandResult(String.format(MESSAGE_SUCCESS, ""));
    }
}
