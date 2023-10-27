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
    private Label eventName;
    @FXML
    private Label description;
    @FXML
    private Label date;
    @FXML
    private Label venueName;
    @FXML
    private Label address;
    @FXML
    private Label capacity;

    public EventDetailsDisplay() {
        super(FXML);
    }

    public void setEventDetails(Event event) {
        if (event == null) {
            clearEventDetails();
            return;
        }

        eventName.setText(event.getName().toString());
        description.setText(event.getDescription().toString());
        date.setText("Date: " + event.getDate().toString());

        if (event.getVenue() != null) {
            venueName.setText("Venue: " + event.getVenue().getName().toString());
            address.setText("Address: " + event.getVenue().getAddress().toString());
            capacity.setText("Capacity: " + event.getVenue().getCapacity().toString());
        } else {
            clearVenueDetails();
        }
    }

    private void clearEventDetails() {
        eventName.setText("");
        description.setText("");
        date.setText("");
        clearVenueDetails();
    }

    private void clearVenueDetails() {
        venueName.setText("");
        address.setText("");
        capacity.setText("");
    }
}
