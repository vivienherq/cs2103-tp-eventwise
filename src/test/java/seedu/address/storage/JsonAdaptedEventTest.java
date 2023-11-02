package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.ACADEMIC;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Description;
import seedu.address.model.event.FromDate;
import seedu.address.model.event.Name;

public class JsonAdaptedEventTest {
    private static final String INVALID_NAME = "H@CKERTH0N";
    private static final String INVALID_DESCRIPTION = "+capture_%flag";
    private static final String INVALID_FROM_DATE = "31 Feb 2023";
    private static final String INVALID_TO_DATE = "31 Feb 2023";
    private static final String INVALID_NOTE = "+capture_%flag";

    private static final String VALID_NAME = ACADEMIC.getName().toString();
    private static final String VALID_DESCRIPTION = ACADEMIC.getDescription().toString();
    private static final String VALID_FROM_DATE = ACADEMIC.getFromDate().toString();
    private static final String VALID_TO_DATE = ACADEMIC.getToDate().toString();

    private static final String VALID_NOTE = ACADEMIC.getNote().toString();


    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(ACADEMIC);
        assertEquals(ACADEMIC, event.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(INVALID_NAME, VALID_DESCRIPTION, VALID_FROM_DATE,
                        VALID_TO_DATE, VALID_NOTE, new ArrayList<>(), new ArrayList<>(), null);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(null, VALID_DESCRIPTION, VALID_FROM_DATE,
                        VALID_TO_DATE, VALID_NOTE, new ArrayList<>(), new ArrayList<>(), null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_NAME, INVALID_DESCRIPTION, VALID_FROM_DATE,
                        VALID_TO_DATE, VALID_NOTE, new ArrayList<>(), new ArrayList<>(), null);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_NAME, null, VALID_FROM_DATE,
                        VALID_TO_DATE, VALID_NOTE, new ArrayList<>(), new ArrayList<>(), null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_NAME, VALID_DESCRIPTION, INVALID_FROM_DATE,
                        VALID_TO_DATE, VALID_NOTE, new ArrayList<>(), new ArrayList<>(), null);
        String expectedMessage = FromDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_NAME, VALID_DESCRIPTION, null,
                        VALID_TO_DATE, VALID_NOTE, new ArrayList<>(),
                        new ArrayList<>(), null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, FromDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }
}
