package ee.ut.math.tvt.bartersmart;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import ee.ut.math.tvt.bartersmart.domain.controller.SalesDomainController;
import ee.ut.math.tvt.bartersmart.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.bartersmart.service.HibernateDataService;
import ee.ut.math.tvt.bartersmart.ui.ConsoleUI;
import ee.ut.math.tvt.bartersmart.ui.SalesSystemUI;

public class Intro {

	private static final Logger log = Logger.getLogger(Intro.class);
	private static final String MODE = "console";

	public static void main(String[] args) throws IOException {
		
		final HibernateDataService service = new HibernateDataService();
		final SalesDomainController domainController = new SalesDomainControllerImpl(service);
		DOMConfigurator.configure("etc/log4j.xml");

		if (args.length == 1 && args[0].equals(MODE)) {
			log.debug("Mode: " + MODE);

			ConsoleUI cui = new ConsoleUI(domainController);
			cui.run();
		} else {

			IntroUI introUI = new IntroUI();
			introUI.setVisible(true);
			introUI.setAlwaysOnTop(true);

			final SalesSystemUI ui = new SalesSystemUI(domainController);
			ui.setVisible(true);
			log.info("Intro started");

			introUI.setAlwaysOnTop(false);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			introUI.setVisible(false);
		}
	}
}
