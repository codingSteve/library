package bestcoders.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestReturn {

    @Test
    public void testReturn() {
	final Member m = new LibraryMember();
	final FrontDesk d = Library.frontDesk();

	final Item b = d.findItemById(1);
	d.requestCheckout(m, b);

	final boolean actualResult = d.returnItem(m, b);
	final boolean expectedResult = true;

	assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testReturnWithNoCheckout() {
	final Member m = new LibraryMember();
	final FrontDesk d = Library.frontDesk();

	final Item b = d.findItemById(1);
	final boolean actualResult = d.returnItem(m, b);
	final boolean expectedResult = false;

	assertEquals(expectedResult, actualResult);

    }

}
