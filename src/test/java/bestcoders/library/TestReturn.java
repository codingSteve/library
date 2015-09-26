package bestcoders.library;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import bestcoders.library.items.Item;
import bestcoders.library.items.ItemType;
import bestcoders.library.members.LibraryMember;

public class TestReturn {

    @Test
    public void testReturn() {

	final List<ItemType> types = Arrays.asList(new ItemType[] { ItemType.BOOK });
	final LibraryMember m = new LibraryMember(1, "Steve", types);
	final FrontDesk d = new FrontDesk();

	final Item b = d.findItemById(1);
	d.requestCheckout(m, b);

	final boolean actualResult = d.returnItem(m, b);
	final boolean expectedResult = true;

	assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testReturnWithNoCheckout() {
	final List<ItemType> types = Arrays.asList(new ItemType[] { ItemType.BOOK });
	final LibraryMember m = new LibraryMember(1, "Steve", types);
	final FrontDesk d = new FrontDesk();

	final Item b = d.findItemById(1);
	final boolean actualResult = d.returnItem(m, b);
	final boolean expectedResult = false;

	assertEquals(expectedResult, actualResult);

    }

}
