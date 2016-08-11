package marvin.apoteket16.schedule;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import marvin.apoteket16.locale.AsqlLocale.Translation;
import marvin.apoteket16.utilities.AsqlOptions;
import marvin.apoteket16.utilities.GuiPopUpManager;

public class GuiScheduleList extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1442182276495207604L;
	
	private final AsqlScheduleManager scheduleManager;
	private final JList<AsqlSchedule> list;
	
	private final JButton removeButton;
	private final JButton detailsButton;
	
	
	public GuiScheduleList (AsqlScheduleManager scheduleManager){
		this.scheduleManager = scheduleManager;
		list = new JList<AsqlSchedule>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setCellRenderer(new AsqlScheduleListRenderer());
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				setButtonAvailability();
			}
		});
		
		
		
		//Add list to scrollpane
		JScrollPane scroller = new JScrollPane(list);
		//scroller.setPreferredSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		
		//Add button panel
		detailsButton = new JButton(Translation.DETAILS_BUTTON.toString());
		detailsButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				detailsButtonAction();
				
			}
			
		});
		
		removeButton = new JButton(Translation.DELETE_BUTTON.toString());
		removeButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				removeButtonAction();
				
			}
			
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(removeButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(10,0)));
		buttonPanel.add(detailsButton);
				
		//Add scrollpane to main pane
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(scroller);
		this.add(Box.createVerticalGlue());
		this.add(buttonPanel);
		updateList();
	}
	
	private void removeButtonAction() {
		int confirmValue = GuiPopUpManager.showOkCancelMessage(Translation.INFORMATION_MESSAGE_CONFIRM_REMOVE_MESSAGE.toString(), Translation.INFORMATION_MESSAGE_CONFIRM_REMOVE_TITLE.toString());
		if (confirmValue == GuiPopUpManager.OK_MESSAGE_VALUE){
			scheduleManager.removeSchedule(list.getModel().getElementAt(list.getSelectedIndex()));
			updateList();
		}
	}

	private void detailsButtonAction() {
		AsqlSchedule schedule = list.getModel().getElementAt(list.getSelectedIndex());

		String message = 	Translation.SCHEDULE_LIST_DETAILS_NAME.toString() + " " + schedule.getName() + 
							"\n"+ Translation.SCHEDULE_LIST_DETAILS_NEXT_RUN.toString()+" " + schedule.getNextRun() +
							"\n"+ Translation.SCHEDULE_LIST_DETAILS_REPEATING.toString()+" " + schedule.getInterval().toString()+
							"\n"+ Translation.SCHEDULE_LIST_DETAILS_SOURCE.toString()+" " + schedule.getSourcePath()+
							"\n"+ Translation.SCHEDULE_LIST_DETAILS_DATABASE.toString() +" " + schedule.getConnection().getName()+
							"\n"+ Translation.SCHEDULE_LIST_DETAILS_TARGET.toString()+ " " + schedule.getTargetPath()+
							"\n"+ Translation.SCHEDULE_LIST_DETAILS_OUTPUT.toString()+ " " + schedule.getResultManager().getName();
							
		GuiPopUpManager.showInformationMessage(message, schedule.getName());
	}
	
	private void setButtonAvailability(){
		boolean availability = !(list.getSelectedIndex() == -1);
		removeButton.setEnabled(availability);
		detailsButton.setEnabled(availability);
	}
	
	public void updateList(){
		//Add items to list
		DefaultListModel<AsqlSchedule> model = new DefaultListModel<AsqlSchedule>();
		for (AsqlSchedule s: scheduleManager.getSchedules()){
			model.addElement(s);
		}
		list.setModel(model);
		setButtonAvailability();
	}
	
	private class AsqlScheduleListRenderer implements ListCellRenderer<AsqlSchedule>{

		@Override
		public Component getListCellRendererComponent(JList<? extends AsqlSchedule> list, AsqlSchedule value, int index,
				boolean isSelected, boolean cellHasFocus) {
			JPanel cell = new JPanel();

			cell.setLayout(new BoxLayout(cell, BoxLayout.Y_AXIS));
			cell.setBorder(BorderFactory.createLineBorder(Color.black));
			
			JPanel upperPart = new JPanel();
			upperPart.setLayout(new BoxLayout(upperPart, BoxLayout.X_AXIS));
			JPanel lowerPart = new JPanel();
			lowerPart.setLayout(new BoxLayout(lowerPart, BoxLayout.X_AXIS));
			if (isSelected){
				upperPart.setBackground(AsqlOptions.SELECT_COLOR);
				lowerPart.setBackground(AsqlOptions.SELECT_COLOR);
			}
			
			//Setup upperPanel
			upperPart.add(new JLabel(Translation.SCHEDULE_LIST_DETAILS_NAME.toString()+ " " + value.getName()));
			upperPart.add(Box.createHorizontalGlue());
			
			//Setup lowerPanel
			lowerPart.add(new JLabel(Translation.SCHEDULE_LIST_DETAILS_NEXT_RUN.toString() +" " + value.getNextRun()));
			lowerPart.add(Box.createHorizontalGlue());
			
			cell.add(upperPart);
			cell.add(lowerPart);
			
			return cell;
		}
	}
}
