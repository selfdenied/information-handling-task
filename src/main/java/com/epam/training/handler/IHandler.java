package com.epam.training.handler;

import java.util.List;

import com.epam.training.entity.ComponentType;
import com.epam.training.entity.IComponent;

/* common interface for handlers */
public interface IHandler {

	/* sets the next handler in the chain of handlers */
	public void setNextHandler(IHandler handler);

	/* passes a list of sub-elements to the next handler */
	public void setSubParts(List<IComponent> listOfSubParts);

	/* method returns the list of sub-elements of the given composite object */
	public List<IComponent> formListOfComponents(IComponent component,
			ComponentType typeNeeded);
}
