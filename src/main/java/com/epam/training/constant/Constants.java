package com.epam.training.constant;

/* the list of all constants used in the application */
public class Constants {
	public static final String LOGGER_CONFIG_FILE_PATH = "config/log4j.xml";
	public static final String RECONSTRUCTED_FILE_PATH = "output/reconstructedText.txt";
	public static final String REPORT_FILE_PATH = "output/actionsReport.txt";
	public static final String INPUT_FILE_PATH = "input/textfile.txt";
	public static final String REG_EXP_FILE_NAME = "regexp";
	public static final String DEFAULT_CHARSET = "UTF-8";
	public static final String NEW_LINE_SYMBOL = "\n";
	public static final String REPORT_ACTION_ONE_MESSAGE = "The list of sentences sorted according to the"
			+ " number of words in them:\n\n";
	public static final String REPORT_ACTION_TWO_MESSAGE = "\nThe list of sentences with the excluded substring"
			+ " of maximum length that starts with 'f' char and ends with 'm' char:\n\n";
}
