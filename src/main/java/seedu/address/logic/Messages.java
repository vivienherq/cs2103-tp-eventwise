package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.vendor.Vendor;
import seedu.address.model.venue.Venue;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";
    public static final String MESSAGE_INVALID_EVENT_DATE = "The date provided is invalid, "
            + "date entered should be either today's date or a future date.";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "At least one of the person index(es) "
            + "provided is invalid";
    public static final String MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX = "At least one of the vendor index(es) "
            + "provided is invalid";
    public static final String MESSAGE_INVALID_VENUE_DISPLAYED_INDEX = "The venue index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_EVENT_NO_PREFIX = "Please select person(s) or vendor(s) to be added to the event"
            + " and/or set a venue to the event";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail());
        return builder.toString();
    }

    /**
     * Formats the {@code event} for display to the user.
     */
    public static String format(Event event) {
        final StringBuilder builder = new StringBuilder();
        builder.append(event.getName())
                .append("; Description: ")
                .append(event.getDescription())
                .append("; Date: ")
                .append(event.getDate());
        return builder.toString();
    }

    /**
     * Formats the {@code venue} for display to the user.
     */
    public static String format(Venue venue) {
        final StringBuilder builder = new StringBuilder();
        builder.append(venue.getName())
                .append("; Address: ")
                .append(venue.getAddress())
                .append("; Capacity: ")
                .append(venue.getCapacity());
        return builder.toString();
    }

    /**
     * Formats the {@code vendor} for display to the user.
     */
    public static String format(Vendor vendor) {
        final StringBuilder builder = new StringBuilder();
        builder.append(vendor.getName())
                .append("; Phone: ")
                .append(vendor.getPhone())
                .append("; Email: ")
                .append(vendor.getEmail());
        return builder.toString();
    }
}
