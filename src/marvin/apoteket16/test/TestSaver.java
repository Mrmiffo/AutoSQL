package marvin.apoteket16.test;

import java.io.IOException;

import marvin.apoteket16.sql.AsqlConnection;
import marvin.apoteket16.utilities.AsqlFileLoader;
import marvin.apoteket16.utilities.AsqlFileSaver;

public class TestSaver {
	public static void main(String[] args){
		String testSaveName = "testArrayList";
		AsqlConnection testCon = new AsqlConnection("testName", "testUserName","testPW","TestHostName",12,"testSID");
		try {
			AsqlFileSaver.saveObject(testCon, testSaveName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AsqlConnection loadCon = null;
		try {
			loadCon = (AsqlConnection) AsqlFileLoader.loadFile(testSaveName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(loadCon.getName());
		
		
	}
}
