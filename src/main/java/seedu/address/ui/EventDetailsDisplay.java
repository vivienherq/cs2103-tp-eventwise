package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;

/**
 * A UI component that displays information of an {@code Event}.
 */
public class EventDetailsDisplay extends UiPart<Region> {

    private static Event currentEvent;
    private static final String FXML = "EventDetailsDisplay.fxml";

    @FXML
    private Label eventName;
    @FXML
    private Label description;
    @FXML
    private Label fromDate;
    @FXML
    private Label venueName;
    @FXML
    private Label address;
    @FXML
    private Label capacity;
    @FXML
    private Label note;

    public EventDetailsDisplay() {
        super(FXML);
    }

    public void setEventDetails(Event event) {
        currentEvent = event;
        if (event == null) {
            clearEventDetails();
            return;
        }

        eventName.setText(event.getName().toString());
        description.setText(event.getDescription().toString());
        fromDate.setText("Date: " + event.getFromDate().toString());

        if (event.getNote() != null && !event.getNote().toString().isEmpty()) {
            note.setText("Note: " + event.getNote().toString());
        }

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
        fromDate.setText("");
        note.setText("");
        clearVenueDetails();
    }

    private void clearVenueDetails() {
        venueName.setText("");
        address.setText("");
        capacity.setText("");
    }

    public static Event getCurrentEvent() {
        return currentEvent;
    }
}
