package bestcoders.library.services;

import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bestcoders.library.BusinessDate;
import bestcoders.library.InventoryService;
import bestcoders.library.Library;
import bestcoders.library.items.Item;
import bestcoders.library.loans.LoanRecord;
import bestcoders.library.loans.LoanState;
import bestcoders.library.members.LibraryMember;
import bestcoders.library.services.helpers.LibraryStreams;

public class ReturnService implements InventoryService {

    private static Logger logger = LoggerFactory.getLogger(ReturnService.class);

    private final LibraryStreams libraryStreams;

    public ReturnService(final Library library) {
	libraryStreams = new LibraryStreams(library);
    }

    @Override
    public boolean apply(final LibraryMember m, final Item i) {

	logger.info("About to return item {} from member", i.getId(), m.getMemberNumber());
	final Stream<LoanRecord> openLoans = libraryStreams.getOpenLoansStream();
	final Stream<LoanRecord> memberLoans = libraryStreams.getLoansForMember(openLoans, m);

	final Optional<LoanRecord> loanRecord = memberLoans.findFirst();

	final boolean op;

	if (loanRecord.isPresent()) {
	    logger.debug("Item: {} has been loaned to member: {}", i, m);
	    final LoanRecord lr = loanRecord.get();
	    lr.setReturnDate(BusinessDate.getCurrentDate());
	    lr.setState(LoanState.CLOSED);

	    op = true;
	} else {
	    logger.debug("Item: {} has not been loaned to member: {}", i, m);
	    op = false;
	}
	return op;
    }

}
