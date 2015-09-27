package bestcoders.library;

import java.util.Collection;

import bestcoders.library.loans.LoanRecord;

public interface LibraryInventoryReport {

    public Collection<LoanRecord> apply();
}
