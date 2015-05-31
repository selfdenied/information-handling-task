package com.epam.training.run;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.epam.training.constant.Constants;
import com.epam.training.util.PrintReportManager;

public class Runner {
	/* getting the logger reference */
	private static final Logger LOG = Logger.getLogger(Runner.class);
	/* initializing the logger configuration */
	static {
		new DOMConfigurator().doConfigure(Constants.LOGGER_CONFIG_FILE_PATH,
				LogManager.getLoggerRepository());
	}

	public static void main(String[] args) {
		PrintReportManager reportManager = new PrintReportManager();
		LOG.info("Initializing the program...");
		reportManager.printReport();
		LOG.info("Finished with no errors...");
	}
}
