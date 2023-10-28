package seedu.address.model.rsvp.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateRSVPException extends RuntimeException {
    public DuplicateRSVPException() {
        super("Operation would result in duplicate rsvps");
    }
}
