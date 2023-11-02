package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.rsvp.Rsvp;
import seedu.address.model.vendor.Vendor;
import seedu.address.model.venue.Venue;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_EVENT = "Events list contains duplicate events(s).";
    public static final String MESSAGE_DUPLICATE_VENUE = "Venues list contains duplicate venue(s).";
    public static final String MESSAGE_DUPLICATE_VENDOR = "Vendors list contains duplicate vendor(s).";
    public static final String MESSAGE_DUPLICATE_RSVP = "RSVPs list contains duplicate venue(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedEvent> events = new ArrayList<>();
    private final List<JsonAdaptedVenue> venues = new ArrayList<>();
    private final List<JsonAdaptedVendor> vendors = new ArrayList<>();
    private final List<JsonAdaptedRsvp> rsvps = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(
            @JsonProperty("persons") List<JsonAdaptedPerson> persons,
            @JsonProperty("events") List<JsonAdaptedEvent> events,
            @JsonProperty("venues") List<JsonAdaptedVenue> venues,
            @JsonProperty("vendors") List<JsonAdaptedVendor> vendors,
            @JsonProperty("rsvps") List<JsonAdaptedRsvp> rsvps) {
        this.persons.addAll(persons);
        this.events.addAll(events);
        this.venues.addAll(venues);
        this.vendors.addAll(vendors);
        this.rsvps.addAll(rsvps);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        events.addAll(source.getEventList().stream().map(JsonAdaptedEvent::new).collect(Collectors.toList()));
        venues.addAll(source.getVenueList().stream().map(JsonAdaptedVenue::new).collect(Collectors.toList()));
        vendors.addAll(source.getVendorList().stream().map(JsonAdaptedVendor::new).collect(Collectors.toList()));
        rsvps.addAll(source.getRsvpList().stream().map(JsonAdaptedRsvp::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }

        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();
            if (addressBook.hasEvent(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            addressBook.addEvent(event);
        }

        for (JsonAdaptedVenue jsonAdaptedVenue : venues) {
            Venue venue = jsonAdaptedVenue.toModelType();
            if (addressBook.hasVenue(venue)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_VENUE);
            }
            addressBook.addVenue(venue);
        }

        for (JsonAdaptedVendor jsonAdaptedVendor : vendors) {
            Vendor vendor = jsonAdaptedVendor.toModelType();
            if (addressBook.hasVendor(vendor)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_VENDOR);
            }
            addressBook.addVendor(vendor);
        }

        for (JsonAdaptedRsvp jsonAdaptedRsvp : rsvps) {
            Rsvp rsvp = jsonAdaptedRsvp.toModelType();
            if (addressBook.hasRsvp(rsvp)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RSVP);
            }
            addressBook.addRsvp(rsvp);
        }
        return addressBook;
    }
}
