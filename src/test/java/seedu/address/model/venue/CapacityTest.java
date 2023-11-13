package seedu.address.model.venue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CapacityTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Capacity(null));
    }

    @Test
    public void constructor_invalidCapacity_throwsIllegalArgumentException() {
        String invalidCapacity = "";
        assertThrows(IllegalArgumentException.class, () -> new Capacity(invalidCapacity));
    }

    @Test
    public void isHashcodeValid() {
        Capacity capacity = new Capacity("200");
        Capacity sameCapacity = new Capacity("200");
        assertEquals(capacity.hashCode(), sameCapacity.hashCode());
    }
}
