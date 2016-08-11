package marvin.apoteket16.test;

import java.io.IOException;

import marvin.apoteket16.utilities.AsqlFileLoader;

public class TestLoader {
	public static void main(String[] args){
		String path = "C:/Users/Anton/Desktop/New Text Document.sql";
		String test = null;
		try {
			test = AsqlFileLoader.loadSQL(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(test);
	}
}
