package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.venue.Address;
import seedu.address.model.venue.Capacity;
import seedu.address.model.venue.Name;
import seedu.address.model.venue.Venue;

/**
 * Jackson-friendly version of {@link Venue}.
 */
class JsonAdaptedVenue {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Venue's %s field is missing!";

    private final String name;
    private final String address;
    private final String capacity;

    /**
     * Constructs a {@code JsonAdaptedVenue} with the given venue details.
     */
    @JsonCreator
    public JsonAdaptedVenue(@JsonProperty("name") String name,
                            @JsonProperty("address") String address,
                            @JsonProperty("capacity") String capacity) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    /**
     * Converts a given {@code Venue} into this class for Jackson use.
     */
    public JsonAdaptedVenue(Venue source) {
        name = source.getName().venueName;
        address = source.getAddress().venueAddress;
        capacity = source.getCapacity().venueCapacity;
    }

    /**
     * Converts this Jackson-friendly adapted venue object into the model's {@code Venue} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted venue.
     */
    public Venue toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (address == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (capacity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Capacity.class.getSimpleName()));
        }
        if (!Capacity.isValidCapacity(capacity)) {
            throw new IllegalValueException(Capacity.MESSAGE_CONSTRAINTS);
        }
        final Capacity modelCapacity = new Capacity(capacity);
        return new Venue(modelName, modelAddress, modelCapacity);
    }
}
