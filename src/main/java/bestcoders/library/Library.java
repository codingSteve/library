package bestcoders.library;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bestcoders.library.inventory.Inventory;
import bestcoders.library.inventory.InventoryItem;
import bestcoders.library.items.Item;
import bestcoders.library.items.ItemType;
import bestcoders.library.members.LibraryMember;

public class Library {
    private static Logger logger = LoggerFactory.getLogger(Library.class);

    private final Collection<LoanRecord> loans = new ArrayList<LoanRecord>();

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

    private void addLoanRecord(final Item i, final LibraryMember m) {
	final LoanRecord l = new LoanRecord(i, m);
	loans.add(l);
	logger.debug("We've recorded {} loan(s).", loans.size());
    }

    public boolean checkout(final Item i, final LibraryMember m) {
	logger.debug("About to add new loan record for item {} to member {}", i, m);
	addLoanRecord(i, m);
	return true;
    }

    public Collection<Item> getAvailableItems(final Collection<ItemType> roles) {
	logger.info("About to prepare available items for {}", roles);
	final Stream<LoanRecord> openLoans = loans.stream().filter(lr -> lr.getState() == LoanState.OPEN);
	final Map<Item, Long> onLoan = openLoans
		.collect(Collectors.groupingBy(LoanRecord::getItem, Collectors.counting()));

	final Collection<InventoryItem> fullCatalogue = inventory.getFullCatalogue();

	final Stream<InventoryItem> authorizedItems = fullCatalogue.stream().filter(invItem -> {
	    final ItemType itemType = invItem.getItem().getType();
	    return roles.contains(itemType);
	});

	final Map<Item, Integer> authorizedItemsSummary = authorizedItems.collect(Collectors
		.groupingBy(InventoryItem::getItem, Collectors.reducing(0, InventoryItem::getQuantity, Integer::sum)));

	final Collection<Item> availableItems = new ArrayList<Item>();
	for (final Entry<Item, Integer> e : authorizedItemsSummary.entrySet()) {
	    final Item item = e.getKey();

	    final Long outOnLoan = onLoan.getOrDefault(item, 0l);
	    final Long totalOwned = e.getValue().longValue();
	    final boolean itemsRemainingInStock = (outOnLoan < totalOwned);

	    logger.debug("Item: {} has {} copies on loan with a total of {} copies owned.", item, outOnLoan,
		    totalOwned);

	    if (itemsRemainingInStock) {
		availableItems.add(item);
	    }
	}

	return availableItems;
    }

    public Optional<Item> getItemById(final int i) {
	logger.info("About to search for item with id of {}", i);
	return inventory.getFullCatalogue().stream().filter(ii -> ii.getItem().getId() == i).limit(1l)
		.map(InventoryItem::getItem).findFirst();
    }

    private Stream<LoanRecord> getOpenLoansStream() {
	return loans.stream().filter(lr -> lr.state == LoanState.OPEN);
    }

    private Stream<LoanRecord> getOpenLoansStreamByUser(final LibraryMember m) {
	return getOpenLoansStream().filter(lr -> lr.getMember().equals(m));
    }

    public Collection<LoanRecord> getOverdueItems() {
	return getOverdueItemsStream().collect(Collectors.toList());
    }

    public Collection<LoanRecord> getOverdueItems(final LibraryMember m) {
	return getOverdueItemsStreamByMember(m).collect(Collectors.toList());
    }

    private Stream<LoanRecord> getOverdueItemsStream() {
	return getOpenLoansStream().filter(lr -> lr.expectedReturnDate.compareTo(businessDate) < 0);
    }

    private Stream<LoanRecord> getOverdueItemsStreamByMember(final LibraryMember m) {
	return getOverdueItemsStream().filter(lr -> lr.getMember().equals(m));
    }

    public int getStockAvailable(final Item i) {
	logger.info("About to check for availability of {}", i);

	final int copiesAvailable;
	final Optional<InventoryItem> totalInventory = inventory.getFullCatalogue().stream()
		.filter(invItem -> invItem.getItem().equals(i)).limit(1).findAny();

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
	final Stream<LoanRecord> memberLoans = getOpenLoansStreamByUser(m).filter(lr -> lr.item.equals(i)).sorted();

	final Optional<LoanRecord> loanRecord = memberLoans.findFirst();

	final boolean op;

	if (loanRecord.isPresent()) {
	    logger.debug("Item: {} has been loaned to member: {}", i, m);
	    final LoanRecord lr = loanRecord.get();
	    lr.returnDate = BusinessDate.getCurrentDate();
	    lr.state = LoanState.CLOSED;

	    op = true;
	} else {
	    logger.debug("Item: {} has not been loaned to member: {}", i, m);
	    op = false;
	}
	return op;
    }

}
