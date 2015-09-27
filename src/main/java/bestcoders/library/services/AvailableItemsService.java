package bestcoders.library.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bestcoders.library.Library;
import bestcoders.library.MemberItemReport;
import bestcoders.library.inventory.InventoryItem;
import bestcoders.library.items.Item;
import bestcoders.library.items.ItemType;
import bestcoders.library.loans.LoanRecord;
import bestcoders.library.members.LibraryMember;
import bestcoders.library.services.helpers.LibraryStreams;

public class AvailableItemsService implements MemberItemReport {

    private static Logger logger = LoggerFactory.getLogger(BorrowService.class);

    private final Library library;

    private final bestcoders.library.services.helpers.LibraryStreams libraryStreams;

    public AvailableItemsService(final Library library) {
	this.library = library;
	libraryStreams = new LibraryStreams(library);
    }

    @Override
    public Collection<Item> apply(final LibraryMember m) {
	final Collection<ItemType> roles = m.getPermittedItemTypes();

	logger.info("About to prepare available items for {}", roles);
	final Stream<LoanRecord> openLoans = libraryStreams.getOpenLoansStream();
	final Map<Item, Long> onLoan = openLoans
		.collect(Collectors.groupingBy(LoanRecord::getItem, Collectors.counting()));

	final Stream<InventoryItem> authorizedItems = library.getPermittedItemsStreamByMember(m);

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

}
