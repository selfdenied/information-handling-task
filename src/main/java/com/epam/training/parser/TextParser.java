package com.epam.training.parser;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.epam.training.constant.Constants;
import com.epam.training.entity.*;
import com.epam.training.exception.IllegalSetValueException;

/* the class for parsing the text into sub-elements */
public class TextParser {
	/* getting the logger reference */
	private static final Logger LOG = Logger.getLogger(TextParser.class);
	/* gets regular expressions from a properties file */
	private static final ResourceBundle rb = ResourceBundle
			.getBundle(Constants.REG_EXP_FILE_NAME);

	/* this method chooses a parsing procedure depending on the component type */
	public static IComponent parse(IComponent component, String content) {
		ComponentType componentType = component.getComponentType();
		IComponent compositeElement = null;

		switch (componentType) {
		case TEXT:
			compositeElement = parseToParagraphsAndListings(component, content);
			break;
		case PARAGRAPH:
			compositeElement = parseToSentences(component, content);
			break;
		case SENTENCE:
			compositeElement = parseToWordsAndMarks(component, content);
			break;
		default:
			LOG.warn("Warning. Parsing the leaf element will have no effect!");
			break;
		}
		return compositeElement;
	}

	/*
	 * Important! Code blocks should start with a small letter (or @ symbol,
	 * i.e. @Override) and have at least one blank line after the end of code.
	 * That's an indication of the code block we use in reg exps to separate
	 * text into paragraphs and listings
	 */
	private static IComponent parseToParagraphsAndListings(IComponent text, String content) {
		Pattern pattern = Pattern.compile(rb.getString("splitText"));
		Matcher matcher = pattern.matcher(content);

		try {
			while (matcher.find()) {
				IComponent listing;
				IComponent paragraph = new TextComposite(ComponentType.PARAGRAPH);
				if (matcher.group().matches(rb.getString("findParagraphsInText"))) {
					paragraph = parseToSentences(paragraph, matcher.group());
					text.addComponent(paragraph);
				}
				if (matcher.group().matches(rb.getString("findListingsInText"))) {
					listing = new TextLeaf(ComponentType.LISTING, matcher.group());
					text.addComponent(listing);
				}
			}
		} catch (IllegalSetValueException exception) {
			LOG.error(exception.getMessage(), exception);
		}
		return text;
	}

	/* splits a paragraph into sentences */
	private static IComponent parseToSentences(IComponent paragraph, String content) {
		Pattern pattern = Pattern.compile(rb.getString("splitParagraph"));
		Matcher matcher = pattern.matcher(content);

		try {
			while (matcher.find()) {
				IComponent sentence = new TextComposite(ComponentType.SENTENCE);
				sentence = parseToWordsAndMarks(sentence, matcher.group());
				paragraph.addComponent(sentence);
			}
		} catch (IllegalSetValueException exception) {
			LOG.error(exception.getMessage(), exception);
		}
		return paragraph;
	}

	/* splits a sentence into words and punctuation marks */
	private static IComponent parseToWordsAndMarks(IComponent sentence, String content) {
		IComponent part = null;
		Pattern pattern = Pattern.compile(rb.getString("splitSentence"));
		String[] textElements = pattern.split(content);

		try {
			for (String element : textElements) {
				ComponentType partType;
				if (element.matches(rb.getString("word"))) {
					partType = ComponentType.WORD;
				} else {
					partType = ComponentType.PUNCT_MARK;
				}
				part = new TextLeaf(partType, element);
				sentence.addComponent(part);
			}
		} catch (IllegalSetValueException exception) {
			LOG.error(exception.getMessage(), exception);
		}
		return sentence;
	}
}
