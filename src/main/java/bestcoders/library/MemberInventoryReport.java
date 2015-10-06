package bestcoders.library;

import java.util.Collection;

import bestcoders.library.loans.LoanRecord;
import bestcoders.library.members.LibraryMember;



/**
 * Base definition of behaviour for
 * read only services on specific 
 * members. 
 */
public interface MemberInventoryReport {

    public Collection<LoanRecord> apply(LibraryMember m);
}
