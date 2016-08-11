package marvin.apoteket16.schedule;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import marvin.apoteket16.sql.AsqlQuery;

/**
 * The schedule manager handles all the schedules and creates the separate threads for running the querys.
 * @author Anton
 *
 */
public class AsqlScheduleManager implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8796219775754997092L;
	private Map<AsqlSchedule, Thread> schedules;
	private List<Thread> tempThreads;

	public AsqlScheduleManager() throws IOException{
		this(new ArrayList<AsqlSchedule>());
	}
	
	public AsqlScheduleManager(List<AsqlSchedule> schedules) throws IOException{
		this.schedules = new HashMap<AsqlSchedule, Thread>();
		for (AsqlSchedule s: schedules){
			activateSchedule(s);
		}
		tempThreads = new ArrayList<Thread>();
		
	}
	
	public List<AsqlSchedule> getSchedules(){
		Set<AsqlSchedule> keySet = schedules.keySet();
		List<AsqlSchedule> toReturn = new ArrayList<AsqlSchedule>();
		toReturn.addAll(keySet);
		return toReturn;
	}
	
	public void addSchedule(AsqlSchedule toAdd) throws IOException{
		activateSchedule(toAdd);
	}
	
	public void removeSchedule(AsqlSchedule toRemove){
		schedules.get(toRemove).interrupt();
		schedules.remove(toRemove);
		
	}
	
	public boolean addTempThread(Thread threadToAdd){
		return tempThreads.add(threadToAdd);
	}
	
	/**
	 * Local method to create a thread for running the schedule.
	 * @param schedule
	 */
	private void activateSchedule(AsqlSchedule schedule) throws IOException{
		Thread thread = new Thread(new AsqlQuery(schedule.getConnection(), schedule.getRepeater(), schedule.getSourcePath(),schedule.getResultManager()));
		schedules.put(schedule, thread);
		thread.start();
	}
	
	public void shutDownThreads(){
		for (Thread t: schedules.values()){
			if (t != null) t.interrupt();
		}
		for (AsqlSchedule s: schedules.keySet()){
			schedules.put(s, null);
		}
		if (tempThreads != null){
			for (Thread t: tempThreads){
				t.interrupt();
			}
			tempThreads = null;
		}

		
	}

	public void reloadThreads() throws IOException{
		shutDownThreads();
		for (AsqlSchedule s: schedules.keySet()){
			activateSchedule(s);
		}
		tempThreads = new ArrayList<Thread>();
		
	}
}
