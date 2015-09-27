package bestcoders.library.services;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bestcoders.library.BusinessDate;
import bestcoders.library.Library;
import bestcoders.library.MemberInventoryReport;
import bestcoders.library.loans.LoanRecord;
import bestcoders.library.members.LibraryMember;
import bestcoders.library.services.helpers.LibraryStreams;

public class MemberOverdueItemsReport implements MemberInventoryReport {
    private static Logger logger = LoggerFactory.getLogger(MemberOverdueItemsReport.class);

    private final Library library;

    private final LibraryStreams libraryStreams;

    public MemberOverdueItemsReport(final Library library) {
	this.library = library;
	libraryStreams = new LibraryStreams(library);

    }

    @Override
    public Collection<LoanRecord> apply(final LibraryMember m) {

	logger.info("About to search for overdue items for member : {} ", m);
	final BusinessDate businessDate = library.getBusinessDate();

	final Stream<LoanRecord> openLoans = libraryStreams.getOpenLoansStream();
	final Stream<LoanRecord> memberLoans = libraryStreams.getLoansForMember(openLoans, m);

	final Stream<LoanRecord> overdueItems = memberLoans
		.filter(lr -> lr.getExpectedReturnDate().compareTo(businessDate) < 0);

	return overdueItems.collect(Collectors.toList());
    }

}
