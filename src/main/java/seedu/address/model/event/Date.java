package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Event's date in EventWise.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Event date should only be in YYYY-MM-DD format";

    // Regex referenced from
    // https://stackoverflow.com/questions/15491894/
    // regex-to-validate-date-formats-dd-mm-yyyy-dd-mm-yyyy-dd-mm-yyyy-dd-mmm-yyyy
    public static final String VALIDATION_REGEX =
            "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)"
            + "(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)"
            + "0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|"
            + "(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)"
            + "(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    public final String eventDate;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        eventDate = date;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return eventDate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Date)) {
            return false;
        }

        Date otherName = (Date) other;
        return eventDate.equals(otherName.eventDate);
    }

    @Override
    public int hashCode() {
        return eventDate.hashCode();
    }
}
