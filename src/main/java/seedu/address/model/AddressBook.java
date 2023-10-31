package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.displayable.DisplayableListViewItem;
import seedu.address.model.displayable.UniqueDisplayableItemList;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.rsvp.Rsvp;
import seedu.address.model.rsvp.UniqueRsvpList;
import seedu.address.model.vendor.UniqueVendorList;
import seedu.address.model.vendor.Vendor;
import seedu.address.model.venue.UniqueVenueList;
import seedu.address.model.venue.Venue;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueRsvpList rsvps;
    private final UniquePersonList persons;
    private final UniqueEventList events;
    private final UniqueVenueList venues;
    private final UniquePersonList eventAttendees;
    private final UniqueVendorList eventVendors;
    private final UniqueDisplayableItemList displayableItems;
    private final UniqueVendorList vendors;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        events = new UniqueEventList();
        venues = new UniqueVenueList();
        eventAttendees = new UniquePersonList();
        eventVendors = new UniqueVendorList();
        displayableItems = new UniqueDisplayableItemList();
        rsvps = new UniqueRsvpList();
        vendors = new UniqueVendorList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the event list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Replaces the contents of an event's attendee list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setEventAttendees(List<Person> persons) {
        this.eventAttendees.setPersons(persons);
    }

    /**
     * Replaces the contents of the venue list with {@code venues}.
     * {@code venues} must not contain duplicate venues.
     */
    public void setVenues(List<Venue> venues) {
        this.venues.setVenues(venues);
    }

    /**
     * Replaces the contents of the displayable listview items list with {@code items}.
     * {@code items} must not contain duplicate displayable list view items.
     */
    public void setDisplayableItems(List<? extends DisplayableListViewItem> items) {
        this.displayableItems.setDisplayableItems(items);
    }

    /**
     * Replaces the contents of the rsvp list with {@code rsvps}.
     * {@code rsvps} must not contain duplicate rsvps.
     */
    public void setRsvps(List<Rsvp> rsvps) {
        this.rsvps.setRsvps(rsvps);
    }

    /**
     * Replaces the contents of the vendor list with {@code vendors}.
     * {@code vendors} must not contain duplicate vendors.
     */
    public void setVendors(List<Vendor> vendors) {
        this.vendors.setVendors(vendors);
    }

    /**
     * Replaces the contents of an event's vendor list with {@code vendors}.
     * {@code vendors} must not contain duplicate vendors.
     */
    public void setEventVendors(List<Vendor> vendors) {
        this.eventVendors.setVendors(vendors);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setEvents(newData.getEventList());
        setVenues(newData.getVenueList());
        setEventAttendees(new ArrayList<>());
        setEventVendors(new ArrayList<>());
        setDisplayableItems(new ArrayList<>());
        setRsvps(newData.getRsvpList());
    }

    /**
     * Resets the existing events data of this {@code AddressBook}.
     */
    public void resetEvents() {
        setEvents(new ArrayList<>());
    }

    /**
     * Resets the existing guests data of this {@code AddressBook}.
     */
    public void resetGuests() {
        setPersons(new ArrayList<>());
    }

    /**
     * Resets the existing venues data of this {@code AddressBook}.
     */
    public void resetVenues() {
        setVenues(new ArrayList<>());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// event-level operations

    /**
     * Returns true if an existing event similar to {@code event} exists in the event list.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Adds an event to the event list.
     * The event must not already exist in the event list.
     */
    public void addEvent(Event e) {
        events.add(e);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the event list.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the event list.
     */
    public void setEvent(Event target, Event editedEvent) {
        requireNonNull(editedEvent);

        events.setEvent(target, editedEvent);
    }

    /**
     * Removes {@code key} from {@code events}.
     * {@code key} must exist in the event list.
     */
    public void removeEvent(Event key) {
        events.remove(key);
    }

    //// venue-level operations

    /**
     * Returns true if an existing venue similar to {@code venue} exists in the venue list.
     */
    public boolean hasVenue(Venue venue) {
        requireNonNull(venue);
        return venues.contains(venue);
    }

    /**
     * Adds a venue to the address book.
     * The venue must not already exist in the address book.
     */
    public void addVenue(Venue venue) {
        venues.add(venue);
    }

    /**
     * Replaces the given venue {@code target} in the list with {@code editedVenue}.
     * {@code target} must exist in the address book.
     * The venue identity of {@code editedVenue} must not be the same as another existing venue in the address book.
     */
    public void setVenue(Venue target, Venue editedVenue) {
        requireNonNull(editedVenue);

        venues.setVenue(target, editedVenue);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeVenue(Venue key) {
        venues.remove(key);
    }

    /**
     * Adds a displayable item to the address book.
     * The displayable item must not already exist in the address book.
     */
    public void addDisplayableItem(DisplayableListViewItem item) {
        displayableItems.add(item);
    }

    /**
     * Replaces the given displayable list view item {@code target} in the list with {@code editedItem}.
     * {@code target} must exist in the address book.
     * The displayable list view identity of {@code editedItem} must not be the same as another existing
     * displayable list view item in the address book.
     */
    public void setDisplayableItem(DisplayableListViewItem target, DisplayableListViewItem editedItem) {
        requireNonNull(editedItem);

        displayableItems.setDisplayableItem(target, editedItem);
    }

    /**
     * Returns true if an RSVP with the same identity as {@code rsvp} exists in the address book.
     */
    public boolean hasRsvp(Rsvp rsvp) {
        requireNonNull(rsvp);
        return rsvps.contains(rsvp);
    }

    /**
     * Adds an RSVP to the address book.
     * The RSVP must not already exist in the address book.
     */
    public void addRsvp(Rsvp rsvp) {
        rsvps.add(rsvp);
    }

    //// vendor-level operations

    /**
     * Returns true if an existing vendor similar to {@code vendor} exists in the vendor list.
     */
    public boolean hasVendor(Vendor vendor) {
        requireNonNull(vendor);
        return vendors.contains(vendor);
    }

    /**
     * Adds a vendor to EventWise.
     * The vendor must not already exist in EventWise.
     */
    public void addVendor(Vendor vendor) {
        vendors.add(vendor);
    }

    /**
     * Replaces the given vendor {@code target} in the list with {@code editedVendor}.
     * {@code target} must exist in EventWise.
     * The vendor identity of {@code editedVendor} must not be the same as another existing vendor in EventWise.
     */
    public void setVendor(Vendor target, Vendor editedVendor) {
        requireNonNull(editedVendor);

        vendors.setVendor(target, editedVendor);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeDisplayableItem(DisplayableListViewItem key) {
        displayableItems.remove(key);
    }

    public void removeVendor(Vendor key) {
        vendors.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Venue> getVenueList() {
        return venues.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Rsvp> getRsvpList() {
        return rsvps.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Person> getEventAttendeesList() {
        return eventAttendees.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Vendor> getEventVendorsList() {
        return eventVendors.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<DisplayableListViewItem> getDisplayableItemList() {
        return displayableItems.asUnmodifiableObservableList();
    }

    public ObservableList<Vendor> getVendorList() {
        return vendors.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }

}
