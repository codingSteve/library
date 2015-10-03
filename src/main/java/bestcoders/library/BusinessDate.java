package bestcoders.library;

import java.util.Calendar;

/**
 * Simple wrapper around the date class. Users can extend and override behavior
 * as needed for testing and more complex use cases
 *
 * @author stevenpowell
 * @see https://github.com/codingSteve/library/issues/10
 *
 */
public class BusinessDate implements Comparable<BusinessDate> {

    public BusinessDate addDays(final int days) {
	final Calendar c = Calendar.getInstance();
	final java.util.Date currentDate = getDate();
	c.setTime(currentDate);
	c.add(Calendar.DAY_OF_MONTH, days);
	final long targetDate = c.getTimeInMillis();

	return new BusinessDate() {
	    @Override
	    public java.util.Date getDate() {
		return new java.util.Date(targetDate);
	    }
	};
    }

    @Override
    public int compareTo(final BusinessDate o) {
	return getDate().compareTo(o.getDate());
    }

    public java.util.Date getDate() {
	return new java.util.Date();
    }

    @Override
    public String toString() {
	return getDate().toString();
    }

}
