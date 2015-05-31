package com.epam.training.handler;

import java.util.List;

import com.epam.training.entity.ComponentType;
import com.epam.training.entity.IComponent;

public interface IHandler {

	public void setNextHandler(IHandler handler);
	
	public void setSubParts(List<IComponent> listOfSubParts);

	public List<IComponent> formListOfComponents(IComponent component,
			ComponentType typeNeeded);
}
