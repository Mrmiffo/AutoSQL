package marvin.apoteket16.sql;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * A class for managing the connections to DB. 
 * @author Anton
 *
 */
public class AsqlConnection implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4967903309040913796L;
	
	private String name;
	private String userName;
	private String password;
	private String hostName;
	private int port;
	private String SID;
	
	private Connection connection;
	
	private boolean isConnected;
	
	public AsqlConnection(String name, String userName, String password, String hostName, int port, String sid){
		this.name = name;
		this.userName = userName;
		this.setPassword(password);
		this.hostName = hostName;
		this.port = port;
		this.SID = sid;

	}
	
	public String getName(){
		return name;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public String getHostName(){
		return hostName;
	}
	
	public int getPort(){
		return port;
	}
	
	public String getSID(){
		return SID;
	}
	
	/**
	 * Connects the connection to the specified database. Remains connected until connection fail or it is disconnected
	 * @return true if connection was established.
	 * @throws UnableToConnectException if connection failed. Such as if any of the information given was invalid.
	 */
	public boolean connect() throws UnableToConnectException{
		if (!isConnected){
			try {

				Class.forName("oracle.jdbc.driver.OracleDriver");

			} catch (ClassNotFoundException e) {
				throw new UnableToConnectException("Driver not found");

			}

			try {

				String connectionString = "jdbc:oracle:thin:"+userName+"/"+password+"@"+hostName+":"+port+":"+SID;
				connection = DriverManager.getConnection(connectionString);

			} catch (SQLException e) {

				throw new UnableToConnectException(e.getMessage());

			}

			if (connection != null) {
				isConnected = true;
			} else {
				throw new UnableToConnectException("Unknown error");
			}
		}
		return isConnected;
	}
	
	/**
	 * Sets isConnected to false and attempts connect() again
	 * @return
	 * @throws UnableToConnectException
	 */
	public boolean reconnect() throws UnableToConnectException{
		isConnected = false;
		return connect();
	}
	
	/**
	 * Checks if connection
	 * @return true if connection is closed. Else true
	 * @throws UnableToDisconnectException if an SQLException occure when connecting the DB.
	 */
	public boolean disconnect() throws UnableToDisconnectException{
		if (connection != null){
			try {
				connection.close();
				if(connection.isClosed()){
					isConnected = false;
				}
				connection = null;
			} catch (SQLException e) {
				throw new UnableToDisconnectException("Failed to disconnect");
			}
		}
		return !isConnected;
	}
	
	/**
	 * Sends a query as a string to the DB and returns the ResultSet generated containing the query result. 
	 * Unable to send update, delete or input statments.
	 * @param query the script to run
	 * @return the ResultSet containing the result of the query.
	 * @throws SQLException if there was any error in the script or if connection to the DB fail.
	 */
	public ResultSet sendQuery(String query) throws SQLException{
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(query);
		return result;
	}

	/**
	 * Sets the DB password.
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean equals(Object toCompare){
		if (toCompare == null || !(toCompare instanceof AsqlConnection)){
			return false;
		} else if (this == toCompare){
			return true;
		}
		
		AsqlConnection comp = (AsqlConnection)toCompare;
		
		if (	   this.name.equals(comp.name)
				&& this.hostName.equals(comp.hostName)
				&& this.userName.equals(comp.userName)
				&& this.password.equals(comp.password)
				&& this.port == comp.port
				&& this.SID.equals(comp.SID)){
			return true;
		} else return false;
	}
	
	@Override
	public Object clone(){
		return new AsqlConnection(name, userName,password, hostName,port,SID);
	}
	
}
