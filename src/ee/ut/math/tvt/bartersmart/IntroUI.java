package ee.ut.math.tvt.bartersmart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.*;

public class IntroUI extends JFrame {
	JTextArea textArea1 = new JTextArea();
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private JPanel kontrollPaneel;

	public IntroUI() throws IOException {
		super("About Bartersmart BeerPOS");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Loon vajalikud paneelid
		final JPanel raamiPaneel = new JPanel();
		this.setMinimumSize(new Dimension(750, 525));
		raamiPaneel.setLayout(new BorderLayout());
		kontrollPaneel = new JPanel();
		kontrollPaneel.setLayout(new FlowLayout(FlowLayout.LEADING, 15, 15));

		textArea1 = new JTextArea();
		// Set the default text for the text area
		textArea1.setEditable(false);
		textArea1.setText("Information on Bartersmart BeerPOS software\n");
		// If text doesn't fit on a line, jump to the next
		textArea1.setLineWrap(true);
		// Makes sure that words stay intact if a line wrap occurs
		textArea1.setWrapStyleWord(true);
		// Adds scroll bars to the text area ----------
		Dimension d = new Dimension(220, 150);
		textArea1.setPreferredSize(d);
		// panen komponendid paneelidele ja alampaneelid ülempaneelidele
		kontrollPaneel.add(textArea1);
		
		Properties appProp = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();   
		InputStream stream = loader.getResourceAsStream("application.properties");
		appProp.load(stream);
		Properties verProp = new Properties();
		loader = Thread.currentThread().getContextClassLoader();   
		stream = loader.getResourceAsStream("version.properties");
		verProp.load(stream);
		stream.close();

		textArea1.append("Team name: " + appProp.getProperty("team_name")+"\n");
		textArea1.append("Team leader: " + appProp.getProperty("team_leader")+"\n");
		textArea1.append("Team leader email: " + appProp.getProperty("team_leader_email")+"\n");
		textArea1.append("Team members: " + appProp.getProperty("team_members")+"\n");
		textArea1.append("Software version: " + verProp.getProperty("build.number")+"\n");

		kontrollPaneel.setPreferredSize(new Dimension(250, 0));
		raamiPaneel.add(kontrollPaneel, BorderLayout.WEST);
		BufferedImage myPicture = ImageIO.read(new File("logo.jpg"));
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		raamiPaneel.add(picLabel);
		this.add(raamiPaneel);
		this.pack();
		this.setVisible(true);
	}
}