package com.epam.training.handler;

import java.util.ArrayList;
import java.util.List;

import com.epam.training.entity.ComponentType;
import com.epam.training.entity.IComponent;

public class HandlingChain {
	private IHandler textHandler;
	private IHandler paragraphHandler;
	private IHandler sentenceHandler;
	
	public HandlingChain() {
		this.textHandler = new TextHandler();
		this.paragraphHandler = new ParagraphHandler();
		this.sentenceHandler = new SentenceHandler();
		
		textHandler.setNextHandler(paragraphHandler);
		paragraphHandler.setNextHandler(sentenceHandler);
	}
	
	public List<IComponent> formListOfComponents(IComponent component,
			ComponentType typeNeeded) {
		List<IComponent> listOfComponents = new ArrayList<IComponent>();
		listOfComponents = textHandler.formListOfComponents(component, typeNeeded);
		return listOfComponents;
	}
}
