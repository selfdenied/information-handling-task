package com.epam.training.entity;

import com.epam.training.exception.IllegalSetValueException;

/* the class describes a leaf element of the text */
public class TextLeaf implements IComponent {
	private ComponentType componentType; // the type of leaf element
	private String content; // the content of leaf element

	public TextLeaf(ComponentType componentType, String content)
			throws IllegalSetValueException {
		setComponentType(componentType);
		setContent(content);
	}

	/* overriden interface methods */

	/* method is not supported by leaf elements */
	@Override
	public void addComponent(IComponent component) {
		throw new UnsupportedOperationException(
				"Error. That kind of operation is not supported by the element!");
	}

	/* method is not supported by leaf elements */
	@Override
	public void removeComponent(IComponent component) {
		throw new UnsupportedOperationException(
				"Error. That kind of operation is not supported by the element!");
	}

	/* leaf element returns itself */
	@Override
	public IComponent getComponent(int index) {
		return this;
	}

	/* method returns the type of leaf element */
	@Override
	public ComponentType getComponentType() {
		return componentType;
	}

	/* leaf element has no sub-elements */
	@Override
	public int listOfComponentsSize() {
		return 0;
	}

	/* no need to parse leaf elements */
	@Override
	public void parse() {
		return;
	}

	/* returns the content of the leaf */
	@Override
	public String reconstruct() {
		return toString();
	}

	/* setters with validation */
	public void setComponentType(ComponentType componentType)
			throws IllegalSetValueException {
		switch (componentType) {
		case LISTING:
		case WORD:
		case PUNCT_MARK:
			this.componentType = componentType;
			break;
		default:
			throw new IllegalSetValueException(
					"Error. Leaf elements can have either WORD, LISTING or PUNCT_MARK type!");
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
