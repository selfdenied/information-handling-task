package com.epam.training.util;

import java.util.List;

import org.apache.log4j.Logger;

import com.epam.training.constant.Constants;
import com.epam.training.entity.*;
import com.epam.training.exception.IllegalSetValueException;
import com.epam.training.logic.*;
import com.epam.training.parser.TextParser;

public class PrintReportManager {
	/* getting the logger reference */
	private static final Logger LOG = Logger
			.getLogger(PrintReportManager.class);

	/*
	 * method prints the report, including the text reconstructed after parsing,
	 * and two actions performed with text
	 */
	public void printReport() {
		LOG.info("Reading text from file...");
		String content = FileReadWriteManager
				.readTextFromFile(Constants.INPUT_FILE_PATH);
		try {
			IComponent text = new TextComposite(ComponentType.TEXT);
			LOG.info("Parsing text from file...");
			text = TextParser.parse(text, content);

			LOG.info("Reconstructing text and writing into an output file...");
			FileReadWriteManager.writeIntoFile(text.reconstruct(),
					Constants.RECONSTRUCTED_FILE_PATH);

			LOG.info("Printing a report of required text actions...");
			FileReadWriteManager.writeIntoFile(reportOfActionOne(text)
					+ reportOfActionTwo(text), Constants.REPORT_FILE_PATH);
		} catch (IllegalSetValueException exception) {
			LOG.error(exception.getMessage(), exception);
		}
	}

	/* supplementary method that forms a report of the first action */
	private String reportOfActionOne(IComponent text)
			throws IllegalSetValueException {
		ActionOne action1 = new ActionOne();
		List<IComponent> listOfSentences = action1
				.allSentencesInTextSorted(text);
		StringBuilder builder = new StringBuilder();

		builder.append(Constants.REPORT_ACTION_ONE_MESSAGE);
		for (int i = 0; i < listOfSentences.size(); i++) {
			builder.append(i + 1).append(") ");
			builder.append(listOfSentences.get(i).reconstruct().trim());
			builder.append(Constants.NEW_LINE_SYMBOL);
		}
		return builder.toString();
	}

	/* supplementary method that forms a report of the second action */
	private String reportOfActionTwo(IComponent text)
			throws IllegalSetValueException {
		ActionTwo action2 = new ActionTwo();
		List<String> listOfSentences = action2.removeMaxSubstringFromSentences(
				text, 'f', 'm');
		StringBuilder builder = new StringBuilder();

		builder.append(Constants.REPORT_ACTION_TWO_MESSAGE);
		for (int i = 0; i < listOfSentences.size(); i++) {
			builder.append(i + 1).append(") ");
			builder.append(listOfSentences.get(i).trim());
			builder.append(Constants.NEW_LINE_SYMBOL);
		}
		return builder.toString();
	}
}
