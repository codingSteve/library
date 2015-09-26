package bestcoders.library;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bestcoders.library.items.Item;
import bestcoders.library.items.ItemType;
import bestcoders.library.members.LibraryMember;

public class FrontDesk {
    private static Logger logger = LoggerFactory.getLogger(FrontDesk.class);

    private Library library;

    public FrontDesk() {
	library = new Library();
    }

    public Optional<Item> findItemById(final int i) {

	return library.getItemById(i);

    }

    public Collection<Item> getAvaliableItems(final LibraryMember m) {
	final Collection<ItemType> roles = m.permittedItemTypes;

	final Collection<Item> availableItems = library.getAvailableItems(roles);
	return availableItems;

    }

    public boolean requestCheckout(final LibraryMember m, final Item i) {
	logger.info("About to check for availability for item {}");

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
