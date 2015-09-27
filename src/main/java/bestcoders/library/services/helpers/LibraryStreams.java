package bestcoders.library.services.helpers;

import java.util.stream.Stream;

import bestcoders.library.Library;
import bestcoders.library.loans.LoanRecord;
import bestcoders.library.loans.LoanState;
import bestcoders.library.members.LibraryMember;

public class LibraryStreams {

    private final Library library;

    public LibraryStreams(final Library library) {
	this.library = library;
    }

    public Stream<LoanRecord> getLoansForMember(final Stream<LoanRecord> s, final LibraryMember m) {
	return s.filter(lr -> lr.getMember().equals(m));
    }

    Stream<LoanRecord> getLoansStream() {
	return library.getLoans().stream();
    }

    public Stream<LoanRecord> getOpenLoansStream() {
	return getLoansStream().filter(lr -> lr.getState() == LoanState.OPEN);
    }

}
