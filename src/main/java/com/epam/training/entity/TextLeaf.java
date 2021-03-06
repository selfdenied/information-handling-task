package com.epam.training.entity;

import com.epam.training.exception.IllegalSetValueException;

/* the class describes a leaf element of the text */
public class TextLeaf implements IComponent {
	private ComponentType componentType; // the type of leaf element
	private String content; // the content of leaf element

	public TextLeaf(ComponentType componentType, String content)
			throws IllegalSetValueException {
		this.content = content;
		setComponentType(componentType);
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

	/* returns the content of the leaf */
	@Override
	public String reconstruct() {
		return toString();
	}

	@Override
	public String toString() {
		return content;
	}

	/* setter with validation */
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
}
