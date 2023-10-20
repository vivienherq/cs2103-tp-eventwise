package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of an {@code Event}.
 */
public class EventDetailsDisplay extends UiPart<Region> {

    private static final String FXML = "EventDetailsDisplay.fxml";

    @FXML
    private Label name;
    @FXML
    private Label description;
    @FXML
    private Label date;

    // Temporary
    @FXML
    private Label persons;


    public EventDetailsDisplay() {
        super(FXML);
    }

    public void setEventDetails(Event event) {
        if (event == null) {
            clearEventDetails();
        } else {
            name.setText(event.getName().toString());
            description.setText(event.getDescription().toString());
            date.setText("Date: " + event.getDate().toString());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Attendees: ").append(System.lineSeparator());
            for (int i = 0; i < event.getPersons().size(); i++) {
                Person person = event.getPersons().get(i);
                stringBuilder.append(String.format("%d. %s", i + 1, person.getName()));
                if (i < event.getPersons().size()) {
                    stringBuilder.append(System.lineSeparator());
                }
            }
            persons.setText(stringBuilder.toString());
        }
    }

    /**
     * Clears the event details labels
     */
    public void clearEventDetails() {
        name.setText("");
        description.setText("");
        date.setText("");
        persons.setText("");
    }
}
