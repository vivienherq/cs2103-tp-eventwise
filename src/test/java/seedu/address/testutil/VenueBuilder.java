package seedu.address.testutil;

import seedu.address.model.venue.Address;
import seedu.address.model.venue.Capacity;
import seedu.address.model.venue.Name;
import seedu.address.model.venue.Venue;

/**
 * A utility class to help with building Venue objects.
 */
public class VenueBuilder {
    public static final String DEFAULT_NAME = "I3AUD";
    public static final String DEFAULT_ADDRESS = "i3 Auditorium, 21 Heng Mui Keng Terrace, "
            + "Icube Building, Singapore 119613";
    public static final String DEFAULT_CAPACITY = "500";

    private Name name;
    private Address address;
    private Capacity capacity;

    /**
     * Creates a {@code VenueBuilder} with the default details.
     */
    public VenueBuilder() {
        name = new Name(DEFAULT_NAME);
        address = new Address(DEFAULT_ADDRESS);
        capacity = new Capacity(DEFAULT_CAPACITY);
    }

    /**
     * Initializes the VenueBuilder with the data of {@code venueToCopy}.
     */
    public VenueBuilder(Venue venueToCopy) {
        name = venueToCopy.getName();
        address = venueToCopy.getAddress();
        capacity = venueToCopy.getCapacity();
    }

    /**
     * Sets the {@code Name} of the {@code Venue} that we are building.
     */
    public VenueBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }


    /**
     * Sets the {@code Address} of the {@code Venue} that we are building.
     */
    public VenueBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Capacity} of the {@code Venue} that we are building.
     */
    public VenueBuilder withCapacity(String capacity) {
        this.capacity = new Capacity(capacity);
        return this;
    }

    public Venue build() {
        return new Venue(name, address, capacity);
    }
}
