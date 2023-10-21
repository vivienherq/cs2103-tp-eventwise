package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearEventsCommand extends Command {

    public static final String COMMAND_WORD = "clearEvents";
    public static final String MESSAGE_SUCCESS = "Events in address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.resetEvents();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
