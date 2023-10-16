package seedu.address.testutil;

import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "Freshman Social Camp 2023";
    public static final String DEFAULT_DESCRIPTION = "FSC 2023";
    public static final String DEFAULT_DATE = "01/09/2023";

    private Name name;
    private Description description;
    private Date date;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        name = new Name(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
        date = new Date(DEFAULT_DATE);
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        description = eventToCopy.getDescription();
        date = eventToCopy.getDate();
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Event} that we are building.
     */
    public EventBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    public Event build() {
        return new Event(name, description, date);
    }
}
