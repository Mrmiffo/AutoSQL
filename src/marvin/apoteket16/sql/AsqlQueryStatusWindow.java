package marvin.apoteket16.sql;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import marvin.apoteket16.locale.AsqlLocale.Translation;
import marvin.apoteket16.utilities.AsqlOptions;

public class AsqlQueryStatusWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8305929119006363453L;

	private JLabel status;
	
	public AsqlQueryStatusWindow(String title){
		super();
		this.setTitle(title);
		this.setSize(200, 150);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		BufferedImage input = null;
		try {
			input = ImageIO.read(ClassLoader.getSystemResource(AsqlOptions.APP_ICON_PATH));
			this.setIconImage(new ImageIcon(input).getImage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(new JLabel(Translation.QUERY_STATUS_SCRIPT_TITEL.toString()));
		panel.add(new JLabel(title));
		panel.add(new JLabel(Translation.QUERY_STATUS_MESSAGE_TITLE.toString()));
		status = new JLabel();
		panel.add(status);
		this.add(panel);
		this.setResizable(false);
	}
	
	public void setMessage(String newMessage){
		status.setText(newMessage);
		this.setVisible(true );
	}
}
