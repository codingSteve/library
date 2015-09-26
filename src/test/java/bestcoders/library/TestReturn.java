package bestcoders.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import bestcoders.library.helpers.LibraryFactory;
import bestcoders.library.items.Item;
import bestcoders.library.items.ItemType;
import bestcoders.library.members.LibraryMember;

public class TestReturn {

    @Test
    public void testReturn() {

	final List<ItemType> types = Arrays.asList(new ItemType[] { ItemType.BOOK });
	final LibraryMember m = new LibraryMember(1, "Steve", types);

	final Library l = LibraryFactory.getLibrary();

	final FrontDesk d = new TestFrontDesk(l);

	final Optional<Item> b = d.findItemById(1);

	assertTrue(b.isPresent());

	final Item book = b.get();
	d.requestCheckout(m, book);

	final boolean actualResult = d.returnItem(m, book);
	final boolean expectedResult = true;

	assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testReturnWithNoCheckout() {
	final List<ItemType> types = Arrays.asList(new ItemType[] { ItemType.BOOK });
	final LibraryMember m = new LibraryMember(1, "Steve", types);
	final Library l = LibraryFactory.getLibrary();
	final FrontDesk d = new TestFrontDesk(l);

	final Optional<Item> b = d.findItemById(1);
	assertTrue(b.isPresent());
	final Item book = b.get();

	final boolean actualResult = d.returnItem(m, book);
	final boolean expectedResult = false;

	assertEquals(expectedResult, actualResult);

    }

}
