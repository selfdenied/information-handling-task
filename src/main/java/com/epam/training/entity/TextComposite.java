package com.epam.training.entity;

import java.util.ArrayList;
import java.util.List;

import com.epam.training.exception.IllegalSetValueException;
import com.epam.training.parser.TextParser;

/* the class describes a composite element of the text */
public class TextComposite implements IComponent {
	private ComponentType componentType;
	private String content;
	private List<IComponent> listOfComponents = new ArrayList<IComponent>();

	public TextComposite(ComponentType componentType, String content)
			throws IllegalSetValueException {
		setComponentType(componentType);
		setContent(content);
	}

	/* overriden interface methods */

	/* method adds a sub-element */
	@Override
	public void addComponent(IComponent component) {
		listOfComponents.add(component);
	}

	/* method removes a sub-element */
	@Override
	public void removeComponent(IComponent component) {
		listOfComponents.remove(component);
	}

	/* method returns the sub-element at a given position */
	@Override
	public IComponent getComponent(int index) {
		return listOfComponents.get(index);
	}

	/* method returns the type of composite element */
	@Override
	public ComponentType getComponentType() {
		return componentType;
	}

	/* returns the size of sub-elements list */
	@Override
	public int listOfComponentsSize() {
		return listOfComponents.size();
	}

	/* method that splits the composite element into the list of sub-elements */
	@Override
	public void parse() {
		TextParser.parse(this);
		/* recursive call of the method for each sub-element */
		for (IComponent component : listOfComponents) {
			component.parse();
		}
	}

	/* method that reconstructs the text after parsing */
	@Override
	public String reconstruct() {
		StringBuilder builder = new StringBuilder();
		/* recursive call of the method for each sub-element */
		for (IComponent component : listOfComponents) {
			builder.append(component.reconstruct());
		}
		return builder.toString();
	}

	/* setters with validation */
	public void setComponentType(ComponentType componentType)
			throws IllegalSetValueException {
		switch (componentType) {
		case TEXT:
		case PARAGRAPH:
		case SENTENCE:
			this.componentType = componentType;
			break;
		default:
			throw new IllegalSetValueException(
					"Error. Composite elements can have either TEXT, PARAGRAPH or SENTENCE type!");
		}
	}

	public void setContent(String content) throws IllegalSetValueException {
		if (content != null) {
			this.content = content;
		} else {
			throw new IllegalSetValueException(
					"Error. Cannot accept 'null' values. Enter a proper element content!");
		}
	}

	@Override
	public String toString() {
		return content;
	}
}
