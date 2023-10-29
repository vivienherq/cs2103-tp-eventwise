package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VENDOR;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.vendor.Vendor;

/**
 * View a list of all vendors in EventWise
 */
public class ViewVendorsCommand extends Command {

    public static final String COMMAND_WORD = "viewVendors";

    public static final String MESSAGE_SUCCESS = "List of Vendors:\n%1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredVendorList(PREDICATE_SHOW_ALL_VENDOR);
        ObservableList<Vendor> vendorList = model.getFilteredVendorList();
        String vendors = "";
        for (int i = 0; i < vendorList.size(); i++) {
            Vendor vendor = vendorList.get(i);
            vendors += String.format("%d: %s; Email: %s; Phone: %s\n", i + 1,
                    vendor.getName(), vendor.getEmail(), vendor.getPhone());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, vendors));
    }
}
