package com.epam.training.parser;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.epam.training.constant.Constants;
import com.epam.training.entity.*;
import com.epam.training.exception.IllegalSetValueException;
import com.epam.training.exception.OperationNotSupportedException;

/* the class for parsing the text into sub-elements */
public class TextParser {
	/* getting the logger reference */
	private static final Logger LOG = Logger.getLogger(TextParser.class);
	/* gets regular expressions from a properties file */
	private static final ResourceBundle rb = ResourceBundle
			.getBundle(Constants.REG_EXP_FILE_NAME);

	/* this method chooses a parsing procedure depending on the component type */
	public static void parse(IComponent component) {
		ComponentType componentType = component.getComponentType();

		switch (componentType) {
		case TEXT:
			parseToParagraphsAndListings(component);
			break;
		case PARAGRAPH:
			parseToSentences(component);
			break;
		case SENTENCE:
			parseToWordsAndMarks(component);
			break;
		default:
			LOG.warn("Warning. Parsing the leaf element will have no effect!");
			break;
		}
	}

	/*
	 * Important! Code blocks should start with a small letter (or @ symbol,
	 * i.e. @Override) and have a blank line after the end of code. That's an
	 * indication of the code block we use in reg exps to separate text into
	 * paragraphs and listings
	 */
	private static void parseToParagraphsAndListings(IComponent component) {
		IComponent part = null;
		String textContent = component.toString();
		Pattern pattern = Pattern.compile(rb.getString("splitText"));
		Matcher matcher = pattern.matcher(textContent);

		try {
			while (matcher.find()) {
				if (matcher.group().matches(rb.getString("findParagraphsInText"))) {
					part = new TextComposite(ComponentType.PARAGRAPH, matcher.group());
				}
				if (matcher.group().matches(rb.getString("findListingsInText"))) {
					part = new TextLeaf(ComponentType.LISTING, matcher.group());
				}
				component.addComponent(part);
			}
		} catch (OperationNotSupportedException exception) {
			LOG.error(exception.getMessage(), exception);
		} catch (IllegalSetValueException exception) {
			LOG.error(exception.getMessage(), exception);
		}
	}

	/* splits a paragraph into sentences */
	private static void parseToSentences(IComponent component) {
		IComponent part = null;
		String textContent = component.toString();
		Pattern pattern = Pattern.compile(rb.getString("splitParagraph"));
		Matcher matcher = pattern.matcher(textContent);

		try {
			while (matcher.find()) {
				part = new TextComposite(ComponentType.SENTENCE, matcher.group());
				component.addComponent(part);
			}
		} catch (OperationNotSupportedException exception) {
			LOG.error(exception.getMessage(), exception);
		} catch (IllegalSetValueException exception) {
			LOG.error(exception.getMessage(), exception);
		}
	}

	/* splits a sentence into words and punctuation marks */
	private static void parseToWordsAndMarks(IComponent component) {
		IComponent part = null;
		String textContent = component.toString();
		Pattern pattern = Pattern.compile(rb.getString("splitSentence"));
		String[] textElements = pattern.split(textContent);

		try {
			for (String element : textElements) {
				ComponentType partType;
				if (element.matches(rb.getString("word"))) {
					partType = ComponentType.WORD;
				} else {
					partType = ComponentType.PUNCT_MARK;
				}
				part = new TextLeaf(partType, element);
				component.addComponent(part);
			} 
		} catch (OperationNotSupportedException exception) {
			LOG.error(exception.getMessage(), exception);
		} catch (IllegalSetValueException exception) {
			LOG.error(exception.getMessage(), exception);
		}
	}
}
