package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.displayable.DisplayableListViewItem;

/**
 * Panel containing the list of displayable list view items.
 */
public class DisplayableListPanel extends UiPart<Region> {

    private static final String FXML = "DisplayableListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DisplayableListPanel.class);

    @FXML
    private ListView<DisplayableListViewItem> displayableItemListView;

    /**
     * Creates a {@code EventListPanel} with the given {@code ObservableList}.
     */
    public DisplayableListPanel(ObservableList<DisplayableListViewItem> displayableListViewItems) {
        super(FXML);
        displayableItemListView.setItems(displayableListViewItems);
        displayableItemListView.setCellFactory(listView -> new DisplayableListPanel.DisplayableItemListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class DisplayableItemListViewCell extends ListCell<DisplayableListViewItem> {
        @Override
        protected void updateItem(DisplayableListViewItem item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DisplayCard(item, getIndex() + 1).getRoot());
            }
        }
    }
}
