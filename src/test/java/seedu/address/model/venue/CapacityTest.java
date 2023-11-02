package seedu.address.model.venue;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CapacityTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Capacity(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidCapacity = "";
        assertThrows(IllegalArgumentException.class, () -> new Capacity(invalidCapacity));
    }
}
