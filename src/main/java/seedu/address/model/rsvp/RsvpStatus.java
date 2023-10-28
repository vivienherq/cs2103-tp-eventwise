package seedu.address.model.rsvp;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * An enum to represent the valid RSVP status.
 */
public enum RsvpStatus {
    CONFIRM_COMING("CC"),
    CONFIRM_NOT_COMING("CCC"),
    TO_BE_CONFIRMED("TBC");

    public static final String MESSAGE_CONSTRAINTS =
            "Value of RSVP Status can only be CC, CCC or TBC.";

    private final String status;

    RsvpStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public static RsvpStatus getRsvpStatus(String status) throws ParseException {
        for (RsvpStatus rsvpStatus : RsvpStatus.values()) {
            if (rsvpStatus.getStatus().equals(status)) {
                return rsvpStatus;
            }
        }
        throw new ParseException(MESSAGE_CONSTRAINTS);
    }
}
