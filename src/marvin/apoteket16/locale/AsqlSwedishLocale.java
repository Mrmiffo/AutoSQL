package marvin.apoteket16.locale;

/**
 * A class which upon creation will overwrite the AsqlLocale translations with Swedish translations
 * @author Anton
 *
 */
public class AsqlSwedishLocale extends AsqlLocale {

	public AsqlSwedishLocale(){
		Translation.BROWSE_BUTTON.setTranslation("Bl�ddra...");
		Translation.BROWSE_DIALOG_FOLDER_SELECT_TITLE.setTranslation("V�lj mapp");
		Translation.ADD_BUTTON.setTranslation("L�gg till...");
		Translation.CLEAR_BUTTON.setTranslation("Rensa");
		Translation.RUN_NOW_BUTTON.setTranslation("K�r nu");
		Translation.SAVE_SCHEDULE_BUTTON.setTranslation("Spara schema");
		Translation.DETAILS_BUTTON.setTranslation("Detaljer...");
		Translation.DELETE_BUTTON.setTranslation("Ta bort");
		Translation.RESTORE_DEFAULTS_BUTTON.setTranslation("�terst�ll standard");
		Translation.SAVE_BUTTON.setTranslation("Spara");
		
		//Object names
		Translation.EXCEL_OUTPUT.setTranslation("Excel export");
		
		Translation.CREATE_SCHEDULE_TAB.setTranslation("Skapa schema");
		Translation.SHOW_SCHEDULES_TAB.setTranslation("Scheman");
		Translation.OPTIONS_TAB.setTranslation("Inst�llningar");
		
		Translation.CREATE_SCHEDULE_SOURCE_TITLE.setTranslation("V�lj script:");
		Translation.CREATE_SCHEDULE_CONNECTION_TITLE.setTranslation("V�lj databas:");
		Translation.CREATE_SCHEDULE_TARGET_TITLE.setTranslation("V�lj m�l:");
		Translation.CREATE_SCHEDULE_START_DATE_TITLE.setTranslation("V�lj startdatum f�r schema:");
		Translation.CREATE_SCHEDULE_START_TIME.setTranslation("Klockan:");
		Translation.CREATE_SCHEDULE_REPEAT_TITLE.setTranslation("Upprepa:");
		Translation.CREATE_SCHEDULE_REPEAT_DAILY.setTranslation("Dagligen");
		Translation.CREATE_SCHEDULE_REPEAT_WEEKLY.setTranslation("Veckovis");
		Translation.CREATE_SCHEDULE_REPEAT_MONTHLY.setTranslation("M�natligen");
		Translation.CREATE_SCHEDULE_REPEAT_EVERY.setTranslation("Var:");
		Translation.CREATE_SCHEDULE_REPEAT_HOUR.setTranslation("timme");
		
		//Create connection gui pop up windows
		Translation.CREATE_CONNECTION_TITLE.setTranslation("Skapa databaskoppling");
		Translation.CREATE_CONNECTION_NAME.setTranslation("Visningsnamn:");
		Translation.CREATE_CONNECTION_HOSTNAME.setTranslation("V�rdnamn (hostname):");
		Translation.CREATE_CONNECTION_PORT.setTranslation("Port");
		Translation.CREATE_CONNECTION_SID.setTranslation("SID:");
		Translation.CREATE_CONNECTION_USERNAME.setTranslation("Anv�ndarenamn:");
		Translation.CREATE_CONNECTION_PASSWORD.setTranslation("L�senord:");
		
		Translation.SCHEDULE_LIST_DETAILS_NAME.setTranslation("Namn:");
		Translation.SCHEDULE_LIST_DETAILS_NEXT_RUN.setTranslation("K�rs n�sta g�ng:");
		Translation.SCHEDULE_LIST_DETAILS_REPEATING.setTranslation("Upprepar:");
		Translation.SCHEDULE_LIST_DETAILS_SOURCE.setTranslation("Script:");
		Translation.SCHEDULE_LIST_DETAILS_DATABASE.setTranslation("Databas:");
		Translation.SCHEDULE_LIST_DETAILS_TARGET.setTranslation("M�l:");
		Translation.SCHEDULE_LIST_DETAILS_OUTPUT.setTranslation("Resultat form:");
		
		Translation.OPTIONS_DEFAULT_SOURCE_TITLE.setTranslation("Standard mapp f�r script:");
		Translation.OPTIONS_DEFAULT_TARGET_TITLE.setTranslation("Standard mapp f�r m�l:");
		Translation.OPTIONS_SELECT_LOCALE_TITEL.setTranslation("Spr�k (kr�ver omstart f�r att f� effekt):");
		
		//Gui error messages
		Translation.ERROR_MESSAGE_UNABLE_TO_DISCONNECT_TITLE.setTranslation("Nerkoppling misslyckades");
		Translation.ERROR_MESSAGE_UNABLE_TO_DISCONNECT_MESSAGE.setTranslation("Misslyckades med att st�nga kontakten till databasen.");
		Translation.ERROR_MESSAGE_UNABLE_TO_CONNECT_TITLE.setTranslation("Uppkoppling misslyckades");
		Translation.ERROR_MESSAGE_UNABLE_TO_CONNECT_MESSAGE.setTranslation("Misslyckades med att kontakta databasen. Kontrollera din uppkoppling.");
		Translation.ERROR_MESSAGE_UNABLE_TO_SAVE_TITLE.setTranslation("Spara misslyckades");
		Translation.ERROR_MESSAGE_UNABLE_TO_SAVE_CONNECTIONS_MESSAGE.setTranslation("Misslyckades med att spara databaser. Databaserna �r inte sparade.");
		Translation.ERROR_MESSAGE_UNABLE_TO_SAVE_SCHEDULES_MESSAGE.setTranslation("Misslyckades med att spara scheman. Scheman inte sparade.");
		Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_TITLE.setTranslation("Laddning misslyckades");
		Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_SCHEDULES_MESSAGE.setTranslation("Misslyckades med att ladda scheman. \nS�kerhetskopiera ASQLSavedSchedules innan du st�nger av programmet annars riskerar du att f�rlora alla scheman.");
		Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_CONNECTIONS_MESSAGE.setTranslation("Misslyckades med att ladda databaser. \nS�kerhetskopiera ASQLSavedConnections innan du st�nger av programmet annars riskerar du att f�rlora alla databaskopplingar.");
		Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_OPTIONS_MESSAGE.setTranslation("Misslyckades med att ladda inst�llningar. �terst�ller till standardinst�llningar.");
		Translation.ERROR_MESSAGE_UNABLE_TO_START_SCHEDULE_MESSAGE.setTranslation("Misslyckades med att skapa schema. Kunde inte skapa separat process.");
		Translation.ERROR_MESSAGE_UNABLE_TO_START_SCHEDULE_TITLE.setTranslation("Schema misslyckades");
		Translation.ERROR_MESSAGE_UNABLE_TO_OPEN_FILE.setTranslation("Misslyckades med att hitta/�ppna filen.");
		Translation.ERROR_MESSAGE_UNABLE_TO_CLOSE_FILE.setTranslation("Misslyckades med att st�nga filen..");
		Translation.ERROR_MESSAGE_UNABLE_TO_WRITE_FILE.setTranslation("Misslyckades med att skriva till filen.");
		Translation.ERROR_MESSAGE_INVALID_INPUT_TITLE.setTranslation("Ogiltig input");
		Translation.ERROR_MESSAGE_INVALID_NAME.setTranslation("Ogiltigt namn. M�ste inneh�lla minst ett tecken.");
		Translation.ERROR_MESSAGE_INVALID_HOSTNAME.setTranslation("Ogiltigt v�rdnamn (hostname). M�ste inneh�lla minst ett tecken.");
		Translation.ERROR_MESSAGE_INVALID_SID.setTranslation("Ogiltig SID. M�ste inneh�lla minst ett tecken.");
		Translation.ERROR_MESSAGE_INVALID_PORT.setTranslation("Ogiltig port, m�ste vara ett tal och inneh�lla minst ett tecken");
		Translation.ERROR_MESSAGE_INVALID_USERNAME.setTranslation("Ogiltigt anv�ndarenamn. M�ste inneh�lla minst ett tecken.");
		Translation.ERROR_MESSAGE_INVALID_PASSWORD.setTranslation("Ogiltigt l�senord. M�ste inneh�lla minst ett tecken.");
		Translation.ERROR_MESSAGE_CONNECTION_CLOSED.setTranslation("Uppkoppling till databasen avbr�ts ov�ntat. V�nligen kontrollera din internetuppkoppling.");
		Translation.ERROR_MESSAGE_INVALID_SQL.setTranslation("Ogiltigt SQL skript.");
		Translation.ERROR_MESSAGE_ATTEMPT.setTranslation("F�rs�k:");
		Translation.ERROR_MESSAGE_EXCEPTION_MESSAGE.setTranslation("Felmeddelande:");
		
		//Gui information messages
		Translation.INFORMATION_MESSAGE_SUCCESS.setTranslation("Slutf�rt");
		Translation.INFORMATION_MESSAGE_RESULT_POSTED.setTranslation("Resultat skapat.");
		Translation.INFORMATION_MESSAGE_SCHEDULE_CREATED_MESSAGE.setTranslation("Schema skapat. N�sta k�rning:");
		Translation.INFORMATION_MESSAGE_SCHEDULE_CREATED_TITLE.setTranslation("Schema skapat");
		Translation.INFORMATION_MESSAGE_CONFIRM_REMOVE_TITLE.setTranslation("Ta bort");
		Translation.INFORMATION_MESSAGE_CONFIRM_REMOVE_MESSAGE.setTranslation("�r du s�ker p� att du vill ta bort?");
		Translation.INFORMATION_MESSAGE_NEED_INPUT.setTranslation("Beh�ver input om:");
		
		//Query status
		Translation.QUERY_STATUS_SCRIPT_TITEL.setTranslation("Skript:");
		Translation.QUERY_STATUS_MESSAGE_TITLE.setTranslation("Status:");
		Translation.QUERY_STATUS_NEXT_RUN.setTranslation("Kontrollerar n�sta k�rning...");
		Translation.QUERY_STATUS_SLEEP_THREAD.setTranslation("Inv�ntar n�sta k�rning, frig�r process...");
		Translation.QUERY_STATUS_INPUT_REQUIREMENT.setTranslation("Kontrollerar krav p� input...");
		Translation.QUERY_STATUS_CONNECTING.setTranslation("Kontaktar databasen...");
		Translation.QUERY_STATUS_SENDING_QUERY.setTranslation("Skickar skript...");
		Translation.QUERY_STATUS_POSTING_RESULTS.setTranslation("Bearbetar resultat...");
		Translation.QUERY_STATUS_UPDATING_SCHEDULE.setTranslation("Uppdaterar schema...");
		Translation.QUERY_STATUS_DISCONNECTING.setTranslation("Kopplar ner fr�n databasen...");
	}
}
