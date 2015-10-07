package bestcoders.library;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private final Map<SupportedService, InventoryService> supportedInventoryServices;

    private final Map<SupportedService, MemberItemReport> supportedMemberReportServices;

    public Library(final BusinessDate businessDate, final Inventory inventory) {
	this.businessDate = businessDate;
	this.inventory = inventory;

	final Map<SupportedService, InventoryService> m = new HashMap<SupportedService, InventoryService>();

	m.put(SupportedService.BORROW, new BorrowService(this));
	m.put(SupportedService.RETURN, new ReturnService(this));

	supportedInventoryServices = Collections.unmodifiableMap(m);

	final Map<SupportedService, MemberItemReport> rs = new HashMap<SupportedService, MemberItemReport>();

	rs.put(SupportedService.AVAILABLE_ITEMS, new AvailableItemsService(this));
	rs.put(SupportedService.OVERDUE_ITEMS, new MemberOverdueItemsReport(this));

	supportedMemberReportServices = Collections.unmodifiableMap(rs);

    }

    public boolean addInventoryItem(final Item item, final int quantity) {
	inventory.addToInventory(item, quantity);
	return true;
    }

    public void addLoanRecord(final Item i, final LibraryMember m) {
	final LoanRecord l = new LoanRecord(i, m, businessDate);

	loans.add(l);
	logger.debug("We've recorded {} loan(s).", loans.size());
    }

    /**
     *
     *
     * @param i
     *            the item we want to act on
     * @param m
     *            the member performing or requesting the action
     * @param s
     *            the service type we want to apply
     * @return boolean indicating success of the operation
     */
    public boolean applyInventoryService(final Item i, final LibraryMember m, final SupportedService s) {
	final InventoryService service = supportedInventoryServices.get(s);
	return service.apply(m, i);
    }

    /**
     * Generic method to support Item reporting for members
     *
     * @param m
     *            the member we're reporting on
     * @param s
     *            the service report we want to run
     * @return a collection of Items from the concrete service
     */
    public Collection<Item> applyMemberItemReport(final LibraryMember m, final SupportedService s) {
	final MemberItemReport r = supportedMemberReportServices.get(s);
	return r.apply(m);
    }

    public BusinessDate getBusinessDate() {
	return businessDate.addDays(0);
    }

    public Optional<Item> getItemById(final int i) {
	logger.info("About to search for item with id of {}", i);
	return inventory.getFullCatalogue().stream().filter(ii -> ii.getItem().getId() == i).limit(1l)
		.map(InventoryItem::getItem).findFirst();
    }

    public List<LoanRecord> getLoans() {
	return Collections.unmodifiableList(loans);
    }

    public Stream<InventoryItem> getPermittedItemsStreamByMember(final LibraryMember m) {
	return inventory.getFullCatalogue().stream()
		.filter(invItem -> m.getPermittedItemTypes().contains(invItem.getItem().getType()));
    }

}
