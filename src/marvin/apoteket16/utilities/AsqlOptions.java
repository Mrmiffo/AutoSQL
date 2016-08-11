package marvin.apoteket16.utilities;

import java.awt.Color;
import java.io.Serializable;

import marvin.apoteket16.locale.AsqlEnglishLocale;
import marvin.apoteket16.locale.AsqlLocale;
import marvin.apoteket16.locale.AsqlSwedishLocale;

public class AsqlOptions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5888378467831985044L;
	
	public static final String APP_NAME = "Auto SQL";
	public static final String APP_ICON_PATH = "marvin/apoteket16/res/AutoSQLLogo.png";
	public static final String SAVED_SCHEDULES_NAME = "ASQLSavedSchedules";
	public static final String SAVED_CONNECTIONS_NAME = "ASQLSavedConnections";
	public static final String SAVED_OPTIONS_NAME = "ASQLSavedOptions";
	
	public static final int SCREEN_WIDTH = 550;
	public static final int SCREEN_HEIGHT = 450;
	public static final Color SELECT_COLOR = new Color(144,195,212);
	public static final int TEXT_INPUT_HEIGHT = 20;
	

	
	private static final String DEFAULT_SOURCE_PATH = "";
	private static final String DEFAULT_TARGET_PATH = System.getProperty("user.home") + "/Desktop";
	private static final LocaleType DEFAULT_LOCALE = LocaleType.SWEDISH;
	
	private String selectedDefaultSourcePath;
	private String selectedDefaultTargetPath;
	private AsqlLocale activeLocale;
	private LocaleType currentLocale;
	
	public enum LocaleType{
		SWEDISH ("Svenska"),
		ENGLISH ("English");
		
		String name;
		
		LocaleType(String name){
			this.name= name;
		}
		
		@Override
		public String toString(){
			return name;
		}
		
	}
	
	public AsqlOptions(){
		this(DEFAULT_LOCALE);
	}
	
	public AsqlOptions(LocaleType locale){
		setDefaultSourcePath(DEFAULT_SOURCE_PATH);
		setDefaultTargetPath(DEFAULT_TARGET_PATH);
		setLocale(locale);
	}

	public String getDefaultSourcePath() {
		return selectedDefaultSourcePath;
	}

	public void setDefaultSourcePath(String defaultSourcePath) {
		this.selectedDefaultSourcePath = defaultSourcePath;
	}

	public String getDefaultTargetPath() {
		return selectedDefaultTargetPath;
	}

	public void setDefaultTargetPath(String defaultTargetPath) {
		this.selectedDefaultTargetPath = defaultTargetPath;
	}
	
	public void setLocale(LocaleType newLocale){
		currentLocale = newLocale;
		updateLocale();
	}
	
	public AsqlLocale getActiveLocale(){
		if (activeLocale == null) updateLocale();
		return activeLocale;
	}
	
	public LocaleType getSelectedLocale(){
		return currentLocale;
	}
	
	public void restoreDefaults(){
		setDefaultSourcePath(DEFAULT_SOURCE_PATH);
		setDefaultTargetPath(DEFAULT_TARGET_PATH);
		currentLocale = DEFAULT_LOCALE;
	}
	
	public void updateLocale(){
		if(currentLocale == LocaleType.ENGLISH){
			activeLocale = new AsqlEnglishLocale();
		} else if (currentLocale == LocaleType.SWEDISH){
			activeLocale = new AsqlSwedishLocale();
		}
	}
	
	public void prepareToSave(){
		activeLocale = null;
	}
	
}
