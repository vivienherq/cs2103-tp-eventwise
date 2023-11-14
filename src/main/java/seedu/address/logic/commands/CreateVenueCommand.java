package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.venue.Venue;

/**
 * Adds a venue to the address book.
 */
public class CreateVenueCommand extends Command {

    public static final String COMMAND_WORD = "venue";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a venue to EventWise. \n"
            + "Parameters: "
            + PREFIX_VENUE_NAME + "NAME "
            + PREFIX_VENUE_ADDRESS + "ADDRESS "
            + PREFIX_VENUE_CAPACITY + "CAPACITY \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_VENUE_NAME + "MPSH 1 "
            + PREFIX_VENUE_ADDRESS + "Multipurpose Sports Hall 1, Sports and Recreation Centre, 119077 "
            + PREFIX_VENUE_CAPACITY + "500";

    public static final String MESSAGE_SUCCESS = "New Venue added: %1$s";
    public static final String MESSAGE_DUPLICATE_VENUE = "This venue already exists in EventWise";

    private final Venue toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public CreateVenueCommand(Venue venue) {
        requireNonNull(venue);
        toAdd = venue;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasVenue(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_VENUE);
        }

        model.addVenue(toAdd);
        model.updateFilteredVenueList(Model.PREDICATE_SHOW_ALL_VENUES);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CreateVenueCommand)) {
            return false;
        }

        CreateVenueCommand otherCreateVenueCommand = (CreateVenueCommand) other;
        return toAdd.equals(otherCreateVenueCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
