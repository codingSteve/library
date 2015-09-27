package bestcoders.library.services;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bestcoders.library.BusinessDate;
import bestcoders.library.Library;
import bestcoders.library.LibraryInventoryReport;
import bestcoders.library.loans.LoanRecord;
import bestcoders.library.services.helpers.LibraryStreams;

public class OverdueItemsService implements LibraryInventoryReport {

    private static Logger logger = LoggerFactory.getLogger(BorrowService.class);

    private final Library library;

    private final LibraryStreams libraryStreams;

    public OverdueItemsService(final Library library) {
	this.library = library;
	libraryStreams = new LibraryStreams(library);
    }

    @Override
    public Collection<LoanRecord> apply() {
	logger.info("About to check for overdue items out on loan");
	final BusinessDate businessDate = library.getBusinessDate();
	final Stream<LoanRecord> openLoans = libraryStreams.getOpenLoansStream();
	final Stream<LoanRecord> overdueItems = openLoans
		.filter(lr -> -1 == lr.getExpectedReturnDate().compareTo(businessDate));

	return overdueItems.collect(Collectors.toList());
    }

}
