package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a Event's date in EventWise.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class ToDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Event date should only be in DD-MM-YYYY format and should be either today's date or a future date. ";

    // Regex solution below adapted by
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
     * Constructs a {@code ToDate}.
     *
     * @param date A valid date.
     */
    public ToDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        eventDate = date;
    }

    /**
     * Returns true if a given date input is of a valid format.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if given date is a valid date.
     */
    public boolean isNotPast() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date currentDate = new Date();
        String currentDateString = dateFormat.format(currentDate);

        try {
            Date inputDate = dateFormat.parse(eventDate);
            Date currentDateParsed = dateFormat.parse(currentDateString);
            return !inputDate.before(currentDateParsed);
        } catch (ParseException e) {
            return false;
        }
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
        if (!(other instanceof ToDate)) {
            return false;
        }

        ToDate otherToDate = (ToDate) other;
        return eventDate.equals(otherToDate.eventDate);
    }

    @Override
    public int hashCode() {
        return eventDate.hashCode();
    }

    /**
     * Returns true if given date is a after the from date.
     */
    public boolean isAfter(FromDate fromDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date toDateObj = dateFormat.parse(eventDate);
            Date fromDateObj = dateFormat.parse(fromDate.eventDate);
            return toDateObj.after(fromDateObj) || toDateObj.equals(fromDateObj);
        } catch (ParseException e) {
            return false;
        }
    }
}
