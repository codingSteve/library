package library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import bestcoders.library.FrontDesk;
import bestcoders.library.Item;
import bestcoders.library.Library;
import bestcoders.library.LibraryMember;
import bestcoders.library.Member;

public class TestBorrow {

    @Test
    public void testBorrow() {
	final Member m = new LibraryMember();
	final FrontDesk d = Library.frontDesk();

	final Item b = d.findItemById(1);
	final boolean actualResult = d.requestCheckout(m, b);
	final boolean expectedResult = true;

	assertEquals(actualResult, expectedResult);

    }

}
