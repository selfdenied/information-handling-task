package com.epam.training.entity;

import java.util.ArrayList;
import java.util.List;

import com.epam.training.parser.TextParser;

/* the class describes a composite element of the text */
public class TextComposite implements IComponent {
	private ComponentType componentType;
	private String content;
	private List<IComponent> listOfComponents = new ArrayList<IComponent>();

	public TextComposite(ComponentType componentType, String content) {
		this.componentType = componentType;
		this.content = content;
	}

	/* overriden interface methods */
	@Override
	public void addComponent(IComponent component) {
		listOfComponents.add(component);
	}

	@Override
	public void removeComponent(IComponent component) {
		listOfComponents.remove(component);
	}

	@Override
	public IComponent getComponent(int index) {
		return listOfComponents.get(index);
	}

	@Override
	public ComponentType getComponentType() {
		return componentType;
	}

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
		for (IComponent component : listOfComponents) {
		/* recursive call of the method for each sub-element */
			builder.append(component.reconstruct());
		}
		return builder.toString();
	}

	@Override
	public String toString() {
		return content;
	}
}
