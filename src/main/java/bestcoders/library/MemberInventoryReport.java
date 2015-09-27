package bestcoders.library;

import java.util.Collection;

import bestcoders.library.loans.LoanRecord;
import bestcoders.library.members.LibraryMember;

public interface MemberInventoryReport {

    public Collection<LoanRecord> apply(LibraryMember m);
}
