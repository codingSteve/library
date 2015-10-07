package bestcoders.library.services;

import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bestcoders.library.InventoryService;
import bestcoders.library.Library;
import bestcoders.library.inventory.InventoryItem;
import bestcoders.library.items.Item;
import bestcoders.library.loans.LoanRecord;
import bestcoders.library.members.LibraryMember;
import bestcoders.library.services.helpers.LibraryStreams;

public class BorrowService implements InventoryService {

    private static Logger logger = LoggerFactory.getLogger(BorrowService.class);

    private final Library library;

    private final LibraryStreams libraryStreams;

    public BorrowService(final Library library) {
	this.library = library;
	libraryStreams = new LibraryStreams(library);
    }

    @Override
    public boolean apply(final LibraryMember m, final Item i) {
	final boolean isAvailable = (0 < getStockAvailable(i, m));

	if (isAvailable) {
	    logger.debug("About to add new loan record for item {} to member {}", i, m);
	    library.addLoanRecord(i, m);
	}

	return isAvailable;
    }

    private long getStockAvailable(final Item i, final LibraryMember m) {
	logger.info("About to check for availability of {}", i);

	final long copiesAvailable;
	final Optional<InventoryItem> totalInventory = library.getPermittedItemsStreamByMember(m)
		.filter(invItem -> invItem.getItem().equals(i)).findAny();

	if (totalInventory.isPresent()) {
	    final InventoryItem ii = totalInventory.get();
	    final Stream<LoanRecord> openLoans = libraryStreams.getOpenLoansStream();
	    final long copiesOnLoan = openLoans.filter(lr -> lr.getItem().equals(i)).count();

	    copiesAvailable = ii.getQuantity() - copiesOnLoan;
	} else {
	    copiesAvailable = 0;
	}

	logger.debug("Item: {} has copies available == {}", i, copiesAvailable);

	return copiesAvailable;
    }

}
