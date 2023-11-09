package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.displayable.DisplayableListViewItem;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.rsvp.Rsvp;
import seedu.address.model.rsvp.RsvpStatus;
import seedu.address.model.vendor.Vendor;
import seedu.address.model.venue.Venue;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;
    Predicate<Venue> PREDICATE_SHOW_ALL_VENUES = unused -> true;
    Predicate<Vendor> PREDICATE_SHOW_ALL_VENDORS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Gets a person from the address book based on the {@code index} specified.
     */
    Person getPerson(Index index) throws CommandException;

    /**
     * Gets a list of person objects from the address book based on the {@code indices} specified.
     */
    List<Person> getPersons(Set<Index> indices) throws CommandException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    // =========== EventWise ================================================================================

    // Events
    /**
     * Returns true if an event with the same identity as {@code event} exists in the event list.
     */
    boolean hasEvent(Event event);

    /**
     * Adds the given event.
     * {@code event} must not already exist in the event list.
     */
    void addEvent(Event event);

    /**
     * Deletes the given event.
     * The event must exist in the event list
     */
    void deleteEvent(Event target);

    /**
     * Replaces the given event {@code target} with {@code editedEvent}.
     * {@code target} must exist in the event list.
     */
    void setEvent(Event target, Event editedEvent);

    /**
     * Edits the given event {@code eventToEdit} details with {@code personsToAdd} and {@code venuesToAdd}.
     */
    Event createEditedEvent(Event eventToEdit, List<Person> personsToAdd, List<Vendor> vendorsToAdd, Venue venueToAdd);

    void resetEvents();
    void resetGuests();
    void resetVenues();
    void resetVendors();


    /** Returns an unmodifiable view of the filtered events list */
    ObservableList<Event> getFilteredEventsList();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    /**
     * Sets the current {@code event} information to be displayed.
     */
    void setEventToView(Event event);

    /**
     * Gets the current {@code event} information to be displayed.
     */
    Event getEventToView();

    // Venues

    /**
     * Returns true if a venue with the same identity as {@code venue} exists in the event list.
     */
    boolean hasVenue(Venue venue);

    /**
     * Adds the given venue.
     * {@code venue} must not already exist in the venue list.
     */
    void addVenue(Venue venue);

    /**
     * Gets a venue from the address book based on the {@code index} specified.
     */
    Venue getVenue(Index index) throws CommandException;

    /**
     * Deletes the given venue.
     * The venue must exist in the venue list
     */
    void deleteVenue(Venue target);

    /**
     * Replaces the given venue {@code target} with {@code editedVenue}.
     * {@code target} must exist in the venue list.
     */
    void setVenue(Venue target, Venue editedVenue);



    /**
     * Adds the given rsvp.
     * {@code rsvp} must not already exist in the rsvp list.
     */
    void addRsvp(Rsvp rsvp);

    /**
     * Deletes the given Rsvp.
     * The Rsvp must exist in the rsvp list.
     */
    void deleteRsvp(Rsvp rsvp);

    /**
     * Sets {@code rsvps} as the updated rsvp list in address book
     */
    void setRsvps(List<Rsvp> rsvps);

    /**
     * Finds a Rsvp given an {@code event} and {@code person}.
     */
    Rsvp findRsvp(Event event, Person person);

    Rsvp createRsvp(Index eventIndex, Index personIndex, RsvpStatus rsvpStatus);

    boolean isValidRsvp(Rsvp rsvp);

    /** Returns an unmodifiable view of the filtered venues list */
    ObservableList<Venue> getFilteredVenuesList();

    /**
     * Updates the filter of the filtered venue list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredVenueList(Predicate<Venue> predicate);

    // Guests

    /** Returns an unmodifiable view of the filtered event attendees list */
    ObservableList<Person> getFilteredEventAttendeesList();

    /** Returns an unmodifiable view of the filtered rsvps list */
    ObservableList<Rsvp> getFilteredRsvpList();

    // Vendors

    /**
     * Returns true if a vendor with the same identity as {@code vendor} exists in the event list.
     */
    boolean hasVendor(Vendor vendor);

    /**
     * Adds the given vendor.
     * {@code vendor} must not already exist in the vendor list.
     */
    void addVendor(Vendor vendor);

    /**
     * Deletes the given vendor.
     * The vendor must exist in the vendor list
     */
    void deleteVendor(Vendor target);

    /**
     * Replaces the given vendor {@code target} with {@code editedVendor}.
     * {@code target} must exist in the venue list.
     */
    void setVendor(Vendor target, Vendor editedVendor);

    /** Returns an unmodifiable view of the filtered vendors list */
    ObservableList<Vendor> getFilteredVendorList();

    /** Returns an unmodifiable view of the filtered event vendors list */
    ObservableList<Vendor> getFilteredEventVendorsList();

    /**
     * Updates the filter of the filtered vendors list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredVendorList(Predicate<Vendor> predicate);

    /**
     * Gets a vendor from the address book based on the {@code index} specified.
     */
    Vendor getVendor(Index index) throws CommandException;

    /**
     * Gets a list of vendor objects from the address book based on the {@code indices} specified.
     */
    List<Vendor> getVendors(Set<Index> indices) throws CommandException;

    // Generic
    /** Returns an unmodifiable view of the filtered displayable items list */
    ObservableList<DisplayableListViewItem> getFilteredDisplayableItemList();

    ObservableList<Rsvp> getFilteredEventRsvpList();

    void updateFilteredEventRsvpList(Predicate<Rsvp> predicate);
}
