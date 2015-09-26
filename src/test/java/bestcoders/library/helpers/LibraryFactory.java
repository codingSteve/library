package bestcoders.library.helpers;

import bestcoders.library.BusinessDate;
import bestcoders.library.Library;
import bestcoders.library.inventory.Inventory;
import bestcoders.library.items.Item;
import bestcoders.library.items.ItemType;

public class LibraryFactory {

    public static Library getLibrary() {
	final BusinessDate businessDate = BusinessDate.getCurrentDate();
	final Inventory inventory = new Inventory();
	return getLibrary(businessDate, inventory);

    }

    public static Library getLibrary(final BusinessDate businessDate, final Inventory inventory) {
	final Library library = new Library(businessDate, inventory);

	final Item h2g2 = new Item(1, "The hitch hiker's guide to the galaxy", ItemType.BOOK);
	library.addInventoryItem(h2g2, 42);

	final Item lotr = new Item(2, "The lord of the rings", ItemType.BOOK);
	library.addInventoryItem(lotr, 3);

	return library;

    }

}
