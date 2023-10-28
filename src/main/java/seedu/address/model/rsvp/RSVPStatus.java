package seedu.address.model.rsvp;

import seedu.address.logic.parser.exceptions.ParseException;

public enum RSVPStatus {
    CONFIRM_COMING("CC"),
    CONFIRM_NOT_COMING("CCC"),
    TO_BE_CONFIRMED("TBC");

    public static final String MESSAGE_CONSTRAINTS =
            "Value of RSVP Status can only be CC, CCC or TBC.";

    private final String status;

    RSVPStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public static RSVPStatus getRSVPStatus(String status) throws ParseException {
        for (RSVPStatus rsvpStatus : RSVPStatus.values()) {
            if (rsvpStatus.getStatus().equals(status)) {
                return rsvpStatus;
            }
        }
        throw new ParseException(MESSAGE_CONSTRAINTS);
    }
}
