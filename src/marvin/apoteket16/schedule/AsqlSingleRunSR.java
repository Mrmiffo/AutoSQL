package marvin.apoteket16.schedule;

import java.util.Calendar;
import java.util.Date;

import marvin.apoteket16.schedule.AsqlSchedule.Interval;

/**
 * An implementation of the Schedule repeater interface that will run only once. 
 * It will return the create date of the object untill the last run method is run, 
 * upon which it will return null.
 * @author Anton
 *
 */
public class AsqlSingleRunSR implements InterfaceAsqlScheduleRepeater{
	
	private static final long serialVersionUID = -4267415261765946342L;
	private boolean hasRun = false;
	private Calendar cal;
	
	public AsqlSingleRunSR(){
		cal = Calendar.getInstance();
	}

	@Override
	/**
	 * Returns the create date of the object until the setLastRun has been run, upon which it will return null.
	 */
	public Date getNext() {
		if (!hasRun){
			return cal.getTime();
		} else return null;
	}

	@Override
	/**
	 * Returns null at all times.
	 */
	public Interval getInterval() {
		return null;
	}

	@Override
	/**
	 * After running this method the getNext method will return null.
	 */
	public void setLastRun(Date lastRun) {
		hasRun = true;
		
	}

}
