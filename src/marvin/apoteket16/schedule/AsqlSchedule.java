package marvin.apoteket16.schedule;

import java.io.Serializable;
import java.util.Date;

import marvin.apoteket16.locale.AsqlLocale.Translation;
import marvin.apoteket16.sql.AsqlConnection;
import marvin.apoteket16.sql.InterfaceAsqlResultManager;

/**
 * A data class containing all the information for a scheduled query.
 * @author Anton
 *
 */
public class AsqlSchedule implements Serializable{

	private static final long serialVersionUID = 5596631174029525288L;
	
	/**
	 * An enum indicating the possible intervals. If CUSTOM is selected and customIntervalDays 
	 * in constructor is not specified. # days will be defaulted to 1.
	 * @author Anton
	 *
	 */
	public enum Interval{
		MONTHLY (Translation.CREATE_SCHEDULE_REPEAT_MONTHLY),
		DAILY (Translation.CREATE_SCHEDULE_REPEAT_DAILY),
		WEEKLY (Translation.CREATE_SCHEDULE_REPEAT_WEEKLY),
		HOUR (Translation.CREATE_SCHEDULE_REPEAT_HOUR);
		
		private Translation name;
		
		private Interval(Translation name){
			this.name = name;
		}

		@Override
		public String toString() {
			return name.toString();
		}

	}
	
	private final String scriptPath;
	private final String targetPath;
	private final String name;
	private final AsqlConnection connection;
	private final InterfaceAsqlScheduleRepeater repeater;
	private final InterfaceAsqlResultManager resultManager;
	
	public AsqlSchedule(String name, String scriptPath, String targetPath, AsqlConnection connection, InterfaceAsqlScheduleRepeater repeater, InterfaceAsqlResultManager resultManager){
		this.scriptPath = scriptPath;
		this.targetPath = targetPath;
		this.name = name;
		this.connection = connection;
		this.repeater = repeater;
		this.resultManager = resultManager;
	}
	
	public String getName(){
		return name;
	}
	
	public String getSourcePath(){
		return scriptPath;
	}
	
	public String getTargetPath(){
		return targetPath;
	}
	
	public AsqlConnection getConnection(){
		return connection;
	}
	
	public Date getNextRun(){
		return repeater.getNext();
	}
	
	public InterfaceAsqlResultManager getResultManager(){
		return resultManager;
	}
	
	public InterfaceAsqlScheduleRepeater getRepeater(){
		return repeater;
	}
	
	public Interval getInterval(){
		return repeater.getInterval();
	}
}
