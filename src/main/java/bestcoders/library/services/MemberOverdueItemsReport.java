package bestcoders.library.services;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bestcoders.library.BusinessDate;
import bestcoders.library.Library;
import bestcoders.library.MemberItemReport;
import bestcoders.library.items.Item;
import bestcoders.library.loans.LoanRecord;
import bestcoders.library.members.LibraryMember;
import bestcoders.library.services.helpers.LibraryStreams;

public class MemberOverdueItemsReport implements MemberItemReport {
    private static Logger logger = LoggerFactory.getLogger(MemberOverdueItemsReport.class);

    private final Library library;

    private final LibraryStreams libraryStreams;

    public MemberOverdueItemsReport(final Library library) {
	this.library = library;
	libraryStreams = new LibraryStreams(library);

    }

    @Override
    public Collection<Item> apply(final LibraryMember m) {

	logger.info("About to search for overdue items for member : {} ", m);
	final BusinessDate businessDate = library.getBusinessDate();

	final Stream<LoanRecord> openLoans = libraryStreams.getOpenLoansStream();
	final Stream<LoanRecord> memberLoans = libraryStreams.getLoansForMember(openLoans, m);

	final Stream<LoanRecord> overdueLoans = memberLoans.filter(lr -> {
	    final BusinessDate expectedReturnDate = lr.getExpectedReturnDate();
	    final BusinessDate currentDate = businessDate;

	    return -1 == expectedReturnDate.compareTo(currentDate);
	});

	final Stream<Item> overdueItems = overdueLoans.map(lr -> lr.getItem());

	return overdueItems.collect(Collectors.toList());
    }

}
