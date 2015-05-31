package com.epam.training.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.training.entity.ComponentType;
import com.epam.training.entity.IComponent;

public class SentenceHandler implements IHandler {
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

	@Override
	public List<IComponent> formListOfComponents(IComponent component,
			ComponentType typeNeeded) {
		List<IComponent> listOfComponents = new ArrayList<IComponent>();
		String typeName = component.getComponentType().name();

		if (typeName.equals("SENTENCE")) {
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
				LOG.error("Error. Unable to separate a sentence object into the specified subparts");
				break;
			}
		} else if (typeName.equals("TEXT") | typeName.equals("PARAGRAPH")) {
			listOfComponents = furtherProcessText(typeNeeded);
		} else {
			LOG.error("Error. You've entered a leaf object. Choose a composite object instead!");
		}
		return listOfComponents;
	}
	
	private List<IComponent> findWordsInSentence(IComponent component) {
		List<IComponent> listOfWords = new ArrayList<IComponent>();
		
		for (int i = 0; i < component.listOfComponentsSize(); i++) {
			String type = component.getComponent(i).getComponentType().name(); 
			if (type.equals("WORD")) {
				listOfWords.add(component.getComponent(i));
			}
		}
		return listOfWords;
	}
	
	private List<IComponent> findMarksInSentence(IComponent component) {
		List<IComponent> listOfMarks = new ArrayList<IComponent>();
		
		for (int i = 0; i < component.listOfComponentsSize(); i++) {
			String type = component.getComponent(i).getComponentType().name(); 
			if (type.equals("PUNCT_MARK")) {
				listOfMarks.add(component.getComponent(i));
			}
		}
		return listOfMarks;
	}
	
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
			break;
		}
		return listOfComponents;
	}
	
	private List<IComponent> selectWordsFromSentences() {
		List<IComponent> listOfWords = new ArrayList<IComponent>();

		for (IComponent sentence : listOfSubParts) {
			listOfWords.addAll(findWordsInSentence(sentence));
		}
		return listOfWords;
	}
	
	private List<IComponent> selectMarksFromSentences() {
		List<IComponent> listOfMarks = new ArrayList<IComponent>();

		for (IComponent sentence : listOfSubParts) {
			listOfMarks.addAll(findMarksInSentence(sentence));
		}
		return listOfMarks;
	}
}
