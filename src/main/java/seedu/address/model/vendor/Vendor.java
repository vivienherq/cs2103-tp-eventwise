package seedu.address.model.vendor;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.displayable.DisplayableListViewItem;

/**
 * Represents a Vendor in EventWise.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Vendor implements DisplayableListViewItem {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    /**
     * Every field must be present and not null.
     */
    public Vendor(Name name, Phone phone, Email email) {
        requireAllNonNull(name, phone, email);
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Returns true if both vendors have the same name.
     * This defines a weaker notion of equality between two vendors.
     */
    public boolean isSameVendor(Vendor otherVendor) {
        if (otherVendor == this) {
            return true;
        }

        return otherVendor != null
                && otherVendor.getName().equals(getName());
    }

    @Override
    public String getDisplayTitle() {
        return getName().toString();
    }

    @Override
    public String getDisplayFirstText() {
        return getPhone().toString();
    }

    @Override
    public String getDisplaySecondText() {
        return getEmail().toString();
    }

    @Override
    public boolean isSameItem(DisplayableListViewItem displayableListViewItem) {
        if (displayableListViewItem == this) {
            return true;
        }

        if (!(displayableListViewItem instanceof Vendor)) {
            return false;
        }

        Vendor otherVendor = (Vendor) displayableListViewItem;

        return isSameVendor(otherVendor);
    }

    /**
     * Returns true if both vendors have the same identity and data fields.
     * This defines a stronger notion of equality between two vendors.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Vendor)) {
            return false;
        }

        Vendor otherVendor = (Vendor) other;
        return name.equals(otherVendor.name)
                && phone.equals(otherVendor.phone)
                && email.equals(otherVendor.email);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .toString();
    }

}
