package seedu.address.testutil;

import seedu.address.logic.commands.EditVendorCommand.EditVendorDescriptor;
import seedu.address.model.vendor.Email;
import seedu.address.model.vendor.Name;
import seedu.address.model.vendor.Phone;
import seedu.address.model.vendor.Vendor;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditVendorDescriptorBuilder {

    private EditVendorDescriptor descriptor;

    public EditVendorDescriptorBuilder() {
        descriptor = new EditVendorDescriptor();
    }

    public EditVendorDescriptorBuilder(EditVendorDescriptor descriptor) {
        this.descriptor = new EditVendorDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditVendorDescriptor} with fields containing {@code venue}'s details
     */
    public EditVendorDescriptorBuilder(Vendor vendor) {
        descriptor = new EditVendorDescriptor();
        descriptor.setName(vendor.getName());
        descriptor.setPhone(vendor.getPhone());
        descriptor.setEmail(vendor.getEmail());
    }

    /**
     * Sets the {@code Name} of the {@code EditVendorDescriptor} that we are building.
     */
    public EditVendorDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditVendorDescriptor} that we are building.
     */
    public EditVendorDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditVendorDescriptor} that we are building.
     */
    public EditVendorDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    public EditVendorDescriptor build() {
        return descriptor;
    }
}
