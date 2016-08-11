package marvin.apoteket16.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import marvin.apoteket16.schedule.AsqlDateRepeaterSR;
import marvin.apoteket16.schedule.AsqlSchedule.Interval;


public class TestRepeater {

	public static void main(String[] args) {
		GregorianCalendar cal = new GregorianCalendar();
		Date startDate = cal.getTime();
		System.out.println("StartDate: " + startDate);
		cal.add(Calendar.MONTH, 5);
		Date endDate = cal.getTime();
		System.out.println("End date: " + endDate);
		AsqlDateRepeaterSR testRepeater = new AsqlDateRepeaterSR(Interval.WEEKLY, startDate, endDate);
		for (int i = 0;i < 10; i++){
			Date result = testRepeater.getNext();
			testRepeater.setLastRun(result);
			System.out.println(i + " " + result);
		}

	}

}
