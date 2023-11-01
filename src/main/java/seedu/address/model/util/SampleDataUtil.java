package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Note;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

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
                new Date("24-12-2023"), null),
            new Event(new seedu.address.model.event.Name("Math Olympiad"),
                new Description("Test your math skills in a challenging competition"),
                new Date("03-01-2024"), null),
            new Event(new seedu.address.model.event.Name("Career Fair"),
                    new Description("Get insights on different career paths"),
                    new Date("08-01-2024"), new Note("Its in 2024.")),
            new Event(new seedu.address.model.event.Name("Supernova"),
                    new Description("All night of partying with a lit DJ lineup"),
                    new Date("03-02-2024"), null),
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
        return sampleAb;
    }

}
