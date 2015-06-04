package com.epam.training.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.training.entity.ComponentType;
import com.epam.training.entity.IComponent;

/* the class that handles sentence composite objects */
class SentenceHandler implements IHandler {
	/* getting the logger reference */
	private static final Logger LOG = Logger.getLogger(SentenceHandler.class);
	private List<IComponent> listOfSubParts;

	@Override
	public void setNextHandler(IHandler nextHandler) {
		return;
	}

	@Override
	public void setSubParts(List<IComponent> listOfSubParts) {
		this.listOfSubParts = listOfSubParts;
	}

	/*
	 * This method deals with composite objects of SENTENCE type. We can get a
	 * list of words or punct. marks using this method. It also deals with text
	 * composite objects redirected from TextHandler or ParagraphHandler classes
	 */
	@Override
	public List<IComponent> formListOfComponents(IComponent component,
			ComponentType typeNeeded) {
		List<IComponent> listOfComponents = new ArrayList<IComponent>();
		ComponentType typeOfContent = component.getComponentType();

		if (typeOfContent.equals(ComponentType.SENTENCE)) {
			switch (typeNeeded) {
			case SENTENCE:
				listOfComponents.add(component);
				break;
			case WORD:
				listOfComponents = findWordsInSentence(component);
				break;
			case PUNCT_MARK:
				listOfComponents = findMarksInSentence(component);
				break;
			default:
				// if TEXT, LISTING or PARAGRAPH types are used as
				// 'typeNeeded' parameter the error will occur
				LOG.error("Error. Unable to separate a sentence object into the specified subparts!");
				break;
			}
		} else if (typeOfContent.equals(ComponentType.TEXT)
				| typeOfContent.equals(ComponentType.PARAGRAPH)) {
			// here we further process text/paragraph composite object
			// if it was redirected from TextHandler/ParagraphHandler class
			listOfComponents = furtherProcessText(typeNeeded);
		} else {
			LOG.error("Error. You've entered a leaf object. Choose a composite object instead!");
		}
		return listOfComponents;
	}

	/* supplementary method that finds words in a sentence */
	private List<IComponent> findWordsInSentence(IComponent component) {
		List<IComponent> listOfWords = new ArrayList<IComponent>();

		for (int i = 0; i < component.listOfComponentsSize(); i++) {
			ComponentType type = component.getComponent(i).getComponentType();
			if (type.equals(ComponentType.WORD)) {
				listOfWords.add(component.getComponent(i));
			}
		}
		return listOfWords;
	}

	/* supplementary method that finds punct. marks in a sentence */
	private List<IComponent> findMarksInSentence(IComponent component) {
		List<IComponent> listOfMarks = new ArrayList<IComponent>();

		for (int i = 0; i < component.listOfComponentsSize(); i++) {
			ComponentType type = component.getComponent(i).getComponentType();
			if (type.equals(ComponentType.PUNCT_MARK)) {
				listOfMarks.add(component.getComponent(i));
			}
		}
		return listOfMarks;
	}

	/*
	 * method processes text/paragraph composite object sent from
	 * TextHandler/ParagraphHandler in case when it is needed to form the list
	 * of words or punctuation marks (since TextHandler/ParagraphHandler objects
	 * redirect such requests)
	 */
	private List<IComponent> furtherProcessText(ComponentType typeNeeded) {
		List<IComponent> listOfComponents = new ArrayList<IComponent>();

		switch (typeNeeded) {
		case WORD:
			listOfComponents = selectWordsFromSentences();
			break;
		case PUNCT_MARK:
			listOfComponents = selectMarksFromSentences();
			break;
		default:
			// other component types are already processed
			LOG.error("Error. Unknown type of component!");
			break;
		}
		return listOfComponents;
	}

	/*
	 * method processes the list of sentences sent from ParagraphHandler (if
	 * this is the case) and forms a list of words
	 */
	private List<IComponent> selectWordsFromSentences() {
		List<IComponent> listOfWords = new ArrayList<IComponent>();

		for (IComponent sentence : listOfSubParts) {
			listOfWords.addAll(findWordsInSentence(sentence));
		}
		return listOfWords;
	}

	/*
	 * method processes the list of sentences sent from ParagraphHandler (if
	 * this is the case) and forms a list of punct. marks
	 */
	private List<IComponent> selectMarksFromSentences() {
		List<IComponent> listOfMarks = new ArrayList<IComponent>();

		for (IComponent sentence : listOfSubParts) {
			listOfMarks.addAll(findMarksInSentence(sentence));
		}
		return listOfMarks;
	}
}
