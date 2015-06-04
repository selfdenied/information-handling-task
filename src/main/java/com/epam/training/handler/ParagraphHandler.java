package com.epam.training.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.training.entity.ComponentType;
import com.epam.training.entity.IComponent;

/* the class that handles paragraph composite objects */
class ParagraphHandler implements IHandler {
	/* getting the logger reference */
	private static final Logger LOG = Logger.getLogger(ParagraphHandler.class);
	private IHandler nextHandler;
	private List<IComponent> listOfSubParts;

	@Override
	public void setNextHandler(IHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	@Override
	public void setSubParts(List<IComponent> listOfSubParts) {
		this.listOfSubParts = listOfSubParts;
	}

	/*
	 * This method deals with composite objects of PARAGRAPH type. We can get a
	 * list of sentences using this method. If the request is words or punct.
	 * marks, the method redirects the request to the next handler in the chain.
	 * It also deals with text composite objects redirected from TextHandler
	 */
	@Override
	public List<IComponent> formListOfComponents(IComponent component,
			ComponentType typeNeeded) {
		List<IComponent> listOfComponents = new ArrayList<IComponent>();
		ComponentType typeOfContent = component.getComponentType();

		if (typeOfContent.equals(ComponentType.PARAGRAPH)) {
			switch (typeNeeded) {
			case PARAGRAPH:
				listOfComponents.add(component);
				break;
			case SENTENCE:
				listOfComponents = formSentences(component);
				break;
			case WORD:
			case PUNCT_MARK:
				// if we need to get the list of words or punct. marks,
				// we form a list of sentences and send it further
				nextHandler.setSubParts(formSentences(component));
				listOfComponents = nextHandler.formListOfComponents(component,
						typeNeeded);
				break;
			default:
				// if TEXT or LISTING types are used as 'typeNeeded' parameter
				// the error will occur (impossible to do)
				LOG.error("Error. Unable to separate a paragraph object into the specified subparts!");
				break;
			}
		} else if (typeOfContent.equals(ComponentType.TEXT)) {
			// here we further process text composite object
			// if it was redirected from TextHandler class
			listOfComponents = furtherProcessText(component, typeNeeded);
		} else {
			// if the type of composite object is not either TEXT or
			// PARAGRAPH the request is redirected to the next handler
			listOfComponents = nextHandler.formListOfComponents(component,
					typeNeeded);
		}
		return listOfComponents;
	}

	/* supplementary method that breaks paragraph into sentences */
	private List<IComponent> formSentences(IComponent component) {
		List<IComponent> listOfSentences = new ArrayList<IComponent>();

		for (int i = 0; i < component.listOfComponentsSize(); i++) {
			listOfSentences.add(component.getComponent(i));
		}
		return listOfSentences;
	}

	/*
	 * method processes text composite object sent from TextHandler in case when
	 * it is needed to form the list of sentences, words or punctuation marks
	 * (since TextHandler object redirects such requests)
	 */
	private List<IComponent> furtherProcessText(IComponent component,
			ComponentType typeNeeded) {
		List<IComponent> listOfComponents = new ArrayList<IComponent>();

		switch (typeNeeded) {
		case SENTENCE:
			listOfComponents = formSentencesFromParagraphs();
			break;
		case WORD:
		case PUNCT_MARK:
			// if it is needed to get the list of words or punct. marks,
			// we form a list of sentences and send it further
			nextHandler.setSubParts(formSentencesFromParagraphs());
			listOfComponents = nextHandler.formListOfComponents(component,
					typeNeeded);
			break;
		default:
			// three other component types are handled by TextHandler class
			LOG.error("Error. Unknown type of component!");
			break;
		}
		return listOfComponents;
	}

	/*
	 * method processes the list of paragraphs sent from TextHandler (if this is
	 * the case) and forms a list of sentences to be sent to the next handler
	 */
	private List<IComponent> formSentencesFromParagraphs() {
		List<IComponent> listOfSentences = new ArrayList<IComponent>();

		for (IComponent paragraph : listOfSubParts) {
			listOfSentences.addAll(formSentences(paragraph));
		}
		return listOfSentences;
	}
}
