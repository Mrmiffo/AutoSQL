package marvin.apoteket16.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import marvin.apoteket16.schedule.AsqlSchedule.Interval;

/**
 * A class used to calculate the next date at which a schedule should be run.
 * @author Anton
 *
 */
public class AsqlDateRepeaterSR implements InterfaceAsqlScheduleRepeater{

	private static final long serialVersionUID = -8961909224943027919L;
	
	private final Interval intervalToUse;
	private final Date startDate;
	private final Date endDate;
	private Date lastRun;
	private final int customInterval;
	
	/**
	 * A module for identifying the next date in a specified interval.
	 * @param intervalToUse the interval to use in getNext. If CUSTOM is selected the default customInverval is selected as 1
	 * @param startDate the start date of the interval
	 * @param endDate the end date after which no repeat should be generated. If getNext is after endDate getNext will return null.
	 */
	public AsqlDateRepeaterSR(Interval intervalToUse, Date startDate, Date endDate){
		this(intervalToUse, startDate, endDate, 1);
	}
	
	/**
	 * A module for identifying the next date in a specified interval.
	 * @param intervalToUse the interval to use in getNext. 
	 * @param startDate the start date of the interval
	 * @param endDate the end date after which no repeat should be generated. If getNext is after endDate getNext will return null.
	 * @param customIntervalDays specifies the number of days for the custom interval
	 */
	public AsqlDateRepeaterSR(Interval intervalToUse, Date startDate, Date endDate, int customIntervalDays){
		if (intervalToUse == null){
			throw new IllegalArgumentException("Interval can't be null");
		}
		if (startDate == null){
			throw new IllegalArgumentException("Start date can't be null");
		}
		this.intervalToUse = intervalToUse;
		this.startDate = startDate;
		this.endDate = endDate;
		this.customInterval = customIntervalDays; 
	}
	
	public Date getStartDate(){
		return (Date)startDate.clone();
	}
	
	public Date getEndDate(){
		if (endDate == null){
			return null;
		} else {
			return (Date)endDate.clone();
		}

	}
	
	public Date getLastRun(){
		if (lastRun == null){
			return null;
		} else {
			return (Date)lastRun.clone();
		}
	}
	

	@Override
	public void setLastRun(Date lastRun){
		if (lastRun == null){
			this.lastRun = null;
		} else {
			this.lastRun = (Date)lastRun.clone();
		}
	}
	
	@Override
	public Interval getInterval(){
		return intervalToUse;
	}

	
	/**
	 * Returns the next Date according to the start date/last run and Interval. Return null if next date is after endDate.
	 */
	public Date getNext(){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(startDate);
		
		//Find the next run time by searching from the start date with the given interval.
		while (lastRun != null && (cal.getTime().before(lastRun) || cal.getTime().equals(lastRun))){
			if (intervalToUse == Interval.DAILY){
				cal.add(Calendar.DAY_OF_MONTH, 1);
			} else if(intervalToUse == Interval.WEEKLY){
				cal.add(Calendar.DAY_OF_MONTH, 7);
			} else if(intervalToUse == Interval.MONTHLY){
				cal.add(Calendar.MONTH, 1);
			} else {
				cal.add(Calendar.HOUR_OF_DAY, customInterval);
			}
		}
		
		//If the end date has passed, return null.
		if (endDate != null && cal.getTime().after(endDate)){
			return null;
		}
		return cal.getTime();
	}

}
