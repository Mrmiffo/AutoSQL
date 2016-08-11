package marvin.apoteket16.test;

import java.io.IOException;
import java.util.Date;

import marvin.apoteket16.schedule.AsqlDateRepeaterSR;
import marvin.apoteket16.schedule.AsqlSchedule.Interval;
import marvin.apoteket16.sql.AsqlConnection;
import marvin.apoteket16.sql.AsqlQuery;
import marvin.apoteket16.sql.InterfaceAsqlResultManager;

public class TestQuery {
	
	public static void main(String[] args){
		String path = "C:/Users/Anton/Desktop/New Text Document.sql";
		InterfaceAsqlResultManager testRM = new TestResultManager();
		AsqlConnection testConnection = new AsqlConnection("Test","test", "test", "test", 1521, "test");
		AsqlQuery testQuery = null;
		
		AsqlDateRepeaterSR testRepeater = new AsqlDateRepeaterSR(Interval.MONTHLY, new Date(), null);
		
		try {
			testQuery = new AsqlQuery(testConnection, testRepeater, path, testRM);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread testThread = new Thread(testQuery);
		testThread.start();
	}
}
