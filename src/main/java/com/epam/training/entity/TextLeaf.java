package com.epam.training.entity;

import com.epam.training.exception.OperationNotSupportedException;

/* the class describes a leaf element of the text */
public class TextLeaf implements IComponent {
	private ComponentType componentType;
	private String content;

	public TextLeaf(ComponentType componentType, String content) {
		this.componentType = componentType;
		this.content = content;
	}

	/* overriden interface methods */
	@Override
	public void addComponent(IComponent component)
			throws OperationNotSupportedException {
		throw new OperationNotSupportedException(
				"Error. That kind of operation is not supported for the element!");
	}

	@Override
	public void removeComponent(IComponent component)
			throws OperationNotSupportedException {
		throw new OperationNotSupportedException(
				"Error. That kind of operation is not supported for the element!");
	}

	@Override
	public IComponent getComponent(int index) {
		return this;
	}

	@Override
	public ComponentType getComponentType() {
		return componentType;
	}

	@Override
	public int listOfComponentsSize() {
		return 0;
	}

	/* no need to parse leaf elements */
	@Override
	public void parse() {
		return;
	}
	
	/* just returns the content of the leaf */
	@Override
	public String reconstruct() {
		return toString();
	}

	@Override
	public String toString() {
		return content;
	}
}
