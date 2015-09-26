package bestcoders.library;

public class LoanRecord {
    public final Item item;
    public final Member member;
    public final BusinessDate checkoutDate;
    public final BusinessDate returnDate;

    public LoanRecord(final Item i, final Member m) {
	item = i;
	member = m;
	checkoutDate = BusinessDate.getCurrentDate();
	returnDate = checkoutDate.addDays(7);
    }

}
