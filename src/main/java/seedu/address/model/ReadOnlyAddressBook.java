package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.rsvp.Rsvp;
import seedu.address.model.vendor.Vendor;
import seedu.address.model.venue.Venue;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate events.
     */
    ObservableList<Event> getEventList();

    /**
     * Returns an unmodifiable view of the venues list.
     * This list will not contain any duplicate venues.
     */
    ObservableList<Venue> getVenueList();

    /**
     * Returns an unmodifiable view of the rsvps list.
     * This list will not contain any duplicate rsvps.
     */
    ObservableList<Rsvp> getRsvpList();

    /**
     * Returns an unmodifiable view of an event's attendee list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getEventAttendeesList();

    /**
     * Returns an unmodifiable view of the vendors list.
     * This list will not contain any duplicate venues.
     */
    ObservableList<Vendor> getVendorList();
}
