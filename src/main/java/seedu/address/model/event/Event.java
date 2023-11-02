package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.displayable.DisplayableListViewItem;
import seedu.address.model.person.Person;
import seedu.address.model.vendor.Vendor;
import seedu.address.model.venue.Venue;

/**
 * Represents a Event in EventWise.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event implements DisplayableListViewItem {

    // Identity fields
    private final Name name;
    private final Description description;
    private final FromDate fromDate;
    private final ToDate toDate;
    private final Note note;
    private List<Person> persons;
    private List<Vendor> vendors;
    private Venue venue;


    /**
     * Every field must be present and not null.
     * Assumptions:
     * 1. When an event is created no people are added to the event.
     * 2. When an event is created no vendors are added to the event.
     * 3. When an event is created, the venue has not been set.
     */
    public Event(Name name, Description description, FromDate fromDate, ToDate toDate, Note note) {
        requireAllNonNull(name, description, fromDate, toDate);
        this.name = name;
        this.description = description;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.note = note;
        this.persons = new ArrayList<>();
        this.vendors = new ArrayList<>();
    }

    /**
     * Every field must be present and not null.
     * This constructor is for creating events that allow persons and venues to be immediately part of it.
     */
    public Event(Name name, Description description, FromDate fromDate, ToDate toDate,
                 Note note, List<Person> persons, List<Vendor> vendors, Venue venue) {
        requireAllNonNull(name, description, fromDate, toDate);
        this.name = name;
        this.description = description;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.note = note;
        this.persons = persons;
        this.venue = venue;
        this.vendors = vendors;
    }

    public Name getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public FromDate getFromDate() {
        return fromDate;
    }
    public ToDate getToDate() {
        return toDate;
    }


    public Note getNote() {
        return note;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Vendor> getVendors() {
        return vendors;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
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
                && fromDate.equals(otherEvent.fromDate);
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
        return getFromDate().toString();
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
        return Objects.hash(name, description, fromDate, toDate, note, persons, venue);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("description", description)
                .add("fromDate", fromDate)
                .add("toDate", toDate)
                .add("note", note)
                .toString();
    }

}
