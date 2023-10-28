package seedu.address.model.rsvp;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.rsvp.exceptions.DuplicateRsvpException;
import seedu.address.model.rsvp.exceptions.RsvpNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Rsvp#isSameRsvp(Rsvp)
 */
public class UniqueRsvpList implements Iterable<Rsvp> {

    private final ObservableList<Rsvp> internalList = FXCollections.observableArrayList();
    private final ObservableList<Rsvp> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Rsvp toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameRsvp);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Rsvp toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRsvpException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setRsvp(Rsvp target, Rsvp editedRsvp) {
        requireAllNonNull(target, editedRsvp);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RsvpNotFoundException();
        }

        if (!target.isSameRsvp(editedRsvp) && contains(editedRsvp)) {
            throw new DuplicateRsvpException();
        }

        internalList.set(index, editedRsvp);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Rsvp toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RsvpNotFoundException();
        }
    }

    public void setRsvps(UniqueRsvpList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setRsvps(List<Rsvp> rsvps) {
        requireAllNonNull(rsvps);
        if (!rsvpsAreUnique(rsvps)) {
            throw new DuplicateRsvpException();
        }

        internalList.setAll(rsvps);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Rsvp> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Rsvp> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueRsvpList)) {
            return false;
        }

        UniqueRsvpList otherUniqueRsvpList = (UniqueRsvpList) other;
        return internalList.equals(otherUniqueRsvpList.internalList);
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
    private boolean rsvpsAreUnique(List<Rsvp> rsvps) {
        for (int i = 0; i < rsvps.size() - 1; i++) {
            for (int j = i + 1; j < rsvps.size(); j++) {
                if (rsvps.get(i).isSameRsvp(rsvps.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
