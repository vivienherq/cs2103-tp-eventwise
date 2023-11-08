package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.rsvp.Rsvp;
import seedu.address.model.rsvp.RsvpStatus;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    private ObservableList<Rsvp> rsvps;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label rsvp;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex, ObservableList<Rsvp> rsvps) {
        super(FXML);
        this.person = person;
        this.rsvps = rsvps;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        System.out.println(EventDetailsDisplay.getCurrentEvent());
        RsvpStatus rsvpVal = getRsvpStatus(EventDetailsDisplay.getCurrentEvent(), person);
        displayRsvp(rsvpVal);
    }

    private RsvpStatus getRsvpStatus(Event event, Person person) {
        for (Rsvp rsvp: rsvps) {
            System.out.println("RSVP Data");
            System.out.println(rsvp.getEvent());
            System.out.println(rsvp.getPerson());
            System.out.println(rsvp.getRsvpStatus());
            if (rsvp.getEvent().isSameEvent(event) && rsvp.getPerson().isSamePerson(person)) {
                return rsvp.getRsvpStatus();
            }
        }
        return null;
    }

    private void displayRsvp(RsvpStatus rsvpVal) {
        if (rsvpVal == null) {
            rsvp.setText("To Be Confirmed");
            return;
        } else if (rsvpVal.name().equals("CC")) {
            rsvp.getStyleClass().addAll("label-tag", "green");
        } else if (rsvpVal.name().equals("CCC")) {
            rsvp.getStyleClass().addAll("label-tag", "red");
        }
        rsvp.setText(rsvpVal.getStatus());
    }
}
