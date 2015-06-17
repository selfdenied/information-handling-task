package com.epam.training.logic;

import java.util.ArrayList;
import java.util.List;

import com.epam.training.entity.ComponentType;
import com.epam.training.entity.IComponent;
import com.epam.training.exception.IllegalSetValueException;
import com.epam.training.handler.HandlingChain;

/* this class performs one of the logic actions required by the task*/
public class ActionTwo {

	/*
	 * method returns a list of all sentences in the text with a given substring
	 * removed (between two specified chars)
	 */
	public List<String> removeMaxSubstringFromSentences(IComponent text,
			char startChar, char endChar) throws IllegalSetValueException {
		HandlingChain chain = new HandlingChain();
		List<String> listOfModifiedSentences = new ArrayList<String>();
		List<IComponent> listOfSentences = chain.formListOfComponents(text,
				ComponentType.SENTENCE);

		for (IComponent sentence : listOfSentences) {
			listOfModifiedSentences.add(sentenceWithSubstringRemoved(
					sentence.reconstruct(), startChar, endChar));
		}
		return listOfModifiedSentences;
	}

	/* supplementary method that removes a substring from a given sentence */
	private String sentenceWithSubstringRemoved(String sentence,
			char startChar, char endChar) {
		StringBuilder builder = new StringBuilder(sentence);

		if (checkIfCharsAreOk(sentence, startChar, endChar)) {
			int startIndex = sentence.indexOf(startChar);
			int endIndex = sentence.lastIndexOf(endChar);
			builder.delete(startIndex, endIndex + 1);
		}
		return builder.toString();
	}

	/*
	 * supplementary method that checks the presence of selected chars and the
	 * right order of them (to avoid StringIndexOutOfBoundsException)
	 */
	private boolean checkIfCharsAreOk(String sentence, char startChar,
			char endChar) {
		String start = String.valueOf(startChar);
		String end = String.valueOf(endChar);
		boolean charsOk = false;

		if (sentence.contains(start) && sentence.contains(end)
				&& sentence.indexOf(start) <= sentence.lastIndexOf(end)) {
			charsOk = true;
		}
		return charsOk;
	}
}
