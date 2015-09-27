package bestcoders.library.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bestcoders.library.InventoryService;
import bestcoders.library.Library;
import bestcoders.library.items.Item;
import bestcoders.library.members.LibraryMember;

public class BorrowService implements InventoryService {

    private static Logger logger = LoggerFactory.getLogger(BorrowService.class);

    private final Library library;

    public BorrowService(final Library library) {
	this.library = library;
    }

    @Override
    public boolean apply(final LibraryMember m, final Item i) {
	final boolean isAvailable = (0 < library.getStockAvailable(i, m));

	if (isAvailable) {
	    logger.debug("About to add new loan record for item {} to member {}", i, m);
	    library.addLoanRecord(i, m);
	}

	return isAvailable;
    }

}
