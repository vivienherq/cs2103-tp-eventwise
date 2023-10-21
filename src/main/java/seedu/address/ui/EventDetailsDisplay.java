package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;

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
        }
    }

    /**
     * Clears the event details labels
     */
    public void clearEventDetails() {
        name.setText("");
        description.setText("");
        date.setText("");
    }
}
