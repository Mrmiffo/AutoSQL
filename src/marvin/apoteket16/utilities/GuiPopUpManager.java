package marvin.apoteket16.utilities;

import javax.swing.JOptionPane;

public class GuiPopUpManager {
	
	public static void showErrorMessage(String message, String title){
		JOptionPane.showMessageDialog(null, message,title, JOptionPane.ERROR_MESSAGE);
	}

	public static void showInformationMessage(String message, String title){
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static String showInputMessage(String message){
		return JOptionPane.showInputDialog(null, message);
	}
	
	/**
	 * The value returned by user pressing OK in the showOkCancelMessage
	 */
	public static final int OK_MESSAGE_VALUE = 0;
	/**
	 * The value returned by user pressing Cancel in the showOkCancelMessage
	 */
	public static final int CANCEL_MESSAGE_VALUE = 2;
	/**
	 * Displays a dialog box showing a message with ok and cancel alternatives.
	 * @param message
	 * @param title
	 * @return OK_MESSAGE_VALUE (0) if user press Ok, CANCEL_MESSAGE_VALUE (2) if user press cancel.
	 */
	public static int showOkCancelMessage(String message, String title){
		return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION);
	}
}
