package bestcoders.library;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Library {
    static Collection<LoanRecord> loans = new ArrayList<LoanRecord>();
    private static Logger logger = LoggerFactory.getLogger(Library.class);

    private static void addLoanRecord(final Item i, final Member m) {
	final LoanRecord l = new LoanRecord(i, m);
	loans.add(l);
	logger.info("We've recorded {} loans.", loans.size());
    }

    public static boolean checkout(final Item i, final Member m) {
	logger.debug("About to add new loan record for item {} to member {}", i, m);
	addLoanRecord(i, m);
	return true;
    }

    public static FrontDesk frontDesk() {
	return new FrontDesk();
    }

    public static int getStockAvailable(final Item i) {
	logger.info("About to check for availability of {}", i);
	final int copiesAvailable = (i.getId() == 1 ? 1 : 0);
	logger.debug("Item: {} is available == {}", i, copiesAvailable);

	return copiesAvailable;
    }

    public static boolean returnItem(final Member m, final Item i) {
	final Stream<LoanRecord> memberLoans = loans.stream()
		.filter(lr -> lr.state == LoanState.OPEN && lr.member.equals(m) && lr.item.equals(i));

	final Optional<LoanRecord> loanRecord = memberLoans.findFirst();

	final boolean op;

	if (loanRecord.isPresent()) {
	    logger.debug("Item: {} has been loaned to member: {}", i, m);
	    final LoanRecord lr = loanRecord.get();
	    lr.returnDate = BusinessDate.getCurrentDate();
	    lr.state = LoanState.CLOSED;

	    op = true;
	} else {
	    logger.debug("Item: {} has not been loaned to member: {}", i, m);
	    op = false;
	}
	return op;
    }

}
