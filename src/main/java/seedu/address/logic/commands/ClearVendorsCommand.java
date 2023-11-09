package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VENDORS;

import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearVendorsCommand extends Command {

    public static final String COMMAND_WORD = "clearVendors";
    public static final String MESSAGE_SUCCESS = "Vendors in address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.resetVendors();
        model.resetAllEventVendors();
        model.updateFilteredVendorList(PREDICATE_SHOW_ALL_VENDORS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
