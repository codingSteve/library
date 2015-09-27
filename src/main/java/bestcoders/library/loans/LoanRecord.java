package bestcoders.library.loans;

import bestcoders.library.BusinessDate;
import bestcoders.library.items.Item;
import bestcoders.library.members.LibraryMember;

public class LoanRecord implements Comparable<LoanRecord> {
    final Item item;
    final LibraryMember member;
    final BusinessDate checkoutDate;
    final BusinessDate expectedReturnDate;
    BusinessDate returnDate;
    LoanState state;

    public LoanRecord(final Item i, final LibraryMember m, final BusinessDate checkoutDate) {
	item = i;
	member = m;
	this.checkoutDate = checkoutDate;
	expectedReturnDate = checkoutDate.addDays(7);
	state = LoanState.OPEN;
    }

    @Override
    public int compareTo(final LoanRecord lr) {
	return getCheckoutDate().compareTo(lr.getCheckoutDate());
    }

    public final BusinessDate getCheckoutDate() {
	return checkoutDate;
    }

    public BusinessDate getExpectedReturnDate() {
	return expectedReturnDate;
    }

    public Item getItem() {
	return item;
    }

    public LibraryMember getMember() {
	return member;
    }

    public BusinessDate getReturnDate() {
	return returnDate;
    }

    public LoanState getState() {
	return state;
    }

    public void setReturnDate(final BusinessDate returnDate) {
	this.returnDate = returnDate;
    }

    public void setState(final LoanState state) {
	this.state = state;
    }

}
