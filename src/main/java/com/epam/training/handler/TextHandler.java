package com.epam.training.handler;

import java.util.ArrayList;
import java.util.List;

import com.epam.training.entity.ComponentType;
import com.epam.training.entity.IComponent;

/* the class that handles text composite objects */
class TextHandler implements IHandler {
	private IHandler nextHandler;

	@Override
	public void setNextHandler(IHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	@Override
	public void setSubParts(List<IComponent> listOfSubParts) {
		return;
	}

	/*
	 * Main method that forms a list of selected sub-elements on the basis of
	 * given composite object. It deals with composite objects of TEXT type. We
	 * can get a list of all paragraphs, listings or sentences using this
	 * method. If the request is words or punct. marks, the method redirects the
	 * request to the next handler in the chain of responsibility
	 */
	@Override
	public List<IComponent> formListOfComponents(IComponent component,
			ComponentType typeNeeded) {
		List<IComponent> listOfComponents = new ArrayList<IComponent>();
		ComponentType typeOfContent = component.getComponentType();

		if (typeOfContent.equals(ComponentType.TEXT)) {
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
				// if we need to get the list of sentences, words or punct.
				// marks, we form a list of paragraphs and send it further
				nextHandler.setSubParts(findParagraphsInText(component));
				listOfComponents = nextHandler.formListOfComponents(component,
						typeNeeded);
				break;
			}
		} else {
			// if the type of composite object is not TEXT the request is
			// redirected to the next handler
			listOfComponents = nextHandler.formListOfComponents(component,
					typeNeeded);
		}
		return listOfComponents;
	}

	/* supplementary method that finds paragraphs in text */
	private List<IComponent> findParagraphsInText(IComponent component) {
		List<IComponent> listOfParagraphs = new ArrayList<IComponent>();

		for (int i = 0; i < component.listOfComponentsSize(); i++) {
			ComponentType type = component.getComponent(i).getComponentType();
			if (type.equals(ComponentType.PARAGRAPH)) {
				listOfParagraphs.add(component.getComponent(i));
			}
		}
		return listOfParagraphs;
	}

	/* supplementary method that finds listings in text */
	private List<IComponent> findListingsInText(IComponent component) {
		List<IComponent> listOfListings = new ArrayList<IComponent>();

		for (int i = 0; i < component.listOfComponentsSize(); i++) {
			ComponentType type = component.getComponent(i).getComponentType();
			if (type.equals(ComponentType.LISTING)) {
				listOfListings.add(component.getComponent(i));
			}
		}
		return listOfListings;
	}
}
