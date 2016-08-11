package marvin.apoteket16.utilities;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import marvin.apoteket16.locale.AsqlLocale.Translation;
import marvin.apoteket16.utilities.AsqlOptions.LocaleType;

/**
 * Options class containing static values for the gui settings.
 * @author Anton
 *
 */
public class GuiOptions extends JPanel{

	private static final long serialVersionUID = -1995176797909049999L;

	private final JTextField targetPathField 				= new JTextField();
	private final JTextField sourcePathField 				= new JTextField();
	private final JComboBox<LocaleType> localeChooser 		= new JComboBox<LocaleType>();
	private final JButton saveButton 						= new JButton(Translation.SAVE_BUTTON.toString());
	
	private final AsqlOptions options;
	
	public GuiOptions(AsqlOptions options){
		this.options = options;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel sourcePanel = initTextInputPanel(Translation.OPTIONS_DEFAULT_SOURCE_TITLE.toString(), sourcePathField);
		JPanel targetPanel = initTextInputPanel(Translation.OPTIONS_DEFAULT_TARGET_TITLE.toString(), targetPathField);
		JPanel selectLocalePanel = initLocalePanel();
		JPanel buttonPanel = initButtonPanel();
		sourcePathField.setText(options.getDefaultSourcePath());
		targetPathField.setText(options.getDefaultTargetPath());
		this.add(sourcePanel);
		this.add(targetPanel);
		this.add(selectLocalePanel);
		this.add(Box.createVerticalGlue());
		this.add(buttonPanel);
	}

	private JPanel initLocalePanel() {
		//Setup the localeChooser

		LocaleType[] locales = LocaleType.values();
		for (int i = 0; i < locales.length;i++){
			localeChooser.addItem(locales[i]);
		}
		localeChooser.setSelectedItem(options.getSelectedLocale());
		localeChooser.setMaximumSize(new Dimension(Integer.MAX_VALUE, AsqlOptions.TEXT_INPUT_HEIGHT));
		//Create panel and add componentes
		JPanel panel= new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(createTitlePanel(Translation.OPTIONS_SELECT_LOCALE_TITEL.toString()));
		panel.add(localeChooser);
		panel.add(Box.createVerticalGlue());
		return panel;
	}

	private JPanel initTextInputPanel(String title, final JTextField inputField) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		//Setup sub panel
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
		inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, AsqlOptions.TEXT_INPUT_HEIGHT));
		inputField.setMinimumSize(new Dimension(100, AsqlOptions.TEXT_INPUT_HEIGHT));
		
		inputPanel.add(inputField);
		JButton browseButton = new JButton(Translation.BROWSE_BUTTON.toString());
		browseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				browseButtonAction(inputField);
			}
		});
		
		//Add components to subpanel
		inputPanel.add(browseButton);
		inputPanel.add(Box.createHorizontalGlue());
		
		//Add sup panels to main panel and return
		panel.add(createTitlePanel(title));
		panel.add(inputPanel);
		return panel;
	}
	
	private void browseButtonAction(JTextField inputField) {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle(Translation.BROWSE_DIALOG_FOLDER_SELECT_TITLE.toString());
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		    inputField.setText(chooser.getSelectedFile().getPath());
		}
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
		
		JButton restoreButton = new JButton(Translation.RESTORE_DEFAULTS_BUTTON.toString());
		restoreButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				restoreButtonAction();
				
			}
		});
	
		
		//Add components and return
		panel.add(Box.createHorizontalGlue());
		panel.add(restoreButton);
		panel.add(Box.createRigidArea(new Dimension(10,0)));
		panel.add(saveButton);
		return panel;
	}
	
	protected void restoreButtonAction() {
		if (GuiPopUpManager.showOkCancelMessage("Are you sure you want to restore defaults?", "Restore defaults") == GuiPopUpManager.OK_MESSAGE_VALUE){
			options.restoreDefaults();
			sourcePathField.setText(options.getDefaultSourcePath());
			targetPathField.setText(options.getDefaultTargetPath());
			localeChooser.setSelectedItem(options.getSelectedLocale());
			saveButtonAction();
		}
	}

	protected void saveButtonAction() {
		options.setDefaultSourcePath(sourcePathField.getText());
		options.setDefaultTargetPath(targetPathField.getText());
		//TODO Implement local chooser
		options.setLocale((LocaleType)localeChooser.getSelectedItem());
		options.prepareToSave();
		try {
			AsqlFileSaver.saveObject(options, AsqlOptions.SAVED_OPTIONS_NAME);
			GuiPopUpManager.showInformationMessage(Translation.INFORMATION_MESSAGE_CHANGES_SAVED.toString(), Translation.INFORMATION_MESSAGE_SUCCESS.toString());
		} catch (IOException e) {
			GuiPopUpManager.showErrorMessage(Translation.ERROR_MESSAGE_UNABLE_TO_SAVE_OPTIONS_MESSAGE.toString(), Translation.ERROR_MESSAGE_UNABLE_TO_SAVE_TITLE.toString());
			e.printStackTrace();
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
	
}
