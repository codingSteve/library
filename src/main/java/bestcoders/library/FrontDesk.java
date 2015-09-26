package bestcoders.library;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bestcoders.library.inventory.Inventory;
import bestcoders.library.items.Item;
import bestcoders.library.items.ItemType;
import bestcoders.library.members.LibraryMember;

/**
 * The front desk provides a facade for the Library. We hide the complexity of
 * some calls from the members
 *
 * @author stevenpowell
 *
 */
public class FrontDesk {
    private static Logger logger = LoggerFactory.getLogger(FrontDesk.class);

    private Library library;

    public FrontDesk() {
	// TODO: the Front Desk should not create the Library.
	// https://github.com/codingSteve/library/issues/5
	library = new Library(BusinessDate.getCurrentDate(), new Inventory());
    }

    public Optional<Item> findItemById(final int i) {

	return library.getItemById(i);

    }

    public Collection<Item> getAvaliableItems(final LibraryMember m) {
	final Collection<ItemType> roles = m.permittedItemTypes;

	final Collection<Item> availableItems = library.getAvailableItems(roles);
	return availableItems;

    }

    public Collection<LoanRecord> getOverdueItems(final LibraryMember m) {
	logger.info("About to check for overdue items");

	return library.getOverdueItems(m);
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
