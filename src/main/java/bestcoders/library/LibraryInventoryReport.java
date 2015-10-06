package bestcoders.library;

import java.util.Collection;

import bestcoders.library.loans.LoanRecord;


/**
 * base definition of reports for 
 * librarians with a view over all
 * members and items 
 */
public interface LibraryInventoryReport {
    
    public Collection<LoanRecord> apply();
}
