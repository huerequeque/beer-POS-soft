package ee.ut.math.tvt.bartersmart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.jgoodies.looks.windows.WindowsLookAndFeel;

public class IntroUI extends JFrame {
	JTextArea textArea1 = new JTextArea();
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public IntroUI() throws IOException {
		super("About Bartersmart BeerPOS");

		Properties appProp = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();   
		InputStream stream = loader.getResourceAsStream("application.properties");
		appProp.load(stream);
		Properties verProp = new Properties();
		loader = Thread.currentThread().getContextClassLoader();   
		stream = loader.getResourceAsStream("version.properties");
		verProp.load(stream);
		stream.close();
		
		// Loon vajalikud paneelid
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		
		final JComponent infoPanel = drawInfoPanel(appProp, verProp);
		panel.add(infoPanel);
		final JComponent picPanel = drawPicPanel(appProp);
		panel.add(picPanel);
		
		this.add(panel);
		
	    // set L&F to the nice Windows style
	    try {
	      UIManager.setLookAndFeel(new WindowsLookAndFeel());

	    } catch (UnsupportedLookAndFeelException e1) {
	    }

	    // size & location
	    int width = 600;
	    int height = 350;
	    setSize(width, height);
	    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    setLocation((screen.width - width) / 2, (screen.height - height) / 2);
	}

	private JComponent drawInfoPanel(Properties appProp, Properties verProp) throws IOException {

		// Create the panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		panel.setBorder(BorderFactory.createTitledBorder("About"));

		// == Add components to the panel

		panel.add(new JLabel("Team name:"));
		panel.add(new JLabel(appProp.getProperty("team_name")));

		panel.add(new JLabel("Team leader:"));
		panel.add(new JLabel(appProp.getProperty("team_leader")));

		panel.add(new JLabel("Team leader email:"));
		panel.add(new JLabel(appProp.getProperty("team_leader_email")));

		panel.add(new JLabel("Team members:"));
		panel.add(new JLabel(appProp.getProperty("team_members")));		
		
		panel.add(new JLabel("Software version:"));
		panel.add(new JLabel(verProp.getProperty("build.number")));
		
		return panel;
	}
	
	private JComponent drawPicPanel(Properties appProp) throws IOException{
		// Create the panel
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Logo"));
		
		BufferedImage myPicture = ImageIO.read(new File(appProp.getProperty("team_logo_file")));
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		panel.add(picLabel);
		return panel;
	}
}