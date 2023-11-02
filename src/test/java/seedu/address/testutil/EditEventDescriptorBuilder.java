package seedu.address.testutil;

import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.FromDate;
import seedu.address.model.event.Name;
import seedu.address.model.event.ToDate;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditEventDescriptorBuilder(Event event) {
        descriptor = new EditEventDescriptor();
        descriptor.setName(event.getName());
        descriptor.setDescription(event.getDescription());
        descriptor.setFromDate(event.getFromDate());
        descriptor.setToDate(event.getToDate());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code FromDate} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withFromDate(String fromDate) {
        descriptor.setFromDate(new FromDate(fromDate));
        return this;
    }


    /**
     * Sets the {@code ToDate} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withToDate(String toDate) {
        descriptor.setToDate(new ToDate(toDate));
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }
}
