package seedu.address.model.rsvp;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * An enum to represent the valid RSVP status.
 */
public enum RsvpStatus {
    CC("Confirm Coming"),
    CCC("Confirm Not Coming"),
    TBC("To Be Confirmed");

    public static final String MESSAGE_CONSTRAINTS =
            "Value of RSVP Status can only be CC, CCC or TBC.";

    private final String status;

    /**
     * Constructs an RsvpStatus with the specified status.
     *
     * @param status The description of the RSVP status.
     */
    RsvpStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    /**
     * Returns the corresponding {@code RsvpStatus} for the given status string.
     *
     * @param status The status string to be converted into an RsvpStatus.
     * @return The {@code RsvpStatus} that corresponds to the given status string.
     * @throws ParseException If the given status string does not match any RsvpStatus.
     */
    public static RsvpStatus getRsvpStatus(String status) throws ParseException {
        for (RsvpStatus rsvpStatus : RsvpStatus.values()) {
            if (rsvpStatus.name().equals(status)) {
                return rsvpStatus;
            }
        }
        throw new ParseException(MESSAGE_CONSTRAINTS);
    }
}
