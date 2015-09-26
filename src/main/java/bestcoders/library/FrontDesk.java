package bestcoders.library;

import java.util.Collection;

import bestcoders.library.items.Item;
import bestcoders.library.items.ItemType;
import bestcoders.library.members.LibraryMember;

public class FrontDesk {

    private Library library;

    public FrontDesk() {
	library = new Library();
    }

    public Item findItemById(final int i) {
	return new Item(1, "The Art Of Computer Programming Volumes 1-6", ItemType.BOOK);

    }

    public Collection<Item> getAvaliableItems(final LibraryMember m) {
	final Collection<ItemType> roles = m.permittedItemTypes;

	final Collection<Item> availableItems = library.getAvailableItems(roles);
	return availableItems;

    }

    public boolean requestCheckout(final LibraryMember m, final Item i) {
	final boolean isAvailable = (0 < library.getStockAvailable(i));
	final boolean checkoutSuccessful = isAvailable && library.checkout(i, m);
	return checkoutSuccessful;
    }

    public boolean returnItem(final LibraryMember m, final Item i) {
	return library.returnItem(m, i);

    }

    protected void setLibrary(final Library l) {
	library = l;
    }

}
