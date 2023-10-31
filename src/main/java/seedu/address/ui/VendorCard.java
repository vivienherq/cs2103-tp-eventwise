package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.vendor.Vendor;

/**
 * An UI component that displays information of a {@code Vendor}.
 */
public class VendorCard extends UiPart<Region> {
    private static final String FXML = "VendorListCard.fxml";

    public final Vendor vendor;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;

    /**
     * Creates a {@code VendorCard} with the given {@code Vendor} and index to display.
     */
    public VendorCard(Vendor vendor, int displayedIndex) {
        super(FXML);
        this.vendor = vendor;
        id.setText(displayedIndex + ". ");
        name.setText(vendor.getName().toString());
        phone.setText(vendor.getPhone().toString());
        email.setText(vendor.getEmail().toString());
    }
}
