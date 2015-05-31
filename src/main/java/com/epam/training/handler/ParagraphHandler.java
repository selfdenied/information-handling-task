package com.epam.training.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.training.entity.ComponentType;
import com.epam.training.entity.IComponent;

public class ParagraphHandler implements IHandler {
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

	@Override
	public List<IComponent> formListOfComponents(IComponent component,
			ComponentType typeNeeded) {
		List<IComponent> listOfComponents = new ArrayList<IComponent>();
		ComponentType typeOfContent = component.getComponentType();

		if (typeOfContent.name().equals("PARAGRAPH")) {
			switch (typeNeeded) {
			case PARAGRAPH:
				listOfComponents.add(component);
				break;
			case SENTENCE:
				listOfComponents = formSentences(component);
				break;
			case WORD:
			case PUNCT_MARK:
				nextHandler.setSubParts(formSentences(component));
				listOfComponents = nextHandler.formListOfComponents(component,
						typeNeeded);
				break;
			default:
				LOG.error("Error. Unable to separate a paragraph object into the specified subparts");
				break;
			}
		} else if (typeOfContent.name().equals("TEXT")) {
			listOfComponents = furtherProcessText(component, typeNeeded);
		} else {
			listOfComponents = nextHandler.formListOfComponents(component,
					typeNeeded);
		}
		return listOfComponents;
	}

	private List<IComponent> formSentences(IComponent component) {
		List<IComponent> listOfSentences = new ArrayList<IComponent>();

		for (int i = 0; i < component.listOfComponentsSize(); i++) {
			listOfSentences.add(component.getComponent(i));
		}
		return listOfSentences;
	}

	private List<IComponent> furtherProcessText(IComponent component,
			ComponentType typeNeeded) {
		List<IComponent> listOfComponents = new ArrayList<IComponent>();
		
		switch (typeNeeded) {
		case SENTENCE:
			listOfComponents = formSentencesFromParagraphs();
			break;
		case WORD:
		case PUNCT_MARK:
			nextHandler.setSubParts(formSentencesFromParagraphs());
			listOfComponents = nextHandler.formListOfComponents(component,
					typeNeeded);
			break;
		default:
			break;
		}
		return listOfComponents;
	}

	private List<IComponent> formSentencesFromParagraphs() {
		List<IComponent> listOfSentences = new ArrayList<IComponent>();

		for (IComponent paragraph : listOfSubParts) {
			listOfSentences.addAll(formSentences(paragraph));
		}
		return listOfSentences;
	}
}
