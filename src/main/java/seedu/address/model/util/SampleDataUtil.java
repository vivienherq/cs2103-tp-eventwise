package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.FromDate;
import seedu.address.model.event.Note;
import seedu.address.model.event.ToDate;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.vendor.Vendor;
import seedu.address.model.venue.Address;
import seedu.address.model.venue.Capacity;
import seedu.address.model.venue.Venue;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"))
        };
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(new seedu.address.model.event.Name("LifeHack"),
                new Description("Show off your coding prowess in a friendly contest"),
                new FromDate("24-12-2023"), new ToDate("25-12-2023"), null),
            new Event(new seedu.address.model.event.Name("Math Olympiad"),
                new Description("Test your math skills in a challenging competition"),
                new FromDate("03-01-2024"), new ToDate("04-01-2024"), null),
            new Event(new seedu.address.model.event.Name("Career Fair"),
                    new Description("Get insights on different career paths"),
                    new FromDate("08-01-2024"), new ToDate("09-01-2024"), new Note("Its in 2024")),
            new Event(new seedu.address.model.event.Name("Supernova"),
                    new Description("All night of partying with a lit DJ lineup"),
                    new FromDate("03-02-2024"), new ToDate("04-02-2024"), null),
        };
    }

    public static Vendor[] getSampleVendors() {
        return new Vendor[] {
                new Vendor(
                        new seedu.address.model.vendor.Name("Catering"),
                        new seedu.address.model.vendor.Phone("64646262"),
                        new seedu.address.model.vendor.Email("catering@gmail.com")
                ),
                new Vendor(
                        new seedu.address.model.vendor.Name("AV Equipment"),
                        new seedu.address.model.vendor.Phone("61234567"),
                        new seedu.address.model.vendor.Email("soundcheck@gmail.com")
                ),
        };
    }

    public static Venue[] getSampleVenues() {
        return new Venue[] {
                new Venue(new seedu.address.model.venue.Name("MPSH 1"),
                        new Address("Sports Drive"),
                        new Capacity("400")),
                new Venue(new seedu.address.model.venue.Name("LT27"),
                        new Address("Lower Kent Ridge Rd"),
                        new Capacity("400")),
                new Venue(new seedu.address.model.venue.Name("University Cultural Centre"),
                        new Address("50 Kent Ridge Crescent Singapore 119279"),
                        new Capacity("1000")),
                new Venue(new seedu.address.model.venue.Name("Central Library"),
                        new Address("12 Kent Ridge Crescent Singapore 119275"),
                        new Capacity("300")),
                new Venue(new seedu.address.model.venue.Name("I3 Auditorium"),
                        new Address("21 Heng Mui Keng Terrace"),
                        new Capacity("400")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        // Add sample persons
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }

        // Add sample events
        for (Event sampleEvent : getSampleEvents()) {
            sampleAb.addEvent(sampleEvent);
        }

        // Add sample vendors
        for (Vendor sampleVendor : getSampleVendors()) {
            sampleAb.addVendor(sampleVendor);
        }

        // Add sample venues
        for (Venue sampleVenue : getSampleVenues()) {
            sampleAb.addVenue(sampleVenue);
        }

        return sampleAb;
    }

}
