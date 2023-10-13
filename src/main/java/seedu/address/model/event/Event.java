package seedu.address.model.event;

public class Event {
    private Name name;
    private Description description;
    private DateTime dateTime;

    public Event(Name name, Description description, DateTime dateTime) {
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
    }
}