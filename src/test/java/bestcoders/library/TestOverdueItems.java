package bestcoders.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;

import bestcoders.library.frontdesk.FrontDesk;
import bestcoders.library.helpers.LibraryFactory;
import bestcoders.library.inventory.Inventory;
import bestcoders.library.items.Item;
import bestcoders.library.items.ItemType;
import bestcoders.library.members.LibraryMember;

public class TestOverdueItems {

    @Test
    public void testNoLoans() {
	final Library l = LibraryFactory.getLibrary();
	final FrontDesk f = new TestFrontDesk(l);

	final LibraryMember m = new LibraryMember(1, "Steve", Arrays.asList(new ItemType[] { ItemType.BOOK }));

	final Collection<LoanRecord> overdueItems = f.getOverdueItems(m);

	final int expectedResult = 0;
	final int actualResult = overdueItems.size();
	assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testSomeLoans() {
	final Library l = LibraryFactory.getLibrary();
	final FrontDesk f = new TestFrontDesk(l);

	final LibraryMember m = new LibraryMember(1, "Steve", Arrays.asList(new ItemType[] { ItemType.BOOK }));

	final Optional<Item> b = f.findItemById(1);
	assertTrue(b.isPresent());
	final Item book = b.get();

	final boolean checkoutSuceeded = f.requestCheckout(m, book);
	assertTrue(checkoutSuceeded);

	final Collection<LoanRecord> overdueItems = f.getOverdueItems(m);

	final int expectedResult = 0;
	final int actualResult = overdueItems.size();
	assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testSomeOverDueLoans() {

	final Calendar c = Calendar.getInstance();
	c.setTime(new java.util.Date());

	final BusinessDate moveableDate = new BusinessDate() {

	    @Override
	    public Date getDate() {
		return c.getTime();
	    }

	};

	final Library l = LibraryFactory.getLibrary(moveableDate, new Inventory());
	final FrontDesk f = new TestFrontDesk(l);

	final LibraryMember m = new LibraryMember(1, "Steve", Arrays.asList(new ItemType[] { ItemType.BOOK }));

	final Optional<Item> b = f.findItemById(1);
	assertTrue(b.isPresent());
	final Item book = b.get();

	final boolean checkoutSuceeded = f.requestCheckout(m, book);
	assertTrue(checkoutSuceeded);

	c.add(Calendar.DAY_OF_MONTH, 10);

	final Collection<LoanRecord> overdueItems = f.getOverdueItems(m);
	final int expectedResult = 1;
	final int actualResult = overdueItems.size();
	assertEquals(expectedResult, actualResult);

    }

}
