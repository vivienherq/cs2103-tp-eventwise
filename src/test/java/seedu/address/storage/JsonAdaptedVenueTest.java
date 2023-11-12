package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedVenue.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVenues.LT27;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.venue.Address;
import seedu.address.model.venue.Capacity;
import seedu.address.model.venue.Name;

public class JsonAdaptedVenueTest {
    private static final String INVALID_NAME = "H@CKERTH0N";
    private static final String INVALID_ADDRESS = "";
    private static final String INVALID_CAPACITY = "A";


    private static final String VALID_NAME = LT27.getName().toString();
    private static final String VALID_ADDRESS = LT27.getAddress().toString();
    private static final String VALID_CAPACITY = LT27.getCapacity().toString();

    @Test
    public void toModelType_validVendorDetails_returnsVendor() throws Exception {
        JsonAdaptedVenue venue = new JsonAdaptedVenue(LT27);
        assertEquals(LT27, venue.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedVenue venue = new JsonAdaptedVenue(INVALID_NAME, VALID_ADDRESS, VALID_CAPACITY);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, venue::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedVenue venue = new JsonAdaptedVenue(null, VALID_ADDRESS, VALID_CAPACITY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, venue::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedVenue venue = new JsonAdaptedVenue(VALID_NAME, INVALID_ADDRESS, VALID_CAPACITY);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, venue::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedVenue venue = new JsonAdaptedVenue(VALID_NAME, null, VALID_CAPACITY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, venue::toModelType);
    }

    @Test
    public void toModelType_invalidCapacity_throwsIllegalValueException() {
        JsonAdaptedVenue venue = new JsonAdaptedVenue(VALID_NAME, VALID_ADDRESS, INVALID_CAPACITY);
        String expectedMessage = Capacity.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, venue::toModelType);
    }

    @Test
    public void toModelType_nullCapacity_throwsIllegalValueException() {
        JsonAdaptedVenue venue = new JsonAdaptedVenue(VALID_NAME, VALID_ADDRESS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Capacity.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, venue::toModelType);
    }
}
