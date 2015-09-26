package bestcoders.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import bestcoders.library.helpers.LibraryFactory;
import bestcoders.library.items.Item;
import bestcoders.library.items.ItemType;
import bestcoders.library.members.LibraryMember;

public class TestInventory {

    private Item getMeditationsBook() {
	return new Item(3, "Meditations", ItemType.BOOK);
    }

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

	final Library library = LibraryFactory.getLibrary();
	final FrontDesk d = new TestFrontDesk(library);

	final List<ItemType> types = Arrays.asList(new ItemType[] { ItemType.BOOK });
	final LibraryMember m = new LibraryMember(1, "Steve", types);

	final Collection<Item> availableInventory = d.getAvaliableItems(m);
	final int actualResult = availableInventory.size();

	final int expectedResult = 2;

	assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testInventoryWithLoans() {

	final Library library = LibraryFactory.getLibrary();
	final FrontDesk d = new TestFrontDesk(library);

	final Item stoic = getMeditationsBook();
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

    @Test
    public void testInventoryWithLoansAndRemainingStock() {

	final Library library = LibraryFactory.getLibrary();
	final FrontDesk d = new TestFrontDesk(library);

	final Item stoic = getMeditationsBook();
	library.addInventoryItem(stoic, 2);

	final List<ItemType> types = Arrays.asList(new ItemType[] { ItemType.BOOK });
	final LibraryMember m = new LibraryMember(1, "Steve", types);

	final boolean borrowSuceeded = d.requestCheckout(m, stoic);

	assertTrue(borrowSuceeded);

	final Collection<Item> availableInventory = d.getAvaliableItems(m);
	final int actualResult = availableInventory.size();

	final int expectedResult = 3;

	assertEquals(expectedResult, actualResult);

    }
}
