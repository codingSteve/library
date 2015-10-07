package bestcoders.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bestcoders.library.frontdesk.FrontDesk;
import bestcoders.library.frontdesk.SimpleFrontDesk;
import bestcoders.library.helpers.LibraryFactory;
import bestcoders.library.inventory.Inventory;
import bestcoders.library.items.Item;
import bestcoders.library.items.ItemType;
import bestcoders.library.members.LibraryMember;

public class TestBorrow {
    private static Logger logger = LoggerFactory.getLogger(TestBorrow.class);

    @Test
    public void testBorrow() {
	final List<ItemType> types = Arrays.asList(new ItemType[] { ItemType.BOOK });

	final Library library = LibraryFactory.getLibrary();

	final LibraryMember m = new LibraryMember(1, "Steve", types);
	final FrontDesk d = new SimpleFrontDesk(library);

	final Optional<Item> b = d.findItemById(1);
	assertTrue(b.isPresent());

	final Item book = b.get();
	logger.info("Found item == {}", book);

	final boolean actualResult = d.requestCheckout(m, book);
	final boolean expectedResult = true;

	assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testNoInfiniteBooks() {

	// See: https://github.com/codingSteve/library/issues/14
	final Library library = new Library(new BusinessDate(), new Inventory());
	final FrontDesk frontDesk = new SimpleFrontDesk(library);
	final Item book1 = new Item(12345, "Meditations", ItemType.BOOK);

	assertTrue(library.addInventoryItem(book1, 1));

	final LibraryMember customer1 = new LibraryMember(1, "Steve", Arrays.asList(ItemType.BOOK));
	final LibraryMember customer2 = new LibraryMember(2, "Angel", Arrays.asList(ItemType.BOOK));

	assertTrue(frontDesk.requestCheckout(customer1, book1));
	assertEquals(0, frontDesk.getAvaliableItems(customer2).size());
	assertFalse(frontDesk.requestCheckout(customer2, book1));

    }

    @Test
    public void testTwoBorrows() {
	final List<ItemType> types = Arrays.asList(new ItemType[] { ItemType.BOOK });

	final Library library = LibraryFactory.getLibrary();

	final LibraryMember m = new LibraryMember(1, "Steve", types);
	final FrontDesk d = new SimpleFrontDesk(library);

	for (int i = 0; ++i < 3;) {
	    logger.info("About to request checkout of item id == {}", i);
	    final Optional<Item> b = d.findItemById(1);
	    assertTrue(b.isPresent());

	    final Item book = b.get();
	    logger.info("Found item == {}", book);

	    final boolean actualResult = d.requestCheckout(m, book);
	    final boolean expectedResult = true;

	    assertEquals(expectedResult, actualResult);

	}

    }
}
