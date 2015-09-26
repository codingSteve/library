package bestcoders.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestBorrow {

    @Test
    public void testBorrow() {
	final Member m = new LibraryMember(1, "Steve");
	final FrontDesk d = Library.frontDesk();

	final Item b = d.findItemById(1);
	final boolean actualResult = d.requestCheckout(m, b);
	final boolean expectedResult = true;

	assertEquals(expectedResult, actualResult);

    }

}
