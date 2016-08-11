package marvin.apoteket16.main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import marvin.apoteket16.locale.AsqlLocale.Translation;
import marvin.apoteket16.schedule.AsqlScheduleManager;
import marvin.apoteket16.schedule.GuiSchedule;
import marvin.apoteket16.schedule.GuiScheduleList;
import marvin.apoteket16.sql.AsqlConnectionManager;
import marvin.apoteket16.sql.UnableToDisconnectException;
import marvin.apoteket16.utilities.AsqlFileLoader;
import marvin.apoteket16.utilities.AsqlFileSaver;
import marvin.apoteket16.utilities.AsqlOptions;
import marvin.apoteket16.utilities.GuiOptions;
import marvin.apoteket16.utilities.GuiPopUpManager;

/**
 * The controller of the main GUI frame. Handles the tabs and loads all the data from saved files.
 * @author Anton
 *
 */
public class GuiController {
	
	private final JFrame frame = new JFrame(AsqlOptions.APP_NAME);
	private final JTabbedPane mainPanel = new JTabbedPane();
	
	private AsqlScheduleManager scheduleManager;
	private AsqlConnectionManager connectionManager;
	private AsqlOptions options;
	
	private final GuiOptions optionsScreen;
	private final GuiSchedule scheduleScreen;
	private final GuiScheduleList scheduleListScreen;
	

	
	public GuiController(){
		//Initialize saved data.
		loadSavedSchedules();
		loadSavedConnections();
		loadSavedOptions();
		
		//Create the tabs
		optionsScreen = new GuiOptions(options);
		scheduleScreen = new GuiSchedule(scheduleManager, connectionManager, options);
		scheduleListScreen = new GuiScheduleList(scheduleManager);

		mainPanel.addTab(Translation.CREATE_SCHEDULE_TAB.toString(), scheduleScreen);
		mainPanel.addTab(Translation.SHOW_SCHEDULES_TAB.toString(), scheduleListScreen);
		mainPanel.addTab(Translation.OPTIONS_TAB.toString(), optionsScreen);
		mainPanel.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				//Only update the list in schedule list tab if it is selected
				if (mainPanel.getSelectedComponent().equals(scheduleListScreen)){
					scheduleListScreen.updateList();
				}
			}
		});
		
		initializeFrame();
		frame.add(mainPanel);
	}

	private void loadSavedOptions() {
		try {
			options = (AsqlOptions) AsqlFileLoader.loadFile(AsqlOptions.SAVED_OPTIONS_NAME);
		} catch (ClassNotFoundException e1) {
			GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_OPTIONS_MESSAGE.toString(), Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_TITLE.toString());
		} catch (IOException e1) {
			GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_OPTIONS_MESSAGE.toString(), Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_TITLE.toString());
		}
		if (options == null){
			options = new AsqlOptions();
		}
		options.updateLocale();
	}

	private void loadSavedConnections() {
		try {
			connectionManager = (AsqlConnectionManager) AsqlFileLoader.loadFile(AsqlOptions.SAVED_CONNECTIONS_NAME);
		} catch (ClassNotFoundException e1) {
			GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_CONNECTIONS_MESSAGE.toString(), Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_TITLE.toString());
		} catch (IOException e1) {
			GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_CONNECTIONS_MESSAGE.toString(), Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_TITLE.toString());
		}
		if (connectionManager == null){
			connectionManager = new AsqlConnectionManager();
		}
		
	}

	private void loadSavedSchedules() {
		try {
			scheduleManager = (AsqlScheduleManager) AsqlFileLoader.loadFile(AsqlOptions.SAVED_SCHEDULES_NAME);
		} catch (ClassNotFoundException e1) {
			GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_SCHEDULES_MESSAGE.toString(), Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_TITLE.toString());
		} catch (IOException e1) {
			GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_SCHEDULES_MESSAGE.toString(), Translation.ERROR_MESSAGE_UNABLE_TO_LOAD_TITLE.toString());
		}
		try {
			if (scheduleManager == null){
				scheduleManager = new AsqlScheduleManager();
			} else {
				scheduleManager.reloadThreads();
			}
		} catch (IOException e){
			GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_UNABLE_TO_START_SCHEDULE_MESSAGE.toString(), Translation.ERROR_MESSAGE_UNABLE_TO_START_SCHEDULE_TITLE.toString());
		}
	}

	private void initializeFrame() {
		//Override the default close operation to close all connections to DB before exiting the program.
		WindowListener exitListender = new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				try {
					connectionManager.closeConnections();
				} catch (UnableToDisconnectException e) {
					GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_UNABLE_TO_DISCONNECT_MESSAGE.toString(), Translation.ERROR_MESSAGE_UNABLE_TO_DISCONNECT_TITLE.toString());
				}
				saveConnections();
				//Stop threads and save schedules on close
				scheduleManager.shutDownThreads();
				saveSchedules();
				System.exit(0);
			}

			private void saveSchedules() {
				try {
					AsqlFileSaver.saveObject(scheduleManager, AsqlOptions.SAVED_SCHEDULES_NAME);
				} catch (IOException e) {
					GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_UNABLE_TO_SAVE_SCHEDULES_MESSAGE.toString(), Translation.ERROR_MESSAGE_UNABLE_TO_SAVE_TITLE.toString());
				}
			}

			private void saveConnections() {
				try {
					AsqlFileSaver.saveObject(connectionManager, AsqlOptions.SAVED_CONNECTIONS_NAME);
				} catch (IOException e) {
					GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_UNABLE_TO_SAVE_CONNECTIONS_MESSAGE.toString(), Translation.ERROR_MESSAGE_UNABLE_TO_SAVE_TITLE.toString());
				}
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		frame.addWindowListener(exitListender);
		
		//Set icon
		try {
			BufferedImage input = ImageIO.read(ClassLoader.getSystemResource(AsqlOptions.APP_ICON_PATH));
			ImageIcon img = new ImageIcon(input);
			frame.setIconImage(img.getImage());
		} catch (IOException e) {
			//Non critical error, program will function even without the icon.
			e.printStackTrace();
		}

		//Set window options
		frame.setSize(AsqlOptions.SCREEN_WIDTH, AsqlOptions.SCREEN_HEIGHT);
		frame.setVisible(true);
		frame.setResizable(false);


	}
}
