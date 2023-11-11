package seedu.address.model.rsvp;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.ACADEMIC;
import static seedu.address.testutil.TypicalEvents.BASKETBALL;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

public class RsvpContainsEventPredicateTest {

    private final RsvpContainsEventPredicate predicate = new RsvpContainsEventPredicate(ACADEMIC);
    private final Rsvp rsvp = new Rsvp(ACADEMIC, ALICE, RsvpStatus.CC);
    private final Rsvp diffRsvp = new Rsvp(BASKETBALL, ALICE, RsvpStatus.CC);

    @Test
    public void test_sameEvent_returnTrue() {
        assertTrue(predicate.test(rsvp));
    }

    @Test
    public void test_differentEvent_returnFalse() {
        assertFalse(predicate.test(diffRsvp));
    }

    @Test
    public void test_invalidEvent_returnFalse() {
        assertThrows(NullPointerException.class, () -> predicate.test(null));
    }
}

