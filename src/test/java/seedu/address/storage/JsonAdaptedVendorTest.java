package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedVendor.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVendors.SUN;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.vendor.Email;
import seedu.address.model.vendor.Name;
import seedu.address.model.vendor.Phone;

public class JsonAdaptedVendorTest {
    private static final String INVALID_NAME = "H@CKERTH0N";
    private static final String INVALID_PHONE = "9abC12334";
    private static final String INVALID_EMAIL = "SUNSUN";


    private static final String VALID_NAME = SUN.getName().toString();
    private static final String VALID_PHONE = SUN.getPhone().toString();
    private static final String VALID_EMAIL = SUN.getEmail().toString();

    @Test
    public void toModelType_validVendorDetails_returnsVendor() throws Exception {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(SUN);
        assertEquals(SUN, vendor.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(INVALID_NAME, VALID_PHONE, VALID_EMAIL);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(null, VALID_PHONE, VALID_EMAIL);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(VALID_NAME, INVALID_PHONE, VALID_EMAIL);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(VALID_NAME, null, VALID_EMAIL);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(VALID_NAME, VALID_PHONE, INVALID_EMAIL);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(VALID_NAME, VALID_PHONE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }
}
