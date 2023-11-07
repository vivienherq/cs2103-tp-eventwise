package seedu.address.testutil;

import seedu.address.logic.commands.EditVenueCommand.EditVenueDescriptor;
import seedu.address.model.venue.Address;
import seedu.address.model.venue.Capacity;
import seedu.address.model.venue.Name;
import seedu.address.model.venue.Venue;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditVenueDescriptorBuilder {

    private EditVenueDescriptor descriptor;

    public EditVenueDescriptorBuilder() {
        descriptor = new EditVenueDescriptor();
    }

    public EditVenueDescriptorBuilder(EditVenueDescriptor descriptor) {
        this.descriptor = new EditVenueDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditVenueDescriptor} with fields containing {@code venue}'s details
     */
    public EditVenueDescriptorBuilder(Venue venue) {
        descriptor = new EditVenueDescriptor();
        descriptor.setName(venue.getName());
        descriptor.setAddress(venue.getAddress());
        descriptor.setCapacity(venue.getCapacity());
    }

    /**
     * Sets the {@code Name} of the {@code EditVenueDescriptor} that we are building.
     */
    public EditVenueDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditVenueDescriptor} that we are building.
     */
    public EditVenueDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Capacity} of the {@code EditVenueDescriptor} that we are building.
     */
    public EditVenueDescriptorBuilder withCapacity(String capacity) {
        descriptor.setCapacity(new Capacity(capacity));
        return this;
    }

    public EditVenueDescriptor build() {
        return descriptor;
    }
}
