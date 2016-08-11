package marvin.apoteket16.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;


public class AsqlFileLoader {
	
	public static Object loadFile(String path) throws IOException, ClassNotFoundException{
		FileInputStream fileIn = null;
		ObjectInputStream objectIn = null;
		Object toReturn = null;
		//Check if file exists.
		File checkExist = new File(path);
		if (!checkExist.exists() || checkExist.isDirectory()){
			return null;
		}
		try{

			fileIn = new FileInputStream(path);
			objectIn = new ObjectInputStream(fileIn);
			toReturn = objectIn.readObject();
		} catch (IOException e1){
			throw e1;
		} catch (ClassNotFoundException e2) {
			throw e2;
		} finally {
			fileIn.close();
			objectIn.close();
		}
		return toReturn;
	}
	
	public static String loadSQL(String path) throws IOException{
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			throw e;
		}
		String toReturn = "";
		String toAdd = "";
		boolean commentSection = false;
		boolean endOfScript = false;
		do{
			try {
				toAdd = in.readLine();
			} catch (IOException e) {
				in.close();
				throw e;
			} 
			if (toAdd != null) {
				if (toAdd.contains("/*")){
					commentSection = true;
					toAdd = "";
				} else if (toAdd.contains("*/")){
					commentSection = false;
				} else if (toAdd.contains(";")){
					toAdd = toAdd.substring(0, toAdd.indexOf(";"));
					endOfScript = true;
				} else if (commentSection){
					toAdd = "";
				}
				toAdd = cleanString(toAdd);
				if (!toAdd.equals("")) toReturn = toReturn + toAdd + " ";
			}
			
		} while(toAdd != null && !endOfScript);
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	private static String cleanString(String toAdd) {
		if (toAdd.contains("*/")){
			toAdd = toAdd.substring(toAdd.indexOf("*/")+2);
		}
		if (toAdd.contains("--")){
			toAdd = toAdd.substring(0,toAdd.indexOf("--"));
		}
		return toAdd;
	}

}
