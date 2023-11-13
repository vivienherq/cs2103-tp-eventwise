package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.vendor.Vendor;

/**
 * Adds a vendor to the address book.
 */
public class CreateVendorCommand extends Command {

    public static final String COMMAND_WORD = "vendor";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a vendor to EventWise. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "SUN Catering "
            + PREFIX_PHONE + "64226800 "
            + PREFIX_EMAIL + "catering@sun.com ";

    public static final String MESSAGE_SUCCESS = "New vendor added: %1$s";
    public static final String MESSAGE_DUPLICATE_VENDOR = "This vendor already exists in the address book";

    private final Vendor toAdd;

    /**
     * Creates an CreateVendorCommand to add the specified {@code Person}
     */
    public CreateVendorCommand(Vendor vendor) {
        requireNonNull(vendor);
        toAdd = vendor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasVendor(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_VENDOR);
        }

        model.addVendor(toAdd);
        model.updateFilteredVendorList(Model.PREDICATE_SHOW_ALL_VENDORS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CreateVendorCommand)) {
            return false;
        }

        CreateVendorCommand otherCreateVendorCommand = (CreateVendorCommand) other;
        return toAdd.equals(otherCreateVendorCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
