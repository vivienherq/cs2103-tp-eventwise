package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.displayable.DisplayableListViewItem;
import seedu.address.model.person.Person;
import seedu.address.model.venue.Venue;

/**
 * Represents a Event in EventWise.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event implements DisplayableListViewItem {

    // Identity fields
    private final Name name;
    private final Description description;
    private final Date date;
    private final List<Person> persons;
    private Venue venue;

    /**
     * Every field must be present and not null.
     * Assumptions:
     * 1. When an event is created no people are added to the event.
     * 2. When an event is created, the venue has not been set.
     */
    public Event(Name name, Description description, Date date) {
        requireAllNonNull(name, description, date);
        this.name = name;
        this.description = description;
        this.date = date;
        this.persons = new ArrayList<>();
    }

    /**
     * Every field must be present and not null.
     * This constructor is for creating events that allow persons and venus to be immediately part of it.
     */
    public Event(Name name, Description description, Date date, List<Person> persons, Venue venue) {
        requireAllNonNull(name, description, date);
        this.name = name;
        this.description = description;
        this.date = date;
        this.persons = persons;
        this.venue = venue;
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

    public List<Person> getPersons() {
        return persons;
    }

    public Venue getVenue() {
        return venue;
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

    @Override
    public String getDisplayTitle() {
        return getName().toString();
    }

    @Override
    public String getDisplayFirstText() {
        return getDescription().toString();
    }

    @Override
    public String getDisplaySecondText() {
        return getDate().toString();
    }

    @Override
    public boolean isSameItem(DisplayableListViewItem displayableListViewItem) {
        if (displayableListViewItem == this) {
            return true;
        }

        if (!(displayableListViewItem instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) displayableListViewItem;

        return isSameEvent(otherEvent);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description, date, persons, venue);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("description", description)
                .add("date", date)
                .toString();
    }

}
