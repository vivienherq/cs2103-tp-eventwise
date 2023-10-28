package seedu.address.testutil;

import seedu.address.model.vendor.Email;
import seedu.address.model.vendor.Name;
import seedu.address.model.vendor.Phone;
import seedu.address.model.vendor.Vendor;

/**
 * A utility class to help with building Venue objects.
 */
public class VendorBuilder {
    public static final String DEFAULT_NAME = "SUN Caters";
    public static final String DEFAULT_EMAIL = "catering@sun.com";
    public static final String DEFAULT_PHONE = "64266426";

    private Name name;
    private Email email;
    private Phone phone;

    /**
     * Creates a {@code VendorBuilder} with the default details.
     */
    public VendorBuilder() {
        name = new Name(DEFAULT_NAME);
        email = new Email(DEFAULT_EMAIL);
        phone = new Phone(DEFAULT_PHONE);
    }

    /**
     * Initializes the VendorBuilder with the data of {@code venueToCopy}.
     */
    public VendorBuilder(Vendor vendorToCopy) {
        name = vendorToCopy.getName();
        email = vendorToCopy.getEmail();
        phone = vendorToCopy.getPhone();
    }

    /**
     * Sets the {@code Name} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }


    /**
     * Sets the {@code Email} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    public Vendor build() {
        return new Vendor(name, phone, email);
    }
}
