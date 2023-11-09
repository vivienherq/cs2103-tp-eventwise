package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Description;
import seedu.address.model.event.FromDate;
import seedu.address.model.event.Note;
import seedu.address.model.event.ToDate;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.rsvp.RsvpStatus;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_EVENT_NAME = "F&S&C";
    private static final String INVALID_EVENT_DESC = "F&";
    private static final String INVALID_EVENT_DATE = "40-40-2000";
    private static final String INVALID_EVENT_NOTE = "@91!";
    private static final String INVALID_RSVP_STATUS = "DC";


    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_EVENT_NAME = "FSC 2023";
    private static final String VALID_EVENT_DESC = "Freshman Social Camp 2023";
    private static final String VALID_EVENT_DATE = "12-09-2023";
    private static final String VALID_EVENT_NOTE = "This event has elements of horror";
    private static final String VALID_RSVP_STATUS = "CC";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseEventName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEventName((String) null));
    }

    @Test
    public void parseEventName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEventName(INVALID_EVENT_NAME));
    }

    @Test
    public void parseEventName_validValueWithoutWhitespace_returnsName() throws Exception {
        seedu.address.model.event.Name expectedName = new seedu.address.model.event.Name(VALID_EVENT_NAME);
        assertEquals(expectedName, ParserUtil.parseEventName(VALID_EVENT_NAME));
    }

    @Test
    public void parseEventName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_EVENT_NAME + WHITESPACE;
        seedu.address.model.event.Name expectedName = new seedu.address.model.event.Name(VALID_EVENT_NAME);
        assertEquals(expectedName, ParserUtil.parseEventName(nameWithWhitespace));
    }

    @Test
    public void parseEventDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseEventDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDescription(INVALID_EVENT_DESC));
    }

    @Test
    public void parseEventDescription_validValueWithoutWhitespace_returnsDescription() throws Exception {
        Description expectedDescription = new Description(VALID_EVENT_DESC);
        assertEquals(expectedDescription, ParserUtil.parseDescription(VALID_EVENT_DESC));
    }

    @Test
    public void parseEventDescription_validValueWithWhitespace_returnsTrimmedDescription() throws Exception {
        String descriptionWithWhitespace = WHITESPACE + VALID_EVENT_DESC + WHITESPACE;
        Description expectedDescription = new Description(VALID_EVENT_DESC);
        assertEquals(expectedDescription, ParserUtil.parseDescription(descriptionWithWhitespace));
    }

    @Test
    public void parseEventFromDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseFromDate((String) null));
    }

    @Test
    public void parseEventFromDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseFromDate(INVALID_EVENT_DATE));
    }

    @Test
    public void parseEventFromDate_validValueWithoutWhitespace_returnsFromDate() throws Exception {
        FromDate expectedFromDate = new FromDate(VALID_EVENT_DATE);
        assertEquals(expectedFromDate, ParserUtil.parseFromDate(VALID_EVENT_DATE));
    }

    @Test
    public void parseEventFromDate_validValueWithWhitespace_returnsTrimmedFromDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_EVENT_DATE + WHITESPACE;
        FromDate expectedFromDate = new FromDate(VALID_EVENT_DATE);
        assertEquals(expectedFromDate, ParserUtil.parseFromDate(dateWithWhitespace));
    }

    @Test
    public void parseEventToDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseToDate((String) null));
    }

    @Test
    public void parseEventToDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseFromDate(INVALID_EVENT_DATE));
    }

    @Test
    public void parseEventToDate_validValueWithoutWhitespace_returnsToDate() throws Exception {
        ToDate expectedToDate = new ToDate(VALID_EVENT_DATE);
        assertEquals(expectedToDate, ParserUtil.parseToDate(VALID_EVENT_DATE));
    }

    @Test
    public void parseEventToDate_validValueWithWhitespace_returnsTrimmedToDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_EVENT_DATE + WHITESPACE;
        ToDate expectedToDate = new ToDate(VALID_EVENT_DATE);
        assertEquals(expectedToDate, ParserUtil.parseToDate(dateWithWhitespace));
    }

    @Test
    public void parseEventNote_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNote(INVALID_EVENT_NOTE));
    }

    @Test
    public void parseEventNote_validValueWithoutWhitespace_returnsNote() throws Exception {
        Note expectedNote = new Note(VALID_EVENT_NOTE);
        assertEquals(expectedNote, ParserUtil.parseNote(VALID_EVENT_NOTE));
    }

    @Test
    public void parseEventNote_validValueWithWhitespace_returnsTrimmedNote() throws Exception {
        String noteWithWhitespace = WHITESPACE + VALID_EVENT_NOTE + WHITESPACE;
        Note expectedNote = new Note(VALID_EVENT_NOTE);
        assertEquals(expectedNote, ParserUtil.parseNote(noteWithWhitespace));
    }

    @Test
    public void parseRsvpStatus_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRsvpStatus((String) null));
    }

    @Test
    public void parseRsvpStatus_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRsvpStatus(INVALID_RSVP_STATUS));
    }

    @Test
    public void parseRsvpStatus_validValueWithoutWhitespace_returnsRsvpStatus() throws Exception {
        assertEquals(RsvpStatus.CC, ParserUtil.parseRsvpStatus(VALID_RSVP_STATUS));
    }

    @Test
    public void parseRsvpStatus_validValueWithWhitespace_returnsRsvpStatus() throws Exception {
        String rsvpStatusWithWhitespace = WHITESPACE + VALID_RSVP_STATUS + WHITESPACE;
        assertEquals(RsvpStatus.CC, ParserUtil.parseRsvpStatus(rsvpStatusWithWhitespace));
    }
}
