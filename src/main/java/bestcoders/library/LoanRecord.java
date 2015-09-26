package bestcoders.library;

public class LoanRecord {
    public final Item item;
    public final Member member;
    public final BusinessDate checkoutDate;
    public final BusinessDate expectedReturnDate;
    public BusinessDate returnDate;
    public LoanState state;

    public LoanRecord(final Item i, final Member m) {
	item = i;
	member = m;
	checkoutDate = BusinessDate.getCurrentDate();
	expectedReturnDate = checkoutDate.addDays(7);
	state = LoanState.OPEN;
    }

}
