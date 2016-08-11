package marvin.apoteket16.test;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import marvin.apoteket16.sql.InterfaceAsqlResultManager;

public class TestResultManager implements InterfaceAsqlResultManager{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5460553987664488916L;

	@Override
	public void postResult(ResultSet result) {
		ResultSetMetaData meta;
		try {
			meta = result.getMetaData();
			while(result.next()){
				for (int i = 1; i <= meta.getColumnCount(); i++){
					System.out.println(result.getString(i));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
