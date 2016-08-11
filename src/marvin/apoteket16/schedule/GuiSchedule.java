package marvin.apoteket16.schedule;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;
import com.toedter.components.JSpinField;

import marvin.apoteket16.locale.AsqlLocale.Translation;
import marvin.apoteket16.schedule.AsqlSchedule.Interval;
import marvin.apoteket16.sql.AsqlConnection;
import marvin.apoteket16.sql.AsqlConnectionManager;
import marvin.apoteket16.sql.AsqlQuery;
import marvin.apoteket16.sql.AsqlXlsPrinterRM;
import marvin.apoteket16.sql.InterfaceAsqlResultManager;
import marvin.apoteket16.utilities.AsqlOptions;
import marvin.apoteket16.utilities.GuiPopUpManager;


public class GuiSchedule extends JPanel {

	private static final long serialVersionUID = -8152312241200099598L;
	
	private final JComboBox<String> filePicker 			= new JComboBox<String>();
	private final JComboBox<String> connectionPicker 	= new JComboBox<String>();
	private final JCalendar datePicker 					= new JCalendar();
	private final JSpinField hour 						= new JSpinField();
	private final JSpinField minute 					= new JSpinField();
	private final ButtonGroup repeatChooser 			= new ButtonGroup();
	private final JRadioButton daily 					= new JRadioButton(Translation.CREATE_SCHEDULE_REPEAT_DAILY.toString());
	private final JRadioButton weekly 					= new JRadioButton(Translation.CREATE_SCHEDULE_REPEAT_WEEKLY.toString());
	private final JRadioButton monthly 					= new JRadioButton(Translation.CREATE_SCHEDULE_REPEAT_MONTHLY.toString());
	private final JRadioButton custom 					= new JRadioButton(Translation.CREATE_SCHEDULE_REPEAT_EVERY.toString());
	private final JSpinField customRepeat 				= new JSpinField();
	private final JTextField targetPicker 				= new JTextField();
	private String fileLocation;
	private final JButton saveButton 					= new JButton(Translation.SAVE_SCHEDULE_BUTTON.toString());
	private final JButton runButton						= new JButton(Translation.RUN_NOW_BUTTON.toString());
	
	private static int DEFAULT_START_HOUR = 8;
	private static int DEFAULT_START_MINUTE = 0;
	
	private final AsqlScheduleManager scheduleManager;
	private final AsqlConnectionManager connections;
	private final AsqlOptions options;

	public GuiSchedule(AsqlScheduleManager scheduleManager, AsqlConnectionManager connections, AsqlOptions options){
		this.scheduleManager = scheduleManager;
		this.connections = connections;
		this.options = options;
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		
		JPanel filePanel = initFilePanel();
		
		JPanel connectionPanel = initConnectionPanel();
		
		JPanel datePanel = initDatePanel();
		
		JPanel targetPanel = initTargetPanel();
		
		JPanel buttonPanel = initButtonPanel();
		
		
		this.add(filePanel);
		this.add(connectionPanel);
		this.add(targetPanel);
		this.add(datePanel);
		this.add(Box.createVerticalGlue());
		this.add(buttonPanel);
		clearButtonAction();

	}

	private JPanel initConnectionPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		updateConnectionPicker();
		
		JPanel chooserPanel = new JPanel();
		chooserPanel.setLayout(new BoxLayout(chooserPanel, BoxLayout.X_AXIS));
		connectionPicker.setMaximumSize(new Dimension(Integer.MAX_VALUE, AsqlOptions.TEXT_INPUT_HEIGHT));
		
		//Create buttons
		JButton addConnection = new JButton(Translation.ADD_BUTTON.toString());
		addConnection.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addConnectionButtonAction();
				
			}
		});
		
		JButton removeConnection = new JButton(Translation.DELETE_BUTTON.toString());
		removeConnection.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				removeConnectionButtonAction();
				
			}
		});
		
		//Add components to chooser sub panel
		chooserPanel.add(connectionPicker);
		chooserPanel.add(addConnection);
		chooserPanel.add(removeConnection);
		
		//Add sub panels to main panel and return the panel.
		panel.add(createTitlePanel(Translation.CREATE_SCHEDULE_CONNECTION_TITLE.toString()));
		panel.add(chooserPanel);
		return panel;
	}
	
	private void removeConnectionButtonAction() {
		int confirmValue = GuiPopUpManager.showOkCancelMessage(Translation.INFORMATION_MESSAGE_CONFIRM_REMOVE_MESSAGE.toString(), Translation.INFORMATION_MESSAGE_CONFIRM_REMOVE_TITLE.toString());
		if (confirmValue == GuiPopUpManager.OK_MESSAGE_VALUE){
			connections.removeConnection(connections.getConnectionInstance((String)connectionPicker.getSelectedItem()));
			updateConnectionPicker();
			updateSaveButton();
		}
	}

	private void addConnectionButtonAction() {
		String name = JOptionPane.showInputDialog(Translation.CREATE_CONNECTION_NAME.toString());
		if (name == null) return; 
		if (name.length() == 0) {
			GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_INVALID_NAME.toString(), Translation.ERROR_MESSAGE_INVALID_INPUT_TITLE.toString());
			return;
		}
		String hostName = JOptionPane.showInputDialog(Translation.CREATE_CONNECTION_HOSTNAME.toString());
		if (hostName == null) return;
		if (hostName.length() == 0) {
			GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_INVALID_HOSTNAME.toString(), Translation.ERROR_MESSAGE_INVALID_INPUT_TITLE.toString());
			return;
		}
		int portInt = 0;
		String port = JOptionPane.showInputDialog(Translation.CREATE_CONNECTION_PORT);
		if (port == null) return;
		try {
			portInt = Integer.parseInt(port);
		} catch (NumberFormatException e) {
			GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_INVALID_PORT.toString(), Translation.ERROR_MESSAGE_INVALID_INPUT_TITLE.toString());
		}
		
		String sid = JOptionPane.showInputDialog(Translation.CREATE_CONNECTION_SID);
		if (sid == null) return;
		if (sid.length() == 0) {
			GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_INVALID_SID.toString(), Translation.ERROR_MESSAGE_INVALID_INPUT_TITLE.toString());
			return;
		}
		String username = JOptionPane.showInputDialog(Translation.CREATE_CONNECTION_USERNAME);
		if (username == null) return;
		if (username.length() == 0) {
			GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_INVALID_USERNAME.toString(), Translation.ERROR_MESSAGE_INVALID_INPUT_TITLE.toString());
			return;
		}
		String pw = JOptionPane.showInputDialog(Translation.CREATE_CONNECTION_PASSWORD);
		if (pw == null) return;
		if (hostName.length() == 0) {
			GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_INVALID_PASSWORD.toString(), Translation.ERROR_MESSAGE_INVALID_INPUT_TITLE.toString());
			return;
		}
		AsqlConnection con = new AsqlConnection(name, username,pw,hostName,portInt, sid);
		//TODO Connection checker removed during test.
//		try {
//			con.connect();
//		} catch (UnableToConnectException e) {
//			GuiPopUpManager.showErrorMessage("Unable to connect to database, connection not created", "Invalid connection");
//			return;
//		} finally {
//			try {
//				con.disconnect();
//			} catch (UnableToDisconnectException e) {
//
//			}
//		}
		connections.addConnection(con);
		updateConnectionPicker();
		connectionPicker.setSelectedIndex(connectionPicker.getItemCount()-1);
		updateSaveButton();
	}

	private JPanel initDatePanel() {
		//Setup sub panels and components 
		//Date panel contain the date chooser
		JPanel datePanel = new JPanel();
		datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));
		datePicker.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1000));
		datePicker.setDate(new Date());
		datePanel.add(datePicker);
		datePanel.add(Box.createHorizontalGlue());
		
		//Time panel contain the two spin fields for hour and minutes.
		hour.setMaximumSize(new Dimension(10000,AsqlOptions.TEXT_INPUT_HEIGHT));
		hour.setMaximum(23);
		hour.setMinimum(0);
		hour.setValue(DEFAULT_START_HOUR);
		
		minute.setMaximumSize(new Dimension(10000,AsqlOptions.TEXT_INPUT_HEIGHT));
		minute.setMaximum(59);
		minute.setMinimum(0);
		minute.setValue(DEFAULT_START_MINUTE);
		
		repeatChooser.add(daily);
		repeatChooser.add(weekly);
		repeatChooser.add(monthly);
		repeatChooser.add(custom);
		
		customRepeat.setValue(1);
		customRepeat.setMaximumSize(new Dimension(10000,AsqlOptions.TEXT_INPUT_HEIGHT));
		customRepeat.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("value")) custom.setSelected(true);
			}
		});
		
		JPanel repeatChooserPanel = new JPanel();
		repeatChooserPanel.setLayout(new BoxLayout(repeatChooserPanel, BoxLayout.X_AXIS));
		repeatChooserPanel.add(new JLabel(Translation.CREATE_SCHEDULE_REPEAT_TITLE.toString()));
		repeatChooserPanel.add(daily);
		repeatChooserPanel.add(weekly);
		repeatChooserPanel.add(monthly);
		repeatChooserPanel.add(custom);
		repeatChooserPanel.add(customRepeat);
		repeatChooserPanel.add(new JLabel(Translation.CREATE_SCHEDULE_REPEAT_HOUR.toString()));

		
		JPanel timePanel = new JPanel();
		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.X_AXIS));
		timePanel.add(new JLabel(Translation.CREATE_SCHEDULE_START_TIME.toString()));
		timePanel.add(hour);
		timePanel.add(minute);
		timePanel.add(Box.createHorizontalGlue());
		timePanel.add(repeatChooserPanel);

		//Add sub components to main panel and return
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(createTitlePanel(Translation.CREATE_SCHEDULE_START_DATE_TITLE.toString()));
		panel.add(datePanel);
		panel.add(timePanel);
		return panel;
	}

	private JPanel initFilePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		fileLocation = options.getDefaultSourcePath();
		
		//Create sub panel and set layout
		JPanel dropDownPanel = new JPanel();
		dropDownPanel.setLayout(new BoxLayout(dropDownPanel, BoxLayout.X_AXIS));
		
		//Update the items to display in the file picker component and configure components
		updateFilePicker();
		filePicker.setMaximumSize(new Dimension(Integer.MAX_VALUE, AsqlOptions.TEXT_INPUT_HEIGHT));
		filePicker.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				updateSaveButton();
				
			}
			
		});
		JButton browseButton = new JButton(Translation.BROWSE_BUTTON.toString());
		browseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				browseFileLocationButtonAction();
				
			}
		});
		
		//Add components to sub panel.
		dropDownPanel.add(filePicker);
		dropDownPanel.add(browseButton);
		dropDownPanel.add(Box.createHorizontalGlue());
		
		//Add sub panels to main panel and return
		panel.add(createTitlePanel(Translation.CREATE_SCHEDULE_SOURCE_TITLE.toString()));
		panel.add(dropDownPanel);
		return panel;
	}

	private JPanel initTargetPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		//Setup sub panel
		JPanel dropDownPanel = new JPanel();
		dropDownPanel.setLayout(new BoxLayout(dropDownPanel, BoxLayout.X_AXIS));
		targetPicker.setMaximumSize(new Dimension(Integer.MAX_VALUE, AsqlOptions.TEXT_INPUT_HEIGHT));
		targetPicker.setMinimumSize(new Dimension(100, AsqlOptions.TEXT_INPUT_HEIGHT));
		targetPicker.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				updateSaveButton();
				
			}
			
		});
		dropDownPanel.add(targetPicker);
		JButton browseButton = new JButton(Translation.BROWSE_BUTTON.toString());
		browseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				targetPickerBrowseButtonAction();
			}
		});
		
		//Add components to subpanel
		dropDownPanel.add(browseButton);
		dropDownPanel.add(Box.createHorizontalGlue());
		
		//Add sup panels to main panel and return
		panel.add(createTitlePanel(Translation.CREATE_SCHEDULE_TARGET_TITLE.toString()));
		panel.add(dropDownPanel);
		return panel;
	}
	
	private JPanel initButtonPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		//Setup components
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveButtonAction();
				
			}
		});
		
		JButton clearButton = new JButton(Translation.CLEAR_BUTTON.toString());
		clearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clearButtonAction();
				
			}
		});
		
		runButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				runButtonAction();
				
			}
		});
	
		
		//Add components and return
		panel.add(Box.createHorizontalGlue());
		panel.add(clearButton);
		panel.add(Box.createRigidArea(new Dimension(10,0)));
		panel.add(runButton);
		panel.add(saveButton);
		return panel;
	}
	
	private void runButtonAction() {

		AsqlConnection connection = connections.getConnectionInstance((String)connectionPicker.getSelectedItem());
		InterfaceAsqlScheduleRepeater repeater = new AsqlSingleRunSR();
		String targetPath = targetPicker.getText();
		String fileName = (String)filePicker.getSelectedItem();
		String scriptPath = fileLocation+"/"+fileName;
		//Remove SQL to create name
		//TODO Introduce name chooser
		String outputName = fileName.substring(0, fileName.length()-4);
		InterfaceAsqlResultManager results = new AsqlXlsPrinterRM(targetPath, outputName);
		try {
			Thread thread = new Thread(new AsqlQuery(connection, repeater, scriptPath,results));
			thread.start();
			scheduleManager.addTempThread(thread);
		} catch (IOException e) {
			GuiPopUpManager.showErrorMessage("Unable to read script, file not found or already in use", "IO Exception");
		}
		
	}

	private void updateFilePicker(){
		filePicker.removeAllItems();
		if (fileLocation != null && !fileLocation.equals("")){
			File folder = new File(fileLocation);
			File[] files = folder.listFiles();
			for (int i = 0; i<files.length;i++){
				if(files[i].getName().contains(".sql")){
					filePicker.addItem(files[i].getName());
				}
			}
		}
		if (filePicker.getItemCount() > 0){
			filePicker.setSelectedIndex(0);
		}
		updateSaveButton();
	}
	
	private void updateConnectionPicker() {
		connectionPicker.removeAllItems();
		for (String con: connections.getConnectionNames()){
			connectionPicker.addItem(con);
		}
		
	}

	private void browseFileLocationButtonAction() {
		JFileChooser chooser = new JFileChooser();
	    chooser.setDialogTitle(Translation.BROWSE_DIALOG_FOLDER_SELECT_TITLE.toString());
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);
	    
	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	        fileLocation = chooser.getSelectedFile().getPath();
	        updateFilePicker();
	    }
	}
	
	private void clearButtonAction(){
		if (filePicker.getItemCount()>0){
			filePicker.setSelectedIndex(0);
		}
		datePicker.setDate(new Date());
		hour.setValue(DEFAULT_START_HOUR);
		minute.setValue(DEFAULT_START_MINUTE);
		targetPicker.setText(options.getDefaultTargetPath());
		monthly.setSelected(true);
		updateSaveButton();
		
	}
	
	private void saveButtonAction(){
		String fileName = (String)filePicker.getSelectedItem();
		//Remove SQL to create name
		//TODO Introduce name chooser
		String name = fileName.substring(0, fileName.length()-4);
		//Create the date time
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(datePicker.getDate());
		cal.set(Calendar.HOUR_OF_DAY, hour.getValue());
		cal.set(Calendar.MINUTE, minute.getValue());
		cal.set(Calendar.SECOND, 0);
		
		Interval repeatInterval;
		if (daily.isSelected()){
			repeatInterval = Interval.DAILY;
		} else if (weekly.isSelected()){
			repeatInterval = Interval.WEEKLY;
		} else if (monthly.isSelected()){
			repeatInterval = Interval.MONTHLY;
		} else {
			repeatInterval = Interval.HOUR;
		}

		//TODO Implement endDate
		InterfaceAsqlScheduleRepeater repeater = new AsqlDateRepeaterSR(repeatInterval, cal.getTime(), null, customRepeat.getValue());
		InterfaceAsqlResultManager resultManager = new AsqlXlsPrinterRM(targetPicker.getText(), name);
		AsqlSchedule createdSchedule = new AsqlSchedule(name, fileLocation+"/"+fileName, targetPicker.getText(), connections.getConnectionInstance((String)connectionPicker.getSelectedItem()), repeater, resultManager);
		try {
			scheduleManager.addSchedule(createdSchedule);
		} catch (IOException e) {
			GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_UNABLE_TO_START_SCHEDULE_MESSAGE.toString(), Translation.ERROR_MESSAGE_UNABLE_TO_START_SCHEDULE_TITLE.toString());
		}
		GuiPopUpManager.showInformationMessage(Translation.INFORMATION_MESSAGE_SCHEDULE_CREATED_MESSAGE+" "+repeater.getNext(), Translation.INFORMATION_MESSAGE_SCHEDULE_CREATED_TITLE.toString());
	}
	
	private void updateSaveButton(){
		String fileName = (String)filePicker.getSelectedItem();
		String connection = (String)connectionPicker.getSelectedItem();
		String target = targetPicker.getText();
		if (fileName == null || connection == null || target == null){
			saveButton.setEnabled(false);
			runButton.setEnabled(false);
		} else {
			saveButton.setEnabled(true);
			runButton.setEnabled(true);
		}
	}
	
	//Method for creating a title for the gui components
	private JPanel createTitlePanel(String title){
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new BoxLayout(toReturn, BoxLayout.X_AXIS));
		toReturn.add(new JLabel(title));
		toReturn.add(Box.createHorizontalGlue());
		return toReturn;
	}


	private void targetPickerBrowseButtonAction() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle(Translation.BROWSE_DIALOG_FOLDER_SELECT_TITLE.toString());
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		    targetPicker.setText(chooser.getSelectedFile().getPath());
		}
	}

}
