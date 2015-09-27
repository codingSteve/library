package bestcoders.library.frontdesk;

import java.util.Collection;
import java.util.Optional;

import bestcoders.library.items.Item;
import bestcoders.library.members.LibraryMember;

/**
 * The front desk provides a facade for the Library. We hide the complexity of
 * some calls from the members
 *
 * @author stevenpowell
 *
 */
public interface FrontDesk {
    /**
     * Support for searching the library for a specific item.
     *
     * @param i
     * @return
     */
    public Optional<Item> findItemById(final int i);

    /**
     *
     * @param m
     * @return A collection of items available for the member to borrow
     */
    public Collection<Item> getAvaliableItems(final LibraryMember m);

    /**
     *
     * @param m
     * @return a Collection of items the member has failed to return by the
     *         expected date
     */
    public Collection<Item> getOverdueItems(final LibraryMember m);

    /**
     * Checkout the item if the member is permitted to do so
     *
     * @param m
     * @param i
     * @return
     */
    public boolean requestCheckout(final LibraryMember m, final Item i);

    /**
     * Return the item once the member has finished with it.
     *
     * @param m
     * @param i
     * @return
     */
    public boolean returnItem(final LibraryMember m, final Item i);

}
