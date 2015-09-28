package bestcoders.library.world;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bestcoders.library.BusinessDate;
import bestcoders.library.Library;
import bestcoders.library.SupportedService;
import bestcoders.library.frontdesk.FrontDesk;
import bestcoders.library.frontdesk.SimpleFrontDesk;
import bestcoders.library.inventory.Inventory;
import bestcoders.library.items.Item;
import bestcoders.library.items.ItemType;
import bestcoders.library.loans.LoanRecord;
import bestcoders.library.members.LibraryMember;

/**
 * Mock environment to set up a set of Library members and exercise the core
 * Library classes.
 *
 *
 * @author stevenpowell
 * @see http://codingsteve.github.io/docs/2015-09-26-library-project/
 */
public class LibraryRunner {
    private static Logger logger = LoggerFactory.getLogger(LibraryRunner.class);

    private static int memberCount = 3;

    public static void main(final String[] ARGV) throws IOException, InterruptedException {
	final LibraryRunner world = new LibraryRunner();

	if (ARGV.length != 0) {
	    final String inventoryPathArg = ARGV[0];
	    final File f = new File(inventoryPathArg);
	    if (f.exists() && f.canRead()) {
		world.loadInventoryFromCSV(inventoryPathArg);

	    } else {
		logger.error("Unable to access inventory at \"{}\", system exits", inventoryPathArg);
		System.exit(-1);
	    }
	} else {
	    world.loadInventoryFromCSV("./src/main/resources/inventory.csv");
	}

	world.registerMembers();

	final Thread clock = world.startTime();

	final List<Thread> memberThreads = world.startMembers();

	clock.join();
	for (final Thread t : memberThreads) {
	    t.join();
	}

	world.listLoanRecords();

    }

    private List<LibraryMember> members;

    private final Calendar calendar = Calendar.getInstance();

    private Library library;

    private FrontDesk frontDesk;
    private BusinessDate businessDate;

    private Inventory inventory;

    LibraryRunner() {
	businessDate = new BusinessDate() {

	    @Override
	    public Date getDate() {
		final Date currentDate;
		synchronized (calendar) {
		    currentDate = calendar.getTime();
		}
		return currentDate;
	    }
	};

	inventory = new Inventory();

	library = new Library(businessDate, inventory);
	frontDesk = new SimpleFrontDesk(library);

    }

    void addInventoryItemFromCSV(final String record) {
	final String[] inventoryRecord = record.split(",");
	final int itemId = Integer.valueOf(inventoryRecord[0]);
	final ItemType itemType = ItemType.valueOf(inventoryRecord[1]);
	final String itemTitle = inventoryRecord[2];

	final Optional<Item> i = library.getItemById(itemId);
	final Item item;
	if (i.isPresent()) {
	    item = i.get();
	} else {
	    item = new Item(itemId, itemTitle, itemType);
	}

	inventory.addToInventory(item, 1);
    }

    private void listLoanRecords() {

	for (final LoanRecord lr : library.getLoans()) {
	    System.out.println(lr);
	}

    }

    private void loadInventoryFromCSV(final String path) throws IOException {
	final Stream<String> allLines = Files.lines(Paths.get(path), Charset.defaultCharset());
	final Stream<String> dataRecords = allLines.skip(1);

	dataRecords.forEach(s -> addInventoryItemFromCSV(s));
	allLines.close();

    }

    private void registerMembers() {

	members = new ArrayList<LibraryMember>(memberCount);

	final Collection<ItemType> justBooks = Arrays.asList(new ItemType[] { ItemType.BOOK });
	final Collection<ItemType> booksAndDVDs = Arrays.asList(new ItemType[] { ItemType.BOOK, ItemType.DVD });

	for (int i = 0; i++ < memberCount;) {
	    final LibraryMember newMember = new LibraryMember(i, "Number " + i,
		    (i % 2 == 1) ? justBooks : booksAndDVDs);
	    logger.info("About to enrol a new member: {}", newMember);
	    members.add(newMember);
	}
    }

    private Thread startMember(final LibraryMember m) {
	final Random rand = new Random();

	final Runnable r = new Runnable() {

	    private Optional<Item> currentItem;

	    @Override
	    public void run() {
		for (int i = 0; ++i < 20;) {

		    final Collection<Item> availableItems = library.applyMemberItemReport(m,
			    SupportedService.AVAILABLE_ITEMS);
		    currentItem = availableItems.stream().findAny();

		    if (currentItem.isPresent()) {
			final Item item = currentItem.get();
			logger.info("Member: {} about to checkout item : {}", m.getMemberNumber(), item);
			frontDesk.requestCheckout(m, item);
		    }

		    final int days = rand.nextInt(9) + 1;
		    logger.info("Member: {} about to enjoy the item for {} day(s)", m.getMemberNumber(), days);
		    try {
			Thread.sleep(500 * days);
		    } catch (final InterruptedException e) {

		    }

		    final Collection<Item> overdueItems = frontDesk.getOverdueItems(m);
		    if (!overdueItems.isEmpty()) {
			logger.error("Member: {} has overdue items!!  ", m.getMemberNumber());
			for (final Item overdueItem : overdueItems) {
			    logger.error("Member {} item {}", m, overdueItem);
			}
		    }

		    if (currentItem.isPresent()) {
			final Item item = currentItem.get();
			logger.info("Member: {} about to return item : {}", m.getMemberNumber(), item);
			frontDesk.returnItem(m, item);
		    }

		}
	    }

	};

	final Thread t = new Thread(r, "MEMBER: " + m.getMemberNumber());
	t.start();
	return t;
    }

    private List<Thread> startMembers() {
	final List<Thread> threads = new ArrayList<Thread>();
	for (final LibraryMember m : members) {
	    threads.add(startMember(m));

	}
	return threads;

    }

    private Thread startTime() {
	final Runnable r = new Runnable() {

	    @Override
	    public void run() {

		for (int i = 0; ++i < 100;) {
		    try {
			Thread.sleep(500);
		    } catch (final InterruptedException e) {
		    }

		    synchronized (calendar) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			logger.info("Day is now {} ", calendar.getTime());
		    }
		}
	    }
	};

	final Thread t = new Thread(r, "BusinessDate clock");
	t.start();
	return t;
    }

}
