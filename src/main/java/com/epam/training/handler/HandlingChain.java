package com.epam.training.handler;

import java.util.List;

import com.epam.training.entity.ComponentType;
import com.epam.training.entity.IComponent;

/*
 * the class initializes the chain of handlers
 * that deal with the given composite object
 */
public class HandlingChain {
	private IHandler textHandler;
	private IHandler paragraphHandler;
	private IHandler sentenceHandler;

	/*
	 * the constructor calls handler objects and sets the proper chain of
	 * handling the composite object
	 */
	public HandlingChain() {
		this.textHandler = new TextHandler();
		this.paragraphHandler = new ParagraphHandler();
		this.sentenceHandler = new SentenceHandler();

		textHandler.setNextHandler(paragraphHandler);
		paragraphHandler.setNextHandler(sentenceHandler);
	}

	/*
	 * Main method that forms a list of selected sub-elements on the basis of
	 * given composite object. I.e. we can pass a text object as a parameter and
	 * get a list of all (for example) words or sentences in this text
	 */
	public List<IComponent> formListOfComponents(IComponent component,
			ComponentType typeNeeded) {
		return textHandler.formListOfComponents(component, typeNeeded);
	}
}
