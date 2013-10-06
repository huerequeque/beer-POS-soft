package ee.ut.math.tvt.bartersmart;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Intro {

	/**
	 * @param args
	 */
	static IntroUI myPOS;
	private static final Logger log = Logger.getLogger(Intro.class);

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DOMConfigurator.configure("etc/log4j.xml");
		myPOS = new IntroUI();
		log.info("Intro started");
	}

}
