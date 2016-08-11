package marvin.apoteket16.sql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AsqlConnectionManager implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7990988606160194851L;
	private List<AsqlConnection> connections;
	private List<AsqlConnection> extractedInstances;
	
	public AsqlConnectionManager(){
		connections = new ArrayList<AsqlConnection>();
		extractedInstances = new ArrayList<AsqlConnection>();
	}
	/**
	 * Convenient method to get all the connection names O(n)
	 * @return A list of all the connection names
	 */
	public synchronized List<String> getConnectionNames() {
		List<String> toReturn = new ArrayList<String>();
		for (AsqlConnection con: connections){
			toReturn.add(con.getName());
		}
		return toReturn;
	}
	
	/**
	 * Method to get a clone of the specified AsqlConnection based on the connection name. O(n)
	 * @param conName The name of the connection to get
	 * @return the connection which has a name equals to conName. Return null if no connection was found
	 */
	public synchronized AsqlConnection getConnectionInstance(String conName){
		if (conName == null) return null;
		
		for (AsqlConnection con: connections){
			if (con.getName().equals(conName)){
				AsqlConnection clonedConnection = (AsqlConnection)con.clone();
				extractedInstances.add(clonedConnection);
				return clonedConnection;
			}
		}
		return null;
	}
	
	private synchronized AsqlConnection findConnection(String conName){
		if (conName == null) return null;
		
		for (AsqlConnection con: connections){
			if (con.getName().equals(conName)){
				return con;
			}
		}
		return null;
	}

	/**
	 * Adds a connection to the list of available connections.
	 * @param conToAdd 
	 * @return true if the value was added, else false.
	 */
	public synchronized boolean addConnection(AsqlConnection conToAdd) {
		if (conToAdd != null && !connections.contains(conToAdd)){
			return connections.add(conToAdd);	 
		}
		return false;
		
	}
	
	/**
	 * Removes the connection from the list of available connections. 
	 * @param conToRemove
	 * @return true if the connection was removed.
	 */
	public synchronized boolean removeConnection(AsqlConnection conToRemove){
		if (conToRemove != null) return connections.remove(conToRemove);
		return false;
	}
	
	/**
	 * Closes all connections and empties the list of instances extracted from getConnectionInstance. 
	 * If the extracted connections are reconnected they will not be closed by calling this function again.
	 * Method should only be called just before closing the program.
	 * @throws UnableToDisconnectException
	 */
	public synchronized void closeConnections() throws UnableToDisconnectException{
		for (String name: getConnectionNames()){
			findConnection(name).disconnect();
		}
		for (AsqlConnection con: extractedInstances){
			con.disconnect();
		}
		extractedInstances = new ArrayList<AsqlConnection>();
	}
}
