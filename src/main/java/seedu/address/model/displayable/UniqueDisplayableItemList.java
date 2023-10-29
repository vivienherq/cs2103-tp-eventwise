package seedu.address.model.displayable;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.displayable.exceptions.DisplayableItemNotFoundException;
import seedu.address.model.displayable.exceptions.DuplicateDisplayableItemException;

/**
 * A list of displayable listview items that enforces uniqueness between its elements and does not allow nulls.
 * A displayable listview item is considered unique by comparing using
 * {@code DisplayableListViewItem#isSameItem (DisplayableListViewItem)}. As such, adding and updating of
 * displayableListItem uses DisplayableListViewItem#isSameItem(DisplayableListViewItem)) for equality so as to
 * ensure that the displayableListItem being added or updated is unique in terms of identity in the UniqueEventList.
 * However, the removal of a displayableListItem uses Event#equals(Object) so as to ensure that the event with exactly
 * the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see DisplayableListViewItem#isSameItem(DisplayableListViewItem)
 */
public class UniqueDisplayableItemList implements Iterable<DisplayableListViewItem> {

    private final ObservableList<DisplayableListViewItem> internalList = FXCollections.observableArrayList();
    private final ObservableList<DisplayableListViewItem> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent displayableListViewItem as the given argument.
     */
    public boolean contains(DisplayableListViewItem toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameItem);
    }

    /**
     * Adds a displayableListViewItem to the list.
     * The displayableListViewItem must not already exist in the list.
     */
    public void add(DisplayableListViewItem toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDisplayableItemException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the displayableListViewItem {@code target} in the list with {@code editedDisplayableItem}.
     * {@code target} must exist in the list.
     * The displayableListViewItem identity of {@code editedVenue} must not be the same as another existing
     * displayableListViewItem in the list.
     */
    public void setDisplayableItem(DisplayableListViewItem target, DisplayableListViewItem editedDisplayableItem) {
        requireAllNonNull(target, editedDisplayableItem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DisplayableItemNotFoundException();
        }

        if (!target.isSameItem(editedDisplayableItem) && contains(editedDisplayableItem)) {
            throw new DuplicateDisplayableItemException();
        }

        internalList.set(index, editedDisplayableItem);
    }

    /**
     * Removes the equivalent displayableListViewItem from the list.
     * The displayableListViewItem must exist in the list.
     */
    public void remove(DisplayableListViewItem toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DisplayableItemNotFoundException();
        }
    }

    public void setDisplayableItems(UniqueDisplayableItemList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code items}.
     * {@code items} must not contain duplicate displayableListItems.
     */
    public void setDisplayableItems(List<? extends DisplayableListViewItem> items) {
        requireAllNonNull(items);
        if (!itemsAreUnique(items)) {
            throw new DuplicateDisplayableItemException();
        }

        internalList.setAll(items);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<DisplayableListViewItem> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<DisplayableListViewItem> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueDisplayableItemList)) {
            return false;
        }

        UniqueDisplayableItemList otherUniqueVenueList = (UniqueDisplayableItemList) other;
        return internalList.equals(otherUniqueVenueList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code items} contains only unique displayableListViewItems.
     */
    private boolean itemsAreUnique(List<? extends DisplayableListViewItem> items) {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = i + 1; j < items.size(); j++) {
                if (items.get(i).isSameItem(items.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
