package seedu.address.model.rsvp;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.rsvp.exceptions.RsvpNotFoundException;

/**
 * A list of RSVPs that enforces uniqueness between its elements and does not allow nulls.
 * A RSVP is considered unique by comparing using {@code Rsvp#isSameRsvp(Rsvp)}. As such, adding and updating of
 * RSVPs uses Rsvp#isSameRsvp(Rsvp) for equality so as to ensure that the RSVP being added or updated is
 * unique in terms of identity in the UniqueRsvpList.
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
     * Returns true if the list contains an equivalent RSVP as the given argument.
     */
    public boolean contains(Rsvp toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameRsvp);
    }

    public Optional<Rsvp> getDuplicateRsvp(Rsvp toCheck) {
        requireNonNull(toCheck);
        return internalList.stream()
                .filter(toCheck::isSameRsvp)
                .findFirst();
    }

    /**
     * Adds an RSVP to the list.
     * The RSVP must not already exist in the list.
     */
    public void add(Rsvp toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            assert getDuplicateRsvp(toAdd).isPresent();
            setRsvp(getDuplicateRsvp(toAdd).get(), toAdd);
            return;
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Rsvp {@code target} in the list with {@code editedRsvp}.
     * {@code target} must exist in the list.
     * The RSVP identity of {@code editedRsvp} must not be the same as another existing RSVP in the list.
     */
    public void setRsvp(Rsvp target, Rsvp editedRsvp) {
        requireAllNonNull(target, editedRsvp);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RsvpNotFoundException();
        }
        internalList.set(index, editedRsvp);
    }

    /**
     * Removes the equivalent RSVP from the list.
     * The RSVP must exist in the list.
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
     * Replaces the contents of this list with {@code rsvps}.
     * {@code rsvps} must not contain duplicate RSVPs.
     */
    public void setRsvps(List<Rsvp> rsvps) {
        requireAllNonNull(rsvps);
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
}
