package marvin.apoteket16.locale;

/**
 * A class which upon creation will overwrite the AsqlLocale translations with english translations
 * @author Anton
 *
 */
public class AsqlEnglishLocale extends AsqlLocale{

	public AsqlEnglishLocale(){
		Translation.BROWSE_BUTTON.setTranslation("Browse..");
		Translation.BROWSE_DIALOG_FOLDER_SELECT_TITLE.setTranslation("Choose directory");
		Translation.ADD_BUTTON.setTranslation("Add...");
		Translation.CLEAR_BUTTON.setTranslation("Clear");
		Translation.RUN_NOW_BUTTON.setTranslation("Run now");
		Translation.SAVE_SCHEDULE_BUTTON.setTranslation("Save schedule");
		Translation.DETAILS_BUTTON.setTranslation("Details...");
		Translation.DELETE_BUTTON.setTranslation("Delete");
		Translation.RESTORE_DEFAULTS_BUTTON.setTranslation("Restore default");
		Translation.SAVE_BUTTON.setTranslation("Save");
		
		//Object names
		Translation.EXCEL_OUTPUT.setTranslation("Excel output");
		
		Translation.CREATE_SCHEDULE_TAB.setTranslation("Create schedule");
		Translation.SHOW_SCHEDULES_TAB.setTranslation("Schedules");
		Translation.OPTIONS_TAB.setTranslation("Options");
		
		//Create connection gui pop up windows
		Translation.CREATE_CONNECTION_TITLE.setTranslation("Create connection");
		Translation.CREATE_CONNECTION_NAME.setTranslation("Display name:");
		Translation.CREATE_CONNECTION_HOSTNAME.setTranslation("Hostname:");
		Translation.CREATE_CONNECTION_PORT.setTranslation("Port");
		Translation.CREATE_CONNECTION_SID.setTranslation("SID:");
		Translation.CREATE_CONNECTION_USERNAME.setTranslation("Username:");
		Translation.CREATE_CONNECTION_PASSWORD.setTranslation("Password:");
		
		Translation.CREATE_SCHEDULE_SOURCE_TITLE.setTranslation("Choose script:");
		Translation.CREATE_SCHEDULE_CONNECTION_TITLE.setTranslation("Choose connection:");
		Translation.CREATE_SCHEDULE_TARGET_TITLE.setTranslation("Choose target:");
		Translation.CREATE_SCHEDULE_START_DATE_TITLE.setTranslation("Choose start date for schedule:");
		Translation.CREATE_SCHEDULE_START_TIME.setTranslation("At:");
		Translation.CREATE_SCHEDULE_REPEAT_TITLE.setTranslation("Repeat:");
		Translation.CREATE_SCHEDULE_REPEAT_DAILY.setTranslation("Daily");
		Translation.CREATE_SCHEDULE_REPEAT_WEEKLY.setTranslation("Weekly");
		Translation.CREATE_SCHEDULE_REPEAT_MONTHLY.setTranslation("Monthly");
		Translation.CREATE_SCHEDULE_REPEAT_EVERY.setTranslation("Every:");
		Translation.CREATE_SCHEDULE_REPEAT_HOUR.setTranslation("hour(s)");
		
		Translation.SCHEDULE_LIST_DETAILS_NAME.setTranslation("Name:");
		Translation.SCHEDULE_LIST_DETAILS_NEXT_RUN.setTranslation("Next run:");
		Translation.SCHEDULE_LIST_DETAILS_REPEATING.setTranslation("Repeat:");
		Translation.SCHEDULE_LIST_DETAILS_SOURCE.setTranslation("Script:");
		Translation.SCHEDULE_LIST_DETAILS_DATABASE.setTranslation("Connection:");
		Translation.SCHEDULE_LIST_DETAILS_TARGET.setTranslation("Target:");
		Translation.SCHEDULE_LIST_DETAILS_OUTPUT.setTranslation("Output:");
		
		Translation.OPTIONS_DEFAULT_SOURCE_TITLE.setTranslation("Default folder for scripts:");
		Translation.OPTIONS_DEFAULT_TARGET_TITLE.setTranslation("Default target folder:");
		Translation.OPTIONS_SELECT_LOCALE_TITEL.setTranslation("Språk (require restart to take effect):");
		
		//Gui error messages
		Translation.ERROR_MESSAGE_UNABLE_TO_DISCONNECT_TITLE.setTranslation("Disconnect failed");
		Translation.ERROR_MESSAGE_UNABLE_TO_DISCONNECT_MESSAGE.setTranslation("Failed to disconnect from the database");
		Translation.ERROR_MESSAGE_UNABLE_TO_CONNECT_TITLE.setTranslation("Connection failed");
		Translation.ERROR_MESSAGE_UNABLE_TO_CONNECT_MESSAGE.setTranslation("Failed to connect to the database. Pleaes check your internet connection.");
		Translation.ERROR_MESSAGE_UNABLE_TO_SAVE_TITLE.setTranslation("Save failed");
		Translation.ERROR_MESSAGE_UNABLE_TO_SAVE_CONNECTIONS_MESSAGE.setTranslation("Unable to save database connections. Connections are not saved.");
		Translation.ERROR_MESSAGE_UNABLE_TO_SAVE_SCHEDULES_MESSAGE.setTranslation("Unable to save schedules. Schedules are not saved.");
		Translation.ERROR_MESSAGE_UNABLE_TO_SAVE_OPTIONS_MESSAGE.setTranslation("Unable to save options");
		Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_TITLE.setTranslation("Load failed");
		Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_SCHEDULES_MESSAGE.setTranslation("Unable to load schedules. \nPlease create a backup of ASQLSavedSchedules before you close the program to avoid loss of data.");
		Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_CONNECTIONS_MESSAGE.setTranslation("Unable to load connections. \nPlease create a backup of ASQLSavedConnections before you close the program to avoid loss of dataq.");
		Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_OPTIONS_MESSAGE.setTranslation("Unable to load options. Restoring default options.");
		Translation.ERROR_MESSAGE_UNABLE_TO_START_SCHEDULE_MESSAGE.setTranslation("Failed to create schedule. Unable to create seperate thread.");
		Translation.ERROR_MESSAGE_UNABLE_TO_START_SCHEDULE_TITLE.setTranslation("Schedule failed");
		Translation.ERROR_MESSAGE_UNABLE_TO_OPEN_FILE.setTranslation("Unable to open/find file.");
		Translation.ERROR_MESSAGE_UNABLE_TO_CLOSE_FILE.setTranslation("Unable to close file.");
		Translation.ERROR_MESSAGE_UNABLE_TO_WRITE_FILE.setTranslation("Unable to write to file");
		Translation.ERROR_MESSAGE_INVALID_INPUT_TITLE.setTranslation("Invalid input");
		Translation.ERROR_MESSAGE_INVALID_NAME.setTranslation("Invalid name. Must contain at least one letter/digit.");
		Translation.ERROR_MESSAGE_INVALID_HOSTNAME.setTranslation("Invalid hostname. Must contain at least one letter/digit.");
		Translation.ERROR_MESSAGE_INVALID_SID.setTranslation("Invalid SID. Must contain at least one letter/digit.");
		Translation.ERROR_MESSAGE_INVALID_PORT.setTranslation("Invalid port. Must be a number and contain at least on digit.");
		Translation.ERROR_MESSAGE_INVALID_USERNAME.setTranslation("Invalid username. Must contain at least one letter/digit.");
		Translation.ERROR_MESSAGE_INVALID_PASSWORD.setTranslation("Invalid password. Must contain at least one letter/digit.");
		Translation.ERROR_MESSAGE_CONNECTION_CLOSED.setTranslation("Connection closed unexpectedly. Please check your internet connection.");
		Translation.ERROR_MESSAGE_INVALID_SQL.setTranslation("Invalid SQL Script.");
		Translation.ERROR_MESSAGE_ATTEMPT.setTranslation("Attempt:");
		Translation.ERROR_MESSAGE_EXCEPTION_MESSAGE.setTranslation("Exception message:");
		
		//Gui information messages
		Translation.INFORMATION_MESSAGE_SUCCESS.setTranslation("Success!");
		Translation.INFORMATION_MESSAGE_RESULT_POSTED.setTranslation("Results posted.");
		Translation.INFORMATION_MESSAGE_SCHEDULE_CREATED_MESSAGE.setTranslation("Schedule created. Next run:");
		Translation.INFORMATION_MESSAGE_SCHEDULE_CREATED_TITLE.setTranslation("Schedule created");
		Translation.INFORMATION_MESSAGE_CONFIRM_REMOVE_TITLE.setTranslation("Remove");
		Translation.INFORMATION_MESSAGE_CONFIRM_REMOVE_MESSAGE.setTranslation("Are you sure you wish to remove?");
		Translation.INFORMATION_MESSAGE_NEED_INPUT.setTranslation("Need input regarding:");
		Translation.INFORMATION_MESSAGE_CHANGES_SAVED.setTranslation("Changes successfully saved.");
		Translation.INFORMATION_MESSAGE_RESTORE_DEFAULTS_MESSAGE.setTranslation("Are you sure you want to restore defaults?");
		Translation.INFORMATION_MESSAGE_RESTORE_DEFAULTS_TITLE.setTranslation("Restore defaults");
		
		//Query status
		Translation.QUERY_STATUS_SCRIPT_TITEL.setTranslation("Script:");
		Translation.QUERY_STATUS_MESSAGE_TITLE.setTranslation("Status:");
		Translation.QUERY_STATUS_NEXT_RUN.setTranslation("Checking next run...");
		Translation.QUERY_STATUS_SLEEP_THREAD.setTranslation("Awaiting next run, putting thread to sleep...");
		Translation.QUERY_STATUS_INPUT_REQUIREMENT.setTranslation("Checking input requirements...");
		Translation.QUERY_STATUS_CONNECTING.setTranslation("Connecting to database...");
		Translation.QUERY_STATUS_SENDING_QUERY.setTranslation("Sending query...");
		Translation.QUERY_STATUS_POSTING_RESULTS.setTranslation("Posting results...");
		Translation.QUERY_STATUS_UPDATING_SCHEDULE.setTranslation("Updating schedule...");
		Translation.QUERY_STATUS_DISCONNECTING.setTranslation("Disconnecting from database...");
	}
}
