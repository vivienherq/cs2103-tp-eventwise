package seedu.address.model.venue;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Venue's capacity in EventWise.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Capacity {


    public static final String MESSAGE_CONSTRAINTS =
            "Venue capacity should only contain numbers, and it should be at least 1 digit long";
    public static final String VALIDATION_REGEX = "\\d+";
    public final String venueCapacity;

    /**
     * Constructs a {@code Phone}.
     *
     * @param capacity A valid number.
     */
    public Capacity(String capacity) {
        requireNonNull(capacity);
        checkArgument(isValidPhone(capacity), MESSAGE_CONSTRAINTS);
        venueCapacity = capacity;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return venueCapacity;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Capacity)) {
            return false;
        }

        Capacity otherCapacity = (Capacity) other;
        return venueCapacity.equals(otherCapacity.venueCapacity);
    }

    @Override
    public int hashCode() {
        return venueCapacity.hashCode();
    }

}
