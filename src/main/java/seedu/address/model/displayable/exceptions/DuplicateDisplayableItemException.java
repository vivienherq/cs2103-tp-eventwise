package seedu.address.model.displayable.exceptions;

/**
 * Signals that the operation will result in duplicate DisplayableListItem (DisplayableListItems are considered
 * duplicates if they have the same identity).
 */
public class DuplicateDisplayableItemException extends RuntimeException {
    public DuplicateDisplayableItemException() {
        super("Operation would result in duplicate displayable item");
    }
}
