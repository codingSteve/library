package bestcoders.library;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bestcoders.library.inventory.Inventory;
import bestcoders.library.inventory.InventoryItem;
import bestcoders.library.items.Item;
import bestcoders.library.loans.LoanRecord;
import bestcoders.library.members.LibraryMember;
import bestcoders.library.services.AvailableItemsService;
import bestcoders.library.services.BorrowService;
import bestcoders.library.services.MemberOverdueItemsReport;
import bestcoders.library.services.ReturnService;

public class Library {
    private static Logger logger = LoggerFactory.getLogger(Library.class);

    private final List<LoanRecord> loans = new ArrayList<LoanRecord>();

    private final Inventory inventory;

    private final BusinessDate businessDate;

    public Library(final BusinessDate businessDate, final Inventory inventory) {
	this.businessDate = businessDate;
	this.inventory = inventory;
    }

    public boolean addInventoryItem(final Item item, final int quantity) {
	inventory.addToInventory(item, quantity);
	return true;
    }

    public void addLoanRecord(final Item i, final LibraryMember m) {
	final LoanRecord l = new LoanRecord(i, m);
	loans.add(l);
	logger.debug("We've recorded {} loan(s).", loans.size());
    }

    private boolean applyInventoryService(final Item i, final LibraryMember m, final InventoryService s) {
	return s.apply(m, i);
    }

    public boolean checkout(final Item i, final LibraryMember m) {
	final InventoryService s = new BorrowService(this);
	return applyInventoryService(i, m, s);
    }

    /**
     *
     * @param member
     * @return the items available for loan to the member
     */
    public Collection<Item> getAvailableItems(final LibraryMember member) {
	final MemberItemReport r = new AvailableItemsService(this);
	return r.apply(member);
    }

    public BusinessDate getBusinessDate() {
	return businessDate;
    }

    public Optional<Item> getItemById(final int i) {
	logger.info("About to search for item with id of {}", i);
	return inventory.getFullCatalogue().stream().filter(ii -> ii.getItem().getId() == i).limit(1l)
		.map(InventoryItem::getItem).findFirst();
    }

    public List<LoanRecord> getLoans() {
	return Collections.unmodifiableList(loans);
    }

    public Collection<LoanRecord> getOverdueItems(final LibraryMember m) {
	final MemberInventoryReport r = new MemberOverdueItemsReport(this);
	return r.apply(m);
    }

    public Stream<InventoryItem> getPermittedItemsStreamByMember(final LibraryMember m) {
	return inventory.getFullCatalogue().stream()
		.filter(invItem -> m.getPermittedItemTypes().contains(invItem.getItem().getType()));
    }

    public int getStockAvailable(final Item i, final LibraryMember m) {
	logger.info("About to check for availability of {}", i);

	final int copiesAvailable;
	final Optional<InventoryItem> totalInventory = getPermittedItemsStreamByMember(m)
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

    public boolean returnItem(final LibraryMember m, final Item i) {
	final InventoryService s = new ReturnService(this);
	return applyInventoryService(i, m, s);
    }

}
