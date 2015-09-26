package bestcoders.library.frontdesk;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bestcoders.library.BusinessDate;
import bestcoders.library.Library;
import bestcoders.library.LoanRecord;
import bestcoders.library.inventory.Inventory;
import bestcoders.library.items.Item;
import bestcoders.library.members.LibraryMember;

/**
 * The front desk provides a facade for the Library. We hide the complexity of
 * some calls from the members
 *
 * @author stevenpowell
 *
 */
public class SimpleFrontDesk implements FrontDesk {
    private static Logger logger = LoggerFactory.getLogger(FrontDesk.class);

    private Library library;

    public SimpleFrontDesk() {
	// TODO: the Front Desk should not create the Library.
	// https://github.com/codingSteve/library/issues/5
	library = new Library(BusinessDate.getCurrentDate(), new Inventory());
    }

    @Override
    public Optional<Item> findItemById(final int i) {

	return library.getItemById(i);

    }

    @Override
    public Collection<Item> getAvaliableItems(final LibraryMember m) {

	final Collection<Item> availableItems = library.getAvailableItems(m);
	return availableItems;

    }

    @Override
    public Collection<LoanRecord> getOverdueItems(final LibraryMember m) {
	logger.info("About to check for overdue items");

	return library.getOverdueItems(m);
    }

    @Override
    public boolean requestCheckout(final LibraryMember m, final Item i) {
	logger.info("About to check for availability for item {}", i);
	final boolean checkoutSuccessful = library.checkout(i, m);
	return checkoutSuccessful;
    }

    @Override
    public boolean returnItem(final LibraryMember m, final Item i) {
	return library.returnItem(m, i);

    }

    protected void setLibrary(final Library l) {
	library = l;
    }

}
