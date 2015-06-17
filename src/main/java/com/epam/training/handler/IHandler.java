package com.epam.training.handler;

import java.util.List;

import com.epam.training.entity.ComponentType;
import com.epam.training.entity.IComponent;
import com.epam.training.exception.IllegalSetValueException;

/* common interface for handlers */
interface IHandler {

	/* sets the next handler in the chain of handlers */
	public void setNextHandler(IHandler handler)
			throws IllegalSetValueException;

	/* passes a list of sub-elements to the next handler */
	public void setSubParts(List<IComponent> listOfSubParts);

	/* method returns the list of sub-elements of the given composite object */
	public List<IComponent> formListOfComponents(IComponent component,
			ComponentType typeNeeded);
}
