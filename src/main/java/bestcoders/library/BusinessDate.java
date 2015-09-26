package bestcoders.library;

import java.util.Calendar;

public abstract class BusinessDate implements Comparable<BusinessDate> {

    public static BusinessDate getCurrentDate() {
	final long today = new java.util.Date().getTime();

	return new BusinessDate() {
	    @Override
	    public java.util.Date getDate() {
		return new java.util.Date(today);
	    }
	};
    }

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

    public abstract java.util.Date getDate();

}
