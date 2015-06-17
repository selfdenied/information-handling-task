package com.epam.training.logic;

import java.util.Collections;
import java.util.List;

import com.epam.training.comparator.SentenceWordsComparator;
import com.epam.training.entity.ComponentType;
import com.epam.training.entity.IComponent;
import com.epam.training.exception.IllegalSetValueException;
import com.epam.training.handler.HandlingChain;

/* this class performs one of the logic actions required by the task */
public class ActionOne {

	/*
	 * method sorts all sentences in the text according to the number of words
	 * in them, starting from the lowest number of words
	 */
	public List<IComponent> allSentencesInTextSorted(IComponent text)
			throws IllegalSetValueException {
		HandlingChain chain = new HandlingChain();
		List<IComponent> listOfSentences = chain.formListOfComponents(text,
				ComponentType.SENTENCE);
		Collections.sort(listOfSentences, new SentenceWordsComparator());
		return listOfSentences;
	}
}
