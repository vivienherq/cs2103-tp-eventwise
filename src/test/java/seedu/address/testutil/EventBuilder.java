package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.FromDate;
import seedu.address.model.event.Name;
import seedu.address.model.event.Note;
import seedu.address.model.event.ToDate;
import seedu.address.model.person.Person;
import seedu.address.model.venue.Venue;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "Freshman Social Camp 2023";
    public static final String DEFAULT_DESCRIPTION = "FSC 2023";
    public static final String DEFAULT_FROM_DATE = "01/12/2024";
    public static final String DEFAULT_TO_DATE = "02/12/2024";

    public static final String DEFAULT_NOTE = "Food and drinks are provided";


    private Name name;
    private Description description;
    private FromDate fromDate;
    private ToDate toDate;
    private Note note;
    private List<Person> persons;
    private Venue venue;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        name = new Name(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
        fromDate = new FromDate(DEFAULT_FROM_DATE);
        toDate = new ToDate(DEFAULT_TO_DATE);
        note = new Note(DEFAULT_NOTE);
        persons = new ArrayList<>();
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        description = eventToCopy.getDescription();
        fromDate = eventToCopy.getFromDate();
        toDate = eventToCopy.getToDate();
        note = eventToCopy.getNote();
        persons = eventToCopy.getPersons();
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
     * Sets the {@code FromDate} of the {@code Event} that we are building.
     */
    public EventBuilder withFromDate(String date) {
        this.fromDate = new FromDate(date);
        return this;
    }

    /**
     * Sets the {@code ToDate} of the {@code Event} that we are building.
     */
    public EventBuilder withToDate(String date) {
        this.toDate = new ToDate(date);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Event} that we are building.
     */
    public EventBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Sets the {@code persons} of the {@code Event} that we are building.
     */
    public EventBuilder withPersons(List<Person> persons) {
        this.persons = persons;
        return this;
    }

    /**
     * Sets the {@code venue} of the {@code Event} that we are building.
     */
    public EventBuilder withVenue(Venue venue) {
        this.venue = venue;
        return this;
    }

    public Event build() {
        return new Event(name, description, fromDate, toDate, note, persons, venue);
    }
}
