package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;
import seedu.address.model.person.Person;
import seedu.address.model.venue.Venue;

/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String name;
    private final String description;
    private final String date;
    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final JsonAdaptedVenue venue;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name,
                            @JsonProperty("email") String description,
                            @JsonProperty("date") String date,
                            @JsonProperty("persons") List<JsonAdaptedPerson> persons,
                            @JsonProperty("venue") JsonAdaptedVenue venue) {
        this.name = name;
        this.description = description;
        this.date = date;
        if (persons != null) {
            this.persons.addAll(persons);
        }
        this.venue = venue;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        name = source.getName().eventName;
        description = source.getDescription().eventDesc;
        date = source.getDate().eventDate;
        persons.addAll(source.getPersons().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
        if (source.getVenue() != null) {
            venue = new JsonAdaptedVenue(source.getVenue());
        } else {
            venue = null;
        }
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Event toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDesc(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        final List<Person> personList = new ArrayList<>();
        for (JsonAdaptedPerson person: persons) {
            personList.add(person.toModelType());
        }

        final List<Person> modelPersons = new ArrayList<>(personList);

        if (venue == null) {
            return new Event(modelName, modelDescription, modelDate, modelPersons, null);
        } else {
            final Venue modelVenue = venue.toModelType();
            return new Event(modelName, modelDescription, modelDate, modelPersons, modelVenue);
        }
    }
}
