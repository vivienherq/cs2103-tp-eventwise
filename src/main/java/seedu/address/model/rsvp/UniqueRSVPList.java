package seedu.address.model.rsvp;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.rsvp.exceptions.DuplicateRSVPException;
import seedu.address.model.rsvp.exceptions.RSVPNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see RSVP#isSameRSVP(RSVP)
 */
public class UniqueRSVPList implements Iterable<RSVP> {

    private final ObservableList<RSVP> internalList = FXCollections.observableArrayList();
    private final ObservableList<RSVP> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(RSVP toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameRSVP);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(RSVP toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRSVPException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setRSVP(RSVP target, RSVP editedRSVP) {
        requireAllNonNull(target, editedRSVP);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RSVPNotFoundException();
        }

        if (!target.isSameRSVP(editedRSVP) && contains(editedRSVP)) {
            throw new DuplicateRSVPException();
        }

        internalList.set(index, editedRSVP);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(RSVP toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RSVPNotFoundException();
        }
    }

    public void setRSVPs(UniqueRSVPList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setRSVPs(List<RSVP> rsvps) {
        requireAllNonNull(rsvps);
        if (!rsvpsAreUnique(rsvps)) {
            throw new DuplicateRSVPException();
        }

        internalList.setAll(rsvps);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<RSVP> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<RSVP> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueRSVPList)) {
            return false;
        }

        UniqueRSVPList otherUniqueRSVPList = (UniqueRSVPList) other;
        return internalList.equals(otherUniqueRSVPList.internalList);
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
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean rsvpsAreUnique(List<RSVP> rsvps) {
        for (int i = 0; i < rsvps.size() - 1; i++) {
            for (int j = i + 1; j < rsvps.size(); j++) {
                if (rsvps.get(i).isSameRSVP(rsvps.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
