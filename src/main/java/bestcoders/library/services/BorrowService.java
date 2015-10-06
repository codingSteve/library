package bestcoders.library.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bestcoders.library.InventoryService;
import bestcoders.library.Library;
import bestcoders.library.inventory.InventoryItem;
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
	final boolean isAvailable = (0 < getStockAvailable(i, m));

	if (isAvailable) {
	    logger.debug("About to add new loan record for item {} to member {}", i, m);
	    library.addLoanRecord(i, m);
	}

	return isAvailable;
    }

    // TODO: why public? 
    public int getStockAvailable(final Item i, final LibraryMember m) {
	logger.info("About to check for availability of {}", i);

	final int copiesAvailable;
	final Optional<InventoryItem> totalInventory = library.getPermittedItemsStreamByMember(m)
		.filter(invItem -> invItem.getItem().equals(i)).findAny();

	if (totalInventory.isPresent()) {
	    final InventoryItem ii = totalInventory.get();
	    copiesAvailable = ii.getQuantity();
	} else {
	    copiesAvailable = 0;
	}

	logger.debug("Item: {} has copies available == {}", i, copiesAvailable);

	return copiesAvailable;
    }

}
