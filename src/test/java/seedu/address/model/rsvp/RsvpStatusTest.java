package seedu.address.model.rsvp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class RsvpStatusTest {

    @Test
    public void validRsvpStatus() throws ParseException {
        assertEquals(RsvpStatus.CC, RsvpStatus.getRsvpStatus("CC"));
        assertEquals(RsvpStatus.CCC, RsvpStatus.getRsvpStatus("CCC"));
        assertEquals(RsvpStatus.TBC, RsvpStatus.getRsvpStatus("TBC"));
    }

    @Test
    public void invalidRsvpStatus() {
        assertThrows(ParseException.class, () -> RsvpStatus.getRsvpStatus("invalid"));
    }

    @Test
    void getStatus_returnsCorrectStatusString() {
        assertEquals("Confirm Coming", RsvpStatus.CC.getStatus());
        assertEquals("Confirm Not Coming", RsvpStatus.CCC.getStatus());
        assertEquals("To Be Confirmed", RsvpStatus.TBC.getStatus());
    }
}
