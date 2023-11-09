package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VENDORS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * View a list of all vendors in EventWise
 */
public class ViewVendorsCommand extends Command {

    public static final String COMMAND_WORD = "viewVendors";

    public static final String MESSAGE_SUCCESS = "Listed all vendor(s)";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredVendorList(PREDICATE_SHOW_ALL_VENDORS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
