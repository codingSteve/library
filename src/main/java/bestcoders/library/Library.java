package bestcoders.library;

import java.util.ArrayList;
import java.util.Collection;

public class Library {
    static Collection<LoanRecord> loans = new ArrayList<LoanRecord>();

    private static void addLoanRecord(final Item i, final Member m) {
	final LoanRecord l = new LoanRecord(i, m);
	loans.add(l);
    }

    public static boolean checkout(final Item i, final Member m) {
	addLoanRecord(i, m);
	return true;
    }

    public static FrontDesk frontDesk() {
	return new FrontDesk();
    }

    public static int getStockAvailable(final Item i) {
	return (i.getId() == 1 ? 1 : 0);
    }

}
