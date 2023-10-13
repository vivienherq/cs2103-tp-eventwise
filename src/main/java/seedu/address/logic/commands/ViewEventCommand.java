package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * View details for a specified event in EventWise
 */
public class ViewEventCommand extends Command {

    public static final String COMMAND_WORD = "viewEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": View the details of an event specified"
            + "by the index number used in the last event listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_EVENT_ID + "1 ";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "viewEvent command not implemented yet";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
