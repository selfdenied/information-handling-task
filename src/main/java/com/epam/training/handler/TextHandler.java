package com.epam.training.handler;

import java.util.ArrayList;
import java.util.List;

import com.epam.training.entity.ComponentType;
import com.epam.training.entity.IComponent;

public class TextHandler implements IHandler {
	private IHandler nextHandler;

	@Override
	public void setNextHandler(IHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
	
	@Override
	public void setSubParts(List<IComponent> listOfSubParts) {
		return;
	}

	@Override
	public List<IComponent> formListOfComponents(IComponent component,
			ComponentType typeNeeded) {
		List<IComponent> listOfComponents = new ArrayList<IComponent>();
		ComponentType typeOfContent = component.getComponentType();

		if (typeOfContent.name().equals("TEXT")) {
			switch (typeNeeded) {
			case TEXT:
				listOfComponents.add(component);
				break;
			case PARAGRAPH:
				listOfComponents = findParagraphsInText(component);
				break;
			case LISTING:
				listOfComponents = findListingsInText(component);
				break;
			default:
				nextHandler.setSubParts(findParagraphsInText(component));
				listOfComponents = nextHandler.formListOfComponents(component,
						typeNeeded);
				break;
			}
		} else {
			listOfComponents = nextHandler.formListOfComponents(component,
					typeNeeded);
		}
		return listOfComponents;
	}
	
	private List<IComponent> findParagraphsInText(IComponent component) {
		List<IComponent> listOfParagraphs = new ArrayList<IComponent>();
		
		for (int i = 0; i < component.listOfComponentsSize(); i++) {
			String type = component.getComponent(i).getComponentType().name(); 
			if (type.equals("PARAGRAPH")) {
				listOfParagraphs.add(component.getComponent(i));
			}
		}
		return listOfParagraphs;
	}
	
	private List<IComponent> findListingsInText(IComponent component) {
		List<IComponent> listOfListings = new ArrayList<IComponent>();
		
		for (int i = 0; i < component.listOfComponentsSize(); i++) {
			String type = component.getComponent(i).getComponentType().name(); 
			if (type.equals("LISTING")) {
				listOfListings.add(component.getComponent(i));
			}
		}
		return listOfListings;
	}
}
