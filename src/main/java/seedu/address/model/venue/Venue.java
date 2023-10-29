package seedu.address.model.venue;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.displayable.DisplayableListViewItem;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Venue implements DisplayableListViewItem {

    // Identity fields
    private final Name name;

    // Data fields
    private final Address address;
    private final Capacity capacity;

    /**
     * Every field must be present and not null.
     */
    public Venue(Name name, Address address, Capacity capacity) {
        requireAllNonNull(name, address, capacity);
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    /**
     * Returns true if both venues have the same name.
     * This defines a weaker notion of equality between two venues.
     */
    public boolean isSameVenue(Venue otherVenue) {
        if (otherVenue == this) {
            return true;
        }

        return otherVenue != null
                && otherVenue.getName().equals(getName());
    }

    @Override
    public String getDisplayTitle() {
        return getName().toString();
    }

    @Override
    public String getDisplayFirstText() {
        return getAddress().toString();
    }

    @Override
    public String getDisplaySecondText() {
        return String.format("Capacity: %s", getCapacity());
    }

    @Override
    public boolean isSameItem(DisplayableListViewItem displayableListViewItem) {
        if (displayableListViewItem == this) {
            return true;
        }

        if (!(displayableListViewItem instanceof Venue)) {
            return false;
        }

        Venue otherVenue = (Venue) displayableListViewItem;

        return isSameVenue(otherVenue);
    }

    /**
     * Returns true if both venues have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Venue)) {
            return false;
        }

        Venue otherVenue = (Venue) other;
        return name.equals(otherVenue.name)
                && address.equals(otherVenue.address)
                && capacity.equals(otherVenue.capacity);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, address, capacity);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("address", address)
                .add("capacity", capacity)
                .toString();
    }

}
