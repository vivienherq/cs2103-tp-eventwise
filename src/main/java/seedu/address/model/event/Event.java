package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Event in EventWise.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    // Identity fields
    private final Name name;
    private final Description description;
    private final Date date;

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, Description description, Date date) {
        requireAllNonNull(name, description, date);
        this.name = name;
        this.description = description;
        this.date = date;
    }


    public Name getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Returns true if both events have the same event name.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.name.equals(name);
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return name.equals(otherEvent.name)
                && description.equals(otherEvent.description)
                && date.equals(otherEvent.date);
    }

    //    @Override
    //    public int hashCode() {
    //        // use this method for custom fields hashing instead of implementing your own
    //        return Objects.hash(name, description, date);
    //    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("description", description)
                .add("date", date)
                .toString();
    }
}
