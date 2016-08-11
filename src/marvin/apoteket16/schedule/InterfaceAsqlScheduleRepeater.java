package marvin.apoteket16.schedule;

import java.io.Serializable;
import java.util.Date;

import marvin.apoteket16.schedule.AsqlSchedule.Interval;

public interface InterfaceAsqlScheduleRepeater extends Serializable{
	public Date getNext();
	
	public Interval getInterval();

	/**
	 * Sets the last datetime the schedule was run. This value is used by getNext() to find the next date in the interval.
	 * @param lastRun
	 */
	void setLastRun(Date lastRun);
}
