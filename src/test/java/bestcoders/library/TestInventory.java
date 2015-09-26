package bestcoders.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import bestcoders.library.items.Item;
import bestcoders.library.items.ItemType;
import bestcoders.library.members.LibraryMember;

public class TestInventory {

    @Test
    public void testEmptyInventory() {

	final List<ItemType> types = Arrays.asList(new ItemType[] { ItemType.BOOK });
	final LibraryMember m = new LibraryMember(1, "Steve", types);
	final FrontDesk d = new FrontDesk();

	final Collection<Item> availableInventory = d.getAvaliableItems(m);
	final int actualResult = availableInventory.size();

	final int expectedResult = 0;

	assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testInventory() {

	final Library library = new Library();
	final FrontDesk d = new TestFrontDesk(library);

	final Item h2g2 = new Item(1, "The hitch hiker's guide to the galaxy", ItemType.BOOK);
	library.addInventoryItem(h2g2, 42);

	final Item lotr = new Item(2, "The lord of the rings", ItemType.BOOK);
	library.addInventoryItem(lotr, 3);

	final List<ItemType> types = Arrays.asList(new ItemType[] { ItemType.BOOK });
	final LibraryMember m = new LibraryMember(1, "Steve", types);

	final Collection<Item> availableInventory = d.getAvaliableItems(m);
	final int actualResult = availableInventory.size();

	final int expectedResult = 2;

	assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testInventoryWithLoans() {

	final Library library = new Library();
	final FrontDesk d = new TestFrontDesk(library);

	final Item h2g2 = new Item(1, "The hitch hiker's guide to the galaxy", ItemType.BOOK);
	library.addInventoryItem(h2g2, 41);
	library.addInventoryItem(h2g2, 1);

	final Item lotr = new Item(2, "The lord of the rings", ItemType.BOOK);
	library.addInventoryItem(lotr, 3);

	final Item stoic = new Item(3, "Meditations", ItemType.BOOK);
	library.addInventoryItem(stoic, 1);

	final List<ItemType> types = Arrays.asList(new ItemType[] { ItemType.BOOK });
	final LibraryMember m = new LibraryMember(1, "Steve", types);

	final boolean borrowSuceeded = d.requestCheckout(m, stoic);

	assertTrue(borrowSuceeded);

	final Collection<Item> availableInventory = d.getAvaliableItems(m);
	final int actualResult = availableInventory.size();

	final int expectedResult = 2;

	assertEquals(expectedResult, actualResult);

    }

}
