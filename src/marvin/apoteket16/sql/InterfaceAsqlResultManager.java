package marvin.apoteket16.sql;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface InterfaceAsqlResultManager extends Serializable{
	public void postResult(ResultSet result) throws SQLException;
	
	public String getName();
}
