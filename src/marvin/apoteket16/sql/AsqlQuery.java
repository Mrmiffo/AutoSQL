package marvin.apoteket16.sql;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import marvin.apoteket16.locale.AsqlLocale.Translation;
import marvin.apoteket16.schedule.InterfaceAsqlScheduleRepeater;
import marvin.apoteket16.utilities.AsqlFileLoader;
import marvin.apoteket16.utilities.GuiPopUpManager;

/**
 * Runnable class used to send a query to the DB at the given time and posting the results.
 * @author Anton
 *
 */
public class AsqlQuery implements Runnable{
	
	private AsqlConnection con;
	private InterfaceAsqlScheduleRepeater repeater;
	private String script;
	private InterfaceAsqlResultManager resultManager;
	private String scriptPath;
	private final int MAX_TRIES = 3;
	/**
	 * A runnable class used to Execute scripts at a scheduled time. When the run() method has been called the object will
	 * will put the thread to sleep until the runTime.
	 * @param database the DB to query
	 * @param repeater when to run the query. 
	 * @param scriptPath the path and name as a string.
	 * @param resultManager The method with which to post the results.
	 * @throws IOException if it was unable to load the given script.
	 */
	public AsqlQuery(AsqlConnection database, InterfaceAsqlScheduleRepeater repeater, String scriptPath, InterfaceAsqlResultManager resultManager) throws IOException{
		this.con = database;
		this.repeater = repeater;
		this.scriptPath = scriptPath;
		//Load SQL
		script = AsqlFileLoader.loadSQL(scriptPath);
		this.resultManager = resultManager;
	}

	
	@Override
	public void run() {
		boolean isInterrupted = false;
		int tryNo = 1;
		Calendar calNow = Calendar.getInstance();
		Calendar calRun = Calendar.getInstance();
		AsqlQueryStatusWindow statusWindow = null;
		while(repeater.getNext() != null && tryNo <= MAX_TRIES){
			if (statusWindow != null){
				statusWindow.setMessage(Translation.QUERY_STATUS_NEXT_RUN.toString());
			}
			//Check when to run and sleep until then.
			calRun.setTime(repeater.getNext());
			while (calNow.before(calRun)){
				try {
					if (statusWindow != null){
						statusWindow.setMessage(Translation.QUERY_STATUS_SLEEP_THREAD.toString());
						statusWindow.dispose();
						statusWindow = null;
					}
					Thread.sleep(calRun.getTimeInMillis()-calNow.getTimeInMillis());
				} catch (InterruptedException e) {
					isInterrupted = true;
					break;
				}
				calNow = Calendar.getInstance();
			}
			if (isInterrupted) break;
			if (statusWindow == null){
				String statusWindowName = scriptPath.substring(scriptPath.lastIndexOf("/")+1);
				statusWindow = new AsqlQueryStatusWindow(statusWindowName);
			}
			statusWindow.setMessage(Translation.QUERY_STATUS_INPUT_REQUIREMENT.toString());
			//Check script for input requirement
			while (script.contains("&")){
				int startOfQuestion = script.indexOf("&");
				int endOfQuestion = script.indexOf("'", startOfQuestion);
				if (endOfQuestion == -1){
					endOfQuestion = script.indexOf(" ", startOfQuestion);
				}
				String question;
				if (endOfQuestion == -1)  {
					question = script.substring(startOfQuestion);
				} else {
					question = script.substring(startOfQuestion, endOfQuestion);
				}
				
				String answer = GuiPopUpManager.showInputMessage(Translation.INFORMATION_MESSAGE_NEED_INPUT + " " + question.substring(1));
				script = script.replace(question, answer);
			}
			statusWindow.setMessage(Translation.QUERY_STATUS_CONNECTING.toString());
			//Connect to DB and send query
			ResultSet result = null;
			try {
				con.connect();
				statusWindow.setMessage(Translation.QUERY_STATUS_SENDING_QUERY.toString());
				result = con.sendQuery(script);
				if (!result.isClosed()){
					//Post the results through the resultManager
					statusWindow.setMessage(Translation.QUERY_STATUS_POSTING_RESULTS.toString());
					resultManager.postResult(result);
					//Set the last run in the repeater in order to get the next time to run the script.
					statusWindow.setMessage(Translation.QUERY_STATUS_UPDATING_SCHEDULE.toString());
					repeater.setLastRun(calNow.getTime());
					tryNo = 0;
					//TODO Change to message, not error
					GuiPopUpManager.showInformationMessage(Translation.INFORMATION_MESSAGE_RESULT_POSTED.toString(), Translation.INFORMATION_MESSAGE_SUCCESS.toString());
				} else {
					GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_CONNECTION_CLOSED + "\n"+ Translation.ERROR_MESSAGE_ATTEMPT +" "+tryNo, Translation.ERROR_MESSAGE_UNABLE_TO_CONNECT_TITLE.toString());
					tryNo++;
				}
				
			} catch (UnableToConnectException e) {
				GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_UNABLE_TO_CONNECT_MESSAGE + "\n"+Translation.ERROR_MESSAGE_ATTEMPT+ " " +tryNo, Translation.ERROR_MESSAGE_UNABLE_TO_CONNECT_TITLE.toString());
				tryNo++;
				e.printStackTrace();
			} catch (SQLException e) {
				tryNo++;
				GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_INVALID_SQL +"\n" + Translation.ERROR_MESSAGE_EXCEPTION_MESSAGE +"\n"+e.getMessage()+"\n"+ Translation.ERROR_MESSAGE_ATTEMPT +" " +tryNo, Translation.ERROR_MESSAGE_INVALID_SQL.toString());
				e.printStackTrace();
			} finally {
				try {
					statusWindow.setMessage(Translation.QUERY_STATUS_DISCONNECTING.toString());
					con.disconnect();
					
				} catch (UnableToDisconnectException e) {
					GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_UNABLE_TO_DISCONNECT_MESSAGE+"\n"+ Translation.ERROR_MESSAGE_ATTEMPT+ " " +tryNo, Translation.ERROR_MESSAGE_UNABLE_TO_DISCONNECT_TITLE.toString());
					e.printStackTrace();
				}
			}
		}
		if (statusWindow != null){
			statusWindow.dispose();
		}
	}

}
