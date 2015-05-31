package com.epam.training.entity;

import com.epam.training.exception.OperationNotSupportedException;

/* the component interface (implemented by both the composites and the leaves) */
public interface IComponent {

	public void addComponent(IComponent component)
			throws OperationNotSupportedException;

	public void removeComponent(IComponent component)
			throws OperationNotSupportedException;

	public IComponent getComponent(int index);

	public ComponentType getComponentType();

	public int listOfComponentsSize();

	public void parse();
	
	public String reconstruct();
}
