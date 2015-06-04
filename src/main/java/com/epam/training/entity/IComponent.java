package com.epam.training.entity;

/* the component interface (implemented by both the composites and the leaves) */
public interface IComponent {

	public void addComponent(IComponent component);

	public void removeComponent(IComponent component);

	public IComponent getComponent(int index);

	public ComponentType getComponentType();

	public int listOfComponentsSize();

	public void parse();

	public String reconstruct();
}
