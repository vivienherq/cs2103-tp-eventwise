package seedu.address.model.displayable;

/**
 * A common interface for objects that can be displayed in a listview.
 */
public interface DisplayableListViewItem {
    String getDisplayTitle();
    String getDisplayFirstText();
    String getDisplaySecondText();
    boolean isSameItem(DisplayableListViewItem displayableListViewItem);
}
