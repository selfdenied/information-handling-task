package com.epam.training.util;

import java.util.List;

import org.apache.log4j.Logger;

import com.epam.training.constant.Constants;
import com.epam.training.entity.ComponentType;
import com.epam.training.entity.IComponent;
import com.epam.training.entity.TextComposite;
import com.epam.training.logic.ActionOne;
import com.epam.training.logic.ActionTwo;

public class PrintReportManager {
	/* getting the logger reference */
	private static final Logger LOG = Logger
			.getLogger(PrintReportManager.class);

	public void printReport() {
		LOG.info("Reading text from file...");
		String content = FileReadWriteManager
				.readTextFromFile(Constants.INPUT_FILE_PATH);
		IComponent text = new TextComposite(ComponentType.TEXT, content);

		LOG.info("Parsing text from file...");
		text.parse();

		LOG.info("Reconstructing text and writing into an output file...");
		FileReadWriteManager.writeIntoFile(text.reconstruct(),
				Constants.RECONSTRUCTED_FILE_PATH);

		LOG.info("Printing a report of required text actions...");
		FileReadWriteManager.writeIntoFile(reportOfActionOne(text)
				+ reportOfActionTwo(text), Constants.REPORT_FILE_PATH);
	}

	private String reportOfActionOne(IComponent text) {
		ActionOne action1 = new ActionOne();
		List<IComponent> listOfSentences = action1
				.allSentencesInTextSorted(text);
		StringBuilder builder = new StringBuilder();

		builder.append(Constants.REPORT_ACTION_ONE_MESSAGE);
		for (int i = 0; i < listOfSentences.size(); i++) {
			builder.append(i + 1).append(") ");
			builder.append(listOfSentences.get(i).toString().trim());
			builder.append(Constants.NEW_LINE_SYMBOL);
		}
		return builder.toString();
	}

	private String reportOfActionTwo(IComponent text) {
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
